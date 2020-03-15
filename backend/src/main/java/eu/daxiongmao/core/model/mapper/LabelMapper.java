package eu.daxiongmao.core.model.mapper;

import eu.daxiongmao.core.model.db.Label;
import eu.daxiongmao.core.model.dto.LabelDTO;
import org.springframework.stereotype.Component;

/**
 * DB to DTO mapper and vice-versa
 * @author Guillaume Diaz
 * @version 1.0 2020/03
 * @since application creation
 */
@Component
public class LabelMapper implements AbstractMapper<Label, LabelDTO> {
    @Override
    public LabelDTO dbEntityToDto(Label dbEntity) {
        if (dbEntity == null) {
            return null;
        }

        LabelDTO dto = new LabelDTO();
        dto.setCode(dbEntity.getCode());
        dto.setChinese(dbEntity.getChinese());
        dto.setEnglish(dbEntity.getEnglish());
        dto.setFrench(dbEntity.getFrench());
        return dto;
    }

    @Override
    public Label dtoToEntity(LabelDTO dtoObject) {
        if (dtoObject == null) {
            return null;
        }
        Label dbEntity = new Label();
        dbEntity.setCode(dtoObject.getCode());
        dbEntity.setChinese(dtoObject.getChinese());
        dbEntity.setEnglish(dtoObject.getEnglish());
        dbEntity.setFrench(dtoObject.getFrench());
        return dbEntity;
    }
}
