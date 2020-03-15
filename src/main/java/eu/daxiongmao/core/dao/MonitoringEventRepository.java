package eu.daxiongmao.core.dao;

import eu.daxiongmao.core.model.db.MonitoringEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * To interact with "monitoring_events" table
 * @author Guillaume Diaz
 * @version 1.0 2020/03
 * @since application creation
 */
@Repository
public interface MonitoringEventRepository extends JpaRepository<MonitoringEvent, Long> {
}
