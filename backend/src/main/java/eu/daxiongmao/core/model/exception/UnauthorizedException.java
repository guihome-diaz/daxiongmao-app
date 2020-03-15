package eu.daxiongmao.core.model.exception;

/**
 * To trigger a forbidden event
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/02
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
