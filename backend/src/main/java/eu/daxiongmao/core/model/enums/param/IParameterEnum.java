package eu.daxiongmao.core.model.enums.param;

/**
 * Description of a parameter enum
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public interface IParameterEnum {

    /** @return parameter name (string) saved in DB for that enum's entry */
    String getParamName();

    /** @return parameter java class (type) related to current enum's entry  */
    Class getClazz();
}
