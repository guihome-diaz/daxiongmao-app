package eu.daxiongmao.core.model.mapper;

import eu.daxiongmao.core.model.db.Parameter;
import eu.daxiongmao.core.model.dto.ParameterDTO;
import org.springframework.stereotype.Component;

/**
 * DB to DTO mapper and vice-versa
 * @author Guillaume Diaz
 * @version 1.0 2019/12
 * @since application creation
 */
@Component
public class ParameterMapper implements AbstractMapper<Parameter, ParameterDTO> {

    @Override
    public ParameterDTO dbEntityToDto(Parameter dbEntity) {
        if (dbEntity == null) { return null; }

        ParameterDTO dto = new ParameterDTO();
        dto.setDescription(dbEntity.getDescription());
        dto.setIsActive(dbEntity.getIsActive());
        dto.setParamName(dbEntity.getParamName());
        dto.setParamType(dbEntity.getParamType());
        dto.setParamValue(dbEntity.getParamValue());
        return dto;
    }

    @Override
    public Parameter dtoToEntity(ParameterDTO dtoObject) {
        if (dtoObject == null) { return null; }

        Parameter dbEntity = new Parameter();
        dbEntity.setDescription(dtoObject.getDescription());
        dbEntity.setIsActive(dtoObject.getIsActive());
        dbEntity.setParamName(dtoObject.getParamName());
        dbEntity.setParamType(dtoObject.getParamType());
        dbEntity.setParamValue(dtoObject.getParamValue());
        return dbEntity;
    }
}
