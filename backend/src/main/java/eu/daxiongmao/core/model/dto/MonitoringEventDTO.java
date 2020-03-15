package eu.daxiongmao.core.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Monitoring event: not functional data that aims to improve overall performances, stability and success rate.
 * It is also useful to detect the 20% of the code that brings 80% of the value.
 * @version 1.0 - 2020/03
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "eventName", "eventType", "thirdParty", "eventTime", "executionTimeInMs", "executionResult", "item1", "item2", "item3"})
@EqualsAndHashCode(of = {"eventName", "eventType", "thirdParty", "eventTime", "executionTimeInMs" })
public class MonitoringEventDTO implements Serializable {

    private static final long serialVersionUID = 20200301;

    /** Nature of the event (performance,functional, etc.) */
    @NotBlank
    @Max(50)
    private String eventName;

    /** Type of the event to track [web-service,database,email,file,inner algorithms] */
    @NotBlank
    @Max(50)
    private String eventType;

    /** Date-time of the event */
    private Date eventTime;

    /** Name of the 3rd party (Google, Facebook, OVH, etc.) */
    @Max(100)
    private String thirdParty;

    /** Execution duration in milliseconds (ms) */
    private Long executionTimeInMs;

    /** To track down success and errors. This is useful to compare timing issues on failure (SUCCESS, FAILURE) */
    @Max(50)
    private String executionResult;

    /** Generic column to organize report contents. PERFORMANCE report: item 1 = Class name */
    @Max(255)
    private String item1;

    /** Generic column to organize report contents. PERFORMANCE report: item 2 = Method name (arguments are not provided) */
    @Max(255)
    private String item2;

    /** Generic column to organize report contents. PERFORMANCE report: item 3 = Functional workflow */
    @Max(255)
    private String item3;

    /** Free text field to describe the situation. Might be useful sometimes (content truncate at 4000) */
    @Max(4000)
    private String comments;

    public MonitoringEventDTO() {
        this.eventTime = new Date();
    }
}
