-- New monitoring table
CREATE TABLE MONITORING_EVENTS
(
    EVENT_ID                    NUMBER(10,0)                       CONSTRAINT "C_NN_MONITORING_EVENT_ID" NOT NULL,
    EVENT_NATURE                VARCHAR2(50)                       CONSTRAINT "C_NN_MONITORING_EVENT_NATURE" NOT NULL,
    EVENT_TYPE                  VARCHAR2(50)                       CONSTRAINT "C_NN_MONITORING_EVENT_TYPE" NOT NULL,
    EVENT_TIME                  TIMESTAMP        DEFAULT SYSDATE   CONSTRAINT "C_NN_MONITORING_EVENT_TIME" NOT NULL,
    THIRD_PARTY                 VARCHAR2(100),
    EXECUTION_TIME              NUMBER(10,0)                       CONSTRAINT "C_NN_MONITORING_EXEC_TIME" NOT NULL,
    EXECUTION_RESULT            VARCHAR2(50)                       CONSTRAINT "C_NN_MONITORING_EXEC_RESULT" NOT NULL,
    ITEM_1                      VARCHAR2(255),
    ITEM_2                      VARCHAR2(255),
    ITEM_3                      VARCHAR2(255),
    COMMENTS                    VARCHAR2(4000),
    CONSTRAINT MONITORING_EVENTS_PK PRIMARY KEY (EVENT_ID)
);

-- Create sequence to manage IDs
CREATE SEQUENCE SEQ_MONITORING_EVENTS;



-- **************************
-- Indexes (performances)
-- **************************
--  To keep good performances please query the DB as follow:
-- 1. event_nature
-- 2. event_type
-- 3. third_party
-- 4. event_time
-- 5. execution_time
-- 6. execution_result

-- Filter events by type: performance / functional
CREATE INDEX MNTR_EVENTS_NATURE_IDX             ON MONITORING_EVENTS (EVENT_NATURE);
-- Filter events by nature: web-service, database, email, file access, computation, etc.
CREATE INDEX MNTR_EVENTS_TYPE_IDX               ON MONITORING_EVENTS (EVENT_TYPE, THIRD_PARTY);
-- Filter events by target system (name of the third party: sms provider, google, etc.)
CREATE INDEX MNTR_EVENTS_THIRD_PARTY_IDX        ON MONITORING_EVENTS (THIRD_PARTY);
-- Filter events by creation date
CREATE INDEX MNTR_EVENTS_CREATION_TIME_IDX      ON MONITORING_EVENTS (EVENT_TIME);
-- Count success / errors
CREATE INDEX MNTR_EVENTS_ERR_COUNT_PARTY_IDX    ON MONITORING_EVENTS (THIRD_PARTY, EXECUTION_RESULT);
CREATE INDEX MNTR_EVENTS_ERR_COUNT_TYPE_IDX     ON MONITORING_EVENTS (EVENT_TYPE, EXECUTION_RESULT);
-- Filter events by execution time and creation date
--  (i) filters below are highly used for scheduled reporting
CREATE INDEX MNTR_EVENTS_TIME_CRT_EXEC_IDX      ON MONITORING_EVENTS (EVENT_TIME, EXECUTION_TIME);
CREATE INDEX MNTR_EVENTS_PARTY_CRT_EXEC_IDX     ON MONITORING_EVENTS (THIRD_PARTY, EVENT_TIME, EXECUTION_TIME);
CREATE INDEX MNTR_EVENTS_TYPE_CRT_EXEC_IDX      ON MONITORING_EVENTS (EVENT_TYPE, EVENT_TIME, EXECUTION_TIME);
CREATE INDEX MNTR_EVENTS_TYP_PRT_CRT_EXEC_IDX   ON MONITORING_EVENTS (EVENT_TYPE, THIRD_PARTY, EVENT_TIME, EXECUTION_TIME);


-- *************
-- Description
-- *************
COMMENT ON COLUMN MONITORING_EVENTS.EVENT_ID         IS 'Database identifier managed by a dedicated sequence';
COMMENT ON COLUMN MONITORING_EVENTS.EVENT_NATURE     IS 'Nature of the event (performance,functional, etc.)';
COMMENT ON COLUMN MONITORING_EVENTS.EVENT_TYPE       IS 'Type of the event to track [web-service,database,email,file,inner algorithms]';
COMMENT ON COLUMN MONITORING_EVENTS.EVENT_TIME       IS 'Date-time of the event';
COMMENT ON COLUMN MONITORING_EVENTS.THIRD_PARTY      IS 'Name of the 3rd party (Google, Facebook, OVH, etc.)';
COMMENT ON COLUMN MONITORING_EVENTS.EXECUTION_TIME   IS 'Execution duration in milliseconds (ms)';
COMMENT ON COLUMN MONITORING_EVENTS.EXECUTION_RESULT IS 'To track down success and errors. This is useful to compare timing issues on failure (SUCCESS, FAILURE)';
COMMENT ON COLUMN MONITORING_EVENTS.ITEM_1           IS 'Generic column to organize report contents. PERFORMANCE report: item 1 = Class name';
COMMENT ON COLUMN MONITORING_EVENTS.ITEM_2           IS 'Generic column to organize report contents. PERFORMANCE report: item 2 = Method name (arguments are not provided)';
COMMENT ON COLUMN MONITORING_EVENTS.ITEM_3           IS 'Generic column to organize report contents. PERFORMANCE report: item 3 = Functional workflow';
COMMENT ON COLUMN MONITORING_EVENTS.COMMENTS         IS 'Free text field to describe the situation. Might be useful sometimes (content truncate at 4000)';


