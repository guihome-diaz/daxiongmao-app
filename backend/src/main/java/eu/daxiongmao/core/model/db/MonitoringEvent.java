package eu.daxiongmao.core.model.db;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Entity
@Table(name = "MONITORING_EVENTS", indexes = {
        // Filter events by type: performance / functional
        @Index(name = "MNTR_EVENTS_NATURE_IDX", columnList = "EVENT_NATURE"),
        // Filter events by nature: web-service, database, email, file access, computation, etc.
        @Index(name = "MNTR_EVENTS_TYPE_IDX", columnList = "EVENT_TYPE, THIRD_PARTY"),
        // Filter events by target system (name of the third party: sms provider, google, etc.)
        @Index(name = "MNTR_EVENTS_THIRD_PARTY_IDX", columnList = "THIRD_PARTY"),
        // Filter events by creation date
        @Index(name = "MNTR_EVENTS_CREATION_TIME_IDX", columnList = "EVENT_TIME"),
        // Count success | errors
        @Index(name = "MNTR_EVENTS_ERR_COUNT_PARTY_IDX", columnList = "THIRD_PARTY, EXECUTION_RESULT"),
        @Index(name = "MNTR_EVENTS_ERR_COUNT_TYPE_IDX", columnList = "EVENT_TYPE, EXECUTION_RESULT"),
        // Filter events by execution time and creation date
        //  (i) filters below are highly used for scheduled reporting
        @Index(name = "MNTR_EVENTS_TIME_CRT_EXEC_IDX", columnList = "EVENT_TIME, EXECUTION_TIME"),
        @Index(name = "MNTR_EVENTS_PARTY_CRT_EXEC_IDX", columnList = "THIRD_PARTY, EVENT_TIME, EXECUTION_TIME"),
        @Index(name = "MNTR_EVENTS_TYPE_CRT_EXEC_IDX", columnList = "EVENT_TYPE, EVENT_TIME, EXECUTION_TIME"),
        @Index(name = "MNTR_EVENTS_TYP_PRT_CRT_EXEC_IDX", columnList = "EVENT_TYPE, THIRD_PARTY, EVENT_TIME, EXECUTION_TIME")
})
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringEvent implements Serializable {

    private static final long serialVersionUID = 20200301;

    @Id
    @Column(name = "EVENT_ID")
    @SequenceGenerator(name = "seqMonitoringEvents", sequenceName = "SEQ_MONITORING_EVENTS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMonitoringEvents")
    private Long id;

    /** Nature of the event (performance,functional, etc.) */
    @NotBlank
    @Max(50)
    @Column(name = "EVENT_NATURE", nullable = false, length = 50)
    private String eventName;

    /** Type of the event to track [web-service,database,email,file,inner algorithms] */
    @NotBlank
    @Max(50)
    @Column(name = "EVENT_TYPE", nullable = false, length = 50)
    private String eventType;

    /** Date-time of the event */
    @Column(name = "EVENT_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime = new Date();

    /** Name of the 3rd party (Google, Facebook, OVH, etc.) */
    @Max(100)
    @Column(name = "THIRD_PARTY", length = 100)
    private String thirdParty;

    /** Execution duration in milliseconds (ms) */
    @Column(name = "EXECUTION_TIME", nullable = false)
    private long executionTimeInMs;

    /** To track down success and errors. This is useful to compare timing issues on failure (SUCCESS, FAILURE) */
    @Max(50)
    @Column(name = "EXECUTION_RESULT", nullable = false, length = 50)
    private String executionResult;

    /** Generic column to organize report contents. PERFORMANCE report: item 1 = Class name */
    @Max(255)
    @Column(name = "ITEM_1", length = 255)
    private String item1;

    /** Generic column to organize report contents. PERFORMANCE report: item 2 = Method name (arguments are not provided) */
    @Max(255)
    @Column(name = "ITEM_2", length = 255)
    private String item2;

    /** Generic column to organize report contents. PERFORMANCE report: item 3 = Functional workflow */
    @Max(255)
    @Column(name = "ITEM_3", length = 255)
    private String item3;

    /** Free text field to describe the situation. Might be useful sometimes (content truncate at 4000) */
    @Max(4000)
    @Column(name = "COMMENTS", length = 4000)
    private String comments;

}
