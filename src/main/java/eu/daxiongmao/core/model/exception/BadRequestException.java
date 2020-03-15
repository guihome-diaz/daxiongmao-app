package eu.daxiongmao.core.model.exception;

import eu.daxiongmao.core.api.error.ApiValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * To trigger a bad request event
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/02
 */
public class BadRequestException extends RuntimeException {

    private List<ApiValidationError> validationErrors;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ApiValidationError error) {
        this.validationErrors = new ArrayList<>();
        this.validationErrors.add(error);
    }

    public BadRequestException(String message, ApiValidationError error) {
        super(message);

        this.validationErrors = new ArrayList<>();
        this.validationErrors.add(error);
    }

    public BadRequestException(String message, List<ApiValidationError> errors) {
        super(message);
        if (errors != null) {
            this.validationErrors = errors;
        }
    }


}
