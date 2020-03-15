package eu.daxiongmao.core.business;


import eu.daxiongmao.core.dao.MonitoringEventRepository;
import eu.daxiongmao.core.model.db.MonitoringEvent;
import eu.daxiongmao.core.model.dto.MonitoringEventDTO;
import eu.daxiongmao.core.model.mapper.MonitoringEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * To manage monitoring events.
 * @author Guillaume Diaz
 * @version 1.0 - 2020/01
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class MonitoringService {

    private final MonitoringEventMapper monitoringEventMapper;
    private final MonitoringEventRepository monitoringEventRepository;

    /** To avoid concurrent DB operations */
    private final Lock lock = new ReentrantLock();

    /**
     * Events that occurred since last persistence in DB.
     * To avoid performances shortage due to monitoring, events are cached in memory and saved periodically in DB.
     * => this is the same principle as Apache Lucene / Elastic Search. Reading is real time but indexation occurs every XX seconds
     * <ul>
     *     <li>Map key: random UUID to identify the event inside the cache. This make it easier to remove once event is saved in DB</li>
     *     <li>Map value: monitoring event to save</li>
     * </ul>
     */
    private final Map<String, MonitoringEventDTO> eventsToSave = new ConcurrentHashMap<>();

    /**
     * To create a new monitoring event.
     * This event will be saved in the current cache and persisted in DB on next write batch.<br>
     *     Technical note: this method does not throws exceptions because we do not want monitoring to break the application
     * @param event event to save
     * @return boolean, "true" if the event was successfully added to the cache
     */
    public boolean newMonitoringEvent(MonitoringEventDTO event) {
        if (event == null) { return false; }

        // TODO check event validity

        eventsToSave.put(UUID.randomUUID().toString(), event);
        return true;
    }

    /**
     * To save the current cache content in DB
     * @return number of events that have been saved in DB
     */
    public int saveEvents() {
        int nbOfEventsSaved = 0;

        // Avoid multi-thread issues: only 1 persistence at a time
        if (lock.tryLock()) {
            // Got lock
            try {
                final List<MonitoringEvent> eventsToPersist = new ArrayList<>();
                final Set<String> uuidOfEventsToRemoveFromCache = new HashSet<>();

                // Read map at instant T and put all of into elements to save
                eventsToSave.forEach((uuid, event) -> {
                    eventsToPersist.add(monitoringEventMapper.dtoToEntity(event));
                    uuidOfEventsToRemoveFromCache.add(uuid);
                });

                // Save events in DB (batch configuration is set in application.properties)
                monitoringEventRepository.saveAll(eventsToPersist);
                // Remove events from cache after persistence
                eventsToSave.keySet().removeAll(uuidOfEventsToRemoveFromCache);
                nbOfEventsSaved = eventsToPersist.size();
            } catch (Exception e) {
              log.error("Failed to save monitoring events in DB", e);
            } finally {
                // Make sure to release the lock to avoid deadlock
                lock.unlock();
            }
        } else {
            // Another process is already running
        }

        log.debug("{} monitoring events have been saved to database", nbOfEventsSaved);
        return nbOfEventsSaved;
    }

}
