package eu.daxiongmao.core.model.dto;

import eu.daxiongmao.core.utils.StringToClassUtils;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * Application parameter
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(callSuper = true, of = { "paramName", "paramValue", "paramType", "description" })
@EqualsAndHashCode(of = {"paramName", "paramValue", "paramType"})
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDTO implements Serializable {

    private static final long serialVersionUID = 20191205L;

    @NotBlank
    @Max(200)
    private String paramType;
    public Class getParamTypeClass() {
        if (this.paramType == null) {
            return null;
        }
        try {
            return Class.forName(this.paramType);
        } catch (ClassCastException | ClassNotFoundException e) {
            throw new IllegalStateException("Failed to cast parameter type to Java Class", e);
        }
    }

    @NotBlank
    @Max(100)
    private String paramName;
    public void setParamName(String paramName) {
        if (StringUtils.isNotBlank(paramName)) {
            this.paramName = paramName.trim().toUpperCase();
        } else {
            this.paramName = null;
        }
    }

    @NotBlank
    @Max(255)
    private String paramValue;

    @Max(1500)
    private String description;

    /** Boolean flag. MANDATORY. "1" to use the object, "0" to block usage */
    private Boolean isActive;

    /**
     * To convert a given String value into a particular output type
     * @param clazz output class to convert the String into
     * @param <T> String class
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     */
    public <T> T getValue(final Class<T> clazz) {
        return StringToClassUtils.getValue(this.paramValue, clazz);
    }

    /**
     * To convert a given String value into a particular output type (according to registered type).
     * Use {@link ParameterDTO#getParamTypeClass()} to retrieve object's class
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     * @throws ClassCastException requested class does not exists
     */
    public Object getValue() {
        return StringToClassUtils.getValue(this.paramValue, this.paramType);
    }
}
