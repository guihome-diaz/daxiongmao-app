package eu.daxiongmao.core.model.mapper;

/**
 * DB to DTO mapper and vice-versa
 * @author Guillaume Diaz
 * @version 1.0 2019/12
 * @since application creation
 * @param <T> Entity class (database server side)
 * @param <K> DTO class (web-service client side)
 */
public interface AbstractMapper<T, K> {

    /**
     * To convert an entity to DTO.
     * @param dbEntity entity to convert
     * @return corresponding DTO
     */
    K dbEntityToDto(T dbEntity);

    /**
     * To convert a DTO to an entity (we given params).
     * @param dtoObject DTO to convert
     * @return corresponding entity, ready to be saved or adjusted for database
     */
    T dtoToEntity(K dtoObject);

}
