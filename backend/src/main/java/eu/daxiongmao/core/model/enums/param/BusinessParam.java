package eu.daxiongmao.core.model.enums.param;


/**
 * List of business parameters in database.
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public enum BusinessParam implements IParameterEnum {

    /** Default application language in case translation for requested language does not exists; or that user lang is not available (yet) */
    APP_DEFAULT_LANGUAGE("APP.DEFAULT_LANG", String.class),

    /** Application COMMERCIAL (public) version. This is not related to DB, Maven or GIT versions at all. */
    APP_VERSION("APP.VERSION", String.class);

    private String paramName;
    private Class clazz;

    BusinessParam(String paramName, Class clazz) {
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
