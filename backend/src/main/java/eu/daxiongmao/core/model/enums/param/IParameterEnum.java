package eu.daxiongmao.core.model.enums.param;

/**
 * Description of a parameter enum
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
public interface IParameterEnum {

    /** To retrieve the parameter name corresponding to current ENUM's entry
     *  @return parameter name (string) saved in DB for that enum's entry */
    String getParamName();

    /** To retrieve the class corresponding to current ENUM's entry
     * @return parameter java class (type) related to current enum's entry  */
    Class getClazz();
}
