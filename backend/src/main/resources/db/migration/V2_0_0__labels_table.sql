-- Labels table
-- For translation and labeling.
-- For instance, this is used to translate error messages and codes - as well as various business information
--
CREATE TABLE LABELS
(
    LABEL_ID                    NUMBER(10,0)         CONSTRAINT "C_NN_LABELS_ID" NOT NULL,
    CODE                        VARCHAR2(250 CHAR)   CONSTRAINT "C_NN_LABELS_CODE" NOT NULL,
    LANG_FR                     VARCHAR2(2000 CHAR),
    LANG_EN                     VARCHAR2(2000 CHAR),
    LANG_ZH                     VARCHAR2(2000 CHAR),
    -- common attributes
    IS_ACTIVE                   NUMBER(1, 0)         DEFAULT 1 CONSTRAINT "C_NN_LABELS_IS_ACTIVE" NOT NULL,
    CREATION_DATE               TIMESTAMP            DEFAULT SYSDATE CONSTRAINT "C_NN_LABELS_CREATION_DATE" NOT NULL,
    MODIFICATION_DATE           TIMESTAMP            DEFAULT SYSDATE CONSTRAINT "C_NN_LABELS_MODIFICATION_DATE" NOT NULL,
    VERSION                     NUMBER(10,0)         DEFAULT 1 CONSTRAINT "C_NN_LABELS_VERSION" NOT NULL,
    CONSTRAINT LABELS_PK PRIMARY KEY (LABEL_ID)
);

-- Create sequence to manage IDs
CREATE SEQUENCE SEQ_LABELS;

-- Indexes (performances)
CREATE INDEX LABELS_ACTIVE_IDX             ON LABELS (IS_ACTIVE);

-- Object description
COMMENT ON COLUMN LABELS.LABEL_ID             IS 'Unique identifier';
COMMENT ON COLUMN LABELS.CODE                 IS 'Label code, in UPPER case (ex: APPLICATION.TITLE)';
COMMENT ON COLUMN LABELS.LANG_FR              IS 'Text in French';
COMMENT ON COLUMN LABELS.LANG_EN              IS 'Text in English';
COMMENT ON COLUMN LABELS.LANG_ZH              IS 'Text in Chinese';
COMMENT ON COLUMN LABELS.IS_ACTIVE            IS 'Boolean flag. MANDATORY. "1" to use the object, "0" to block usage';
COMMENT ON COLUMN LABELS.CREATION_DATE        IS 'Object creation date-time';
COMMENT ON COLUMN LABELS.MODIFICATION_DATE    IS 'Object last modification date-time';
COMMENT ON COLUMN LABELS.VERSION              IS 'Object version. This is incremented at each operation';