package eu.daxiongmao.core.model.exception;

/**
 * Cannot convert an Entity to DTO, or vice-versa
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/02
 */
public class MapperException extends RuntimeException {

    /**
     * Cannot convert an entity to DTO or vice-versa
     * @param message error message
     */
    public MapperException(String message) {
        super(message);
    }
}
