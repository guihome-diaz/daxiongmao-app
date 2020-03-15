package eu.daxiongmao.core.api;

import eu.daxiongmao.core.api.error.ApiError;
import eu.daxiongmao.core.api.error.ApiErrorCodesEnum;
import eu.daxiongmao.core.business.LabelService;
import eu.daxiongmao.core.model.enums.AppLang;
import eu.daxiongmao.core.model.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * Controller that acts like an aspect to handle all exceptions and convert them into HTTP correct response.
 * @author Guillaume Diaz (implementation)
 * @author Spring boot team / community (design)
 * @version 1.0
 * @since 2020/02
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private final LabelService labelService;

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ApiErrorCodesEnum.HTTP_400_BAD_REQUEST, ex), request);
    }

    // HTTP 400

    @ExceptionHandler({ ConstraintViolationException.class })
    protected ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ApiErrorCodesEnum.HTTP_400_BAD_REQUEST, ex), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ApiErrorCodesEnum.HTTP_400_BAD_REQUEST, ex), request);
    }


    // HTTP 404: not found
    @ExceptionHandler({ NoResultException.class})
    protected ResponseEntity<Object> handleNotFound(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ApiErrorCodesEnum.HTTP_404_NOT_FOUND, ex), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ApiErrorCodesEnum.HTTP_404_NOT_FOUND, ex), request);
    }


    /**
     * Override Spring default error for {@link MethodArgumentNotValidException}: return HTTP 406, NOT_ACCEPTABLE
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_ACCEPTABLE, ApiErrorCodesEnum.HTTP_406_NOT_ACCEPTABLE, ex), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleNotAuthorized(Exception ex, WebRequest request) throws Exception {
        if (ex instanceof UnauthorizedException) {
            return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, ApiErrorCodesEnum.HTTP_403_FORBIDDEN, ex), request);
        }

        // Unknown exception, typically a wrapper.
        // We only deal with top-level exceptions here, so let's rethrow the given exception for further processing
        // through the HandlerExceptionResolver chain.
        throw ex;
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, WebRequest request) {
        // Get language
        AppLang errLang = labelService.getDefaultLanguage();
        if (request != null) {
            errLang = labelService.getLanguageForLocale(request.getLocale());
        }

        // Set error code's label translation as "error message", if available
        final Optional<String> errText = labelService.getByCodeAndLanguage(apiError.getErrorCode(), errLang);
        errText.ifPresent(apiError::setErrorMessage);

        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
