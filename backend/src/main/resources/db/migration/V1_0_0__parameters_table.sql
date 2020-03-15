-- parameters table
CREATE TABLE PARAMETERS
  (
    PARAM_ID                    NUMBER(10,0)         CONSTRAINT "C_NN_PARAMS_ID" NOT NULL,
    PARAM_NAME                  VARCHAR2(100 CHAR)   CONSTRAINT "C_NN_PARAMS_NAME" NOT NULL,
    PARAM_VALUE                 VARCHAR2(255 CHAR)   CONSTRAINT "C_NN_PARAMS_VALUE" NOT NULL,
    PARAM_TYPE                  VARCHAR2(200 CHAR)   CONSTRAINT "C_NN_PARAMS_TYPE" NOT NULL,
    IS_ACTIVE                   NUMBER(1, 0)         DEFAULT 1 CONSTRAINT "C_NN_PARAMS_IS_ACTIVE" NOT NULL,
    IS_SENSITIVE                NUMBER(1, 0)         DEFAULT 0 CONSTRAINT "C_NN_PARAMS_IS_SENSITIVE" NOT NULL,
    DESCRIPTION                 VARCHAR2(1500 CHAR),
    CREATION_DATE               TIMESTAMP            DEFAULT SYSDATE CONSTRAINT "C_NN_PARAMS_CREATION_DATE" NOT NULL,
    MODIFICATION_DATE           TIMESTAMP            DEFAULT SYSDATE CONSTRAINT "C_NN_PARAMS_MODIFICATION_DATE" NOT NULL,
    VERSION                     NUMBER(10,0)         DEFAULT 1 CONSTRAINT "C_NN_PARAMS_VERSION" NOT NULL,
    CONSTRAINT PARAMS_PK PRIMARY KEY (PARAM_ID)
  );

-- Create sequence to manage IDs
CREATE SEQUENCE SEQ_PARAMETERS;

-- Indexes (performances)
CREATE INDEX PARAMS_ACTIVE_IDX               ON PARAMETERS (IS_ACTIVE);

-- Object description
COMMENT ON COLUMN PARAMETERS.PARAM_ID            IS 'Unique identifier';
COMMENT ON COLUMN PARAMETERS.PARAM_NAME          IS 'Parameter name (upper-case)';
COMMENT ON COLUMN PARAMETERS.PARAM_VALUE         IS 'Parameter value';
COMMENT ON COLUMN PARAMETERS.PARAM_TYPE          IS 'Parameter type (java class, full qualified name: java.lang.String, java.lang.Integer, custom enum etc.)';
COMMENT ON COLUMN PARAMETERS.DESCRIPTION         IS 'Parameter description';
COMMENT ON COLUMN PARAMETERS.IS_ACTIVE           IS 'Boolean flag. MANDATORY. "1" to use the object, "0" to block usage';
COMMENT ON COLUMN PARAMETERS.IS_SENSITIVE        IS 'Boolean flag. MANDATORY. "0" if the information can be displayed, "1" if it is confidential (ex: password, secure path, etc.)';
COMMENT ON COLUMN PARAMETERS.CREATION_DATE       IS 'Object creation date-time';
COMMENT ON COLUMN PARAMETERS.MODIFICATION_DATE   IS 'Object last modification date-time';
COMMENT ON COLUMN PARAMETERS.VERSION             IS 'Object version. This is incremented at each operation';
