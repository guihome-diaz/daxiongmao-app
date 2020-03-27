package eu.daxiongmao.core.model.enums.param;


import eu.daxiongmao.core.model.enums.param.IParameterEnum;

/**
 * List of security parameters in database.
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public enum SecurityParam implements IParameterEnum {

    /** Current SALT (secure random) algorithm. This must be an highly secure algorithm */
    SALT_ALGORITHM("SECURITY.SALT.ALGORITHM", String.class)

    ;
    private String paramName;
    private Class clazz;

    SecurityParam(String paramName, Class clazz) {
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
