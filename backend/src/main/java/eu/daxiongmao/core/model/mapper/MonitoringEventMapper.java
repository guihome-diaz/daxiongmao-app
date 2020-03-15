package eu.daxiongmao.core.model.mapper;

import eu.daxiongmao.core.model.db.MonitoringEvent;
import eu.daxiongmao.core.model.dto.MonitoringEventDTO;
import org.springframework.stereotype.Component;

/**
 * DB to DTO mapper and vice-versa
 * @author Guillaume Diaz
 * @version 1.0 2019/12
 * @since application creation
 */
@Component
public class MonitoringEventMapper implements AbstractMapper<MonitoringEvent, MonitoringEventDTO> {

    @Override
    public MonitoringEventDTO dbEntityToDto(MonitoringEvent dbEntity) {
        if (dbEntity == null) { return null; }

        MonitoringEventDTO dto = new MonitoringEventDTO();
        dto.setComments(dbEntity.getComments());
        dto.setEventName(dbEntity.getEventName());
        dto.setEventTime(dbEntity.getEventTime());
        dto.setEventType(dbEntity.getEventType());
        dto.setExecutionResult(dbEntity.getExecutionResult());
        dto.setExecutionTimeInMs(dbEntity.getExecutionTimeInMs());
        dto.setItem1(dbEntity.getItem1());
        dto.setItem2(dbEntity.getItem2());
        dto.setItem3(dbEntity.getItem3());
        dto.setThirdParty(dbEntity.getThirdParty());
        return dto;
    }

    @Override
    public MonitoringEvent dtoToEntity(MonitoringEventDTO dtoObject) {
        if (dtoObject == null) { return null; }

        MonitoringEvent dbEntity = new MonitoringEvent();
        dbEntity.setComments(dtoObject.getComments());
        dbEntity.setEventName(dtoObject.getEventName());
        dbEntity.setEventTime(dtoObject.getEventTime());
        dbEntity.setEventType(dtoObject.getEventType());
        dbEntity.setExecutionResult(dtoObject.getExecutionResult());
        dbEntity.setExecutionTimeInMs(dtoObject.getExecutionTimeInMs());
        dbEntity.setItem1(dtoObject.getItem1());
        dbEntity.setItem2(dtoObject.getItem2());
        dbEntity.setItem3(dtoObject.getItem3());
        dbEntity.setThirdParty(dtoObject.getThirdParty());
        return dbEntity;
    }
}
