package eu.daxiongmao.core.api.error;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Representation of an error that is part of a chain.<br>
 *     This is often use for validation to return many validation errors at once
 * @author Guillaume Diaz (implementation)
 * @version 1.0
 * @since 2020/02
 */
@Getter
@Setter
@ToString(of = { "object", "field", "rejectedValue", "message" })
@AllArgsConstructor
@JsonRootName("validationError")
public class ApiValidationError implements Serializable {

    private static final long serialVersionUID = -20200227L;

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
