package eu.daxiongmao.core.model.enums.param;

import eu.daxiongmao.core.model.enums.Environment;

/**
 * List of technical parameters in database.
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public enum TechnicalParam implements IParameterEnum {

    /** Current mode of the application. To get the allowed values see the corresponding Java ENUM in the code */
    MODE("APP.MODE", String.class),

    /** Environment specifics values */
    ENVIRONMENT("APP.ENVIRONMENT", Environment.class),

    /** Parameters caching refresh */
    MIN_TIME_IN_SECONDS_BETWEEN_CACHE_REFRESH("APP.CACHE.REFRESH.MINIMUM_DELAY_BETWEEN_REFRESH_IN_SECONDS", Integer.class),

    /** DB schema version */
    DATABASE_VERSION("APP.DB.VERSION", String.class),

    /** Include exception stacktrace in JSON responses (if "false" stacktrace will not be send) */
    WEB_SERVICES_JSON_INCLUDE_STACKTRACE_ON_ERROR("WEB-SERVICES.JSON.INCLUDE_STACKTRACE_ON_ERROR", Boolean.class)
    ;
    private String paramName;
    private Class clazz;

    TechnicalParam(String paramName, Class clazz) {
        this.paramName = paramName;
        this.clazz = clazz;
    }

    @Override
    public String getParamName() {
        return paramName;
    }

    @Override
    public Class getClazz() {
        return clazz;
    }
}
