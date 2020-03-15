package eu.daxiongmao.core.api.error;

/**
 * List of all errors codes
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/02
 */
public enum ApiErrorCodesEnum {

    /** Default error */
    UNEXPECTED_ERROR,

    /** HTTP 400 error: missing arguments / wrong conditions / malformed request */
    HTTP_400_BAD_REQUEST,

    /** HTTP 403 error: user is NOT allowed to perform requested operation / access to that particular data */
    HTTP_403_FORBIDDEN,

    /** HTTP 404 error: resource not found */
    HTTP_404_NOT_FOUND,

    HTTP_406_NOT_ACCEPTABLE,

    /** HTTP 500 error: internal server error (backend side error, failed to perform requested task / operation). */
    HTTP_500_INTERNAL_SERVER_ERROR
    ;

}
