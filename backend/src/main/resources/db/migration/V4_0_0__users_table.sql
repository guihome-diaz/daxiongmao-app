-- User table
CREATE TABLE USERS
  (
    USER_ID                     NUMBER(10,0)         CONSTRAINT "C_NN_USERS_ID" NOT NULL,
    FIRST_NAME                  VARCHAR2(100 CHAR),
    SURNAME                     VARCHAR2(200 CHAR),
    LANG_CODE                   VARCHAR2(2 CHAR)     CONSTRAINT "C_NN_USERS_LANG_CODE" NOT NULL,
    USERNAME                    VARCHAR2(50 CHAR)    CONSTRAINT "C_NN_USERS_USERNAME" NOT NULL,
    EMAIL                       VARCHAR2(255 CHAR)   CONSTRAINT "C_NN_USERS_EMAIL" NOT NULL,
    PHONE_NUMBER                VARCHAR2(20 CHAR)    CONSTRAINT "C_NN_USERS_PHONE" NOT NULL,
    STATUS                      VARCHAR2(255 CHAR)   CONSTRAINT "C_NN_USERS_STATUS" NOT NULL,
    IS_ACTIVE                   NUMBER(1, 0)         DEFAULT 0 CONSTRAINT "C_NN_USERS_IS_ACTIVE" NOT NULL,
    ACTIVATION_KEY              VARCHAR2(255 CHAR)   CONSTRAINT "C_NN_USERS_ACTIVATION_KEY" NOT NULL,
    DATE_EMAIL_CONFIRMED        TIMESTAMP NULL,
    PASSWORD_HASH               VARCHAR2(255 CHAR),
    PASSWORD_SALT               VARCHAR2(255 CHAR)   CONSTRAINT "C_NN_USERS_PWD_SALT" NOT NULL,
    HASH_ALGORITHM              VARCHAR2(50 CHAR)    CONSTRAINT "C_NN_USERS_HASH_ALGO" NOT NULL,
    SALT_ALGORITHM              VARCHAR2(50 CHAR)    CONSTRAINT "C_NN_USERS_SALT_ALGO" NOT NULL,
    PASSWORD_LAST_CHANGE        TIMESTAMP NULL,
    CREATION_DATE               TIMESTAMP            DEFAULT SYSDATE CONSTRAINT "C_NN_USERS_CREATION_DATE" NOT NULL,
    MODIFICATION_DATE           TIMESTAMP            DEFAULT SYSDATE CONSTRAINT "C_NN_USERS_MODIFICATION_DATE" NOT NULL,
    VERSION                     NUMBER(10,0)         DEFAULT 1 CONSTRAINT "C_NN_USERS_VERSION" NOT NULL,
    CONSTRAINT USERS_PK PRIMARY KEY (USER_ID)
  );

-- Create sequence to manage IDs
CREATE SEQUENCE SEQ_USERS;

-- Indexes (performances)
CREATE INDEX USERS_NAME_IDX                     ON USERS (FIRST_NAME, SURNAME);

-- Object description
COMMENT ON COLUMN USERS.USER_ID               IS 'Unique identifier';
COMMENT ON COLUMN USERS.FIRST_NAME            IS 'First name (french: prenom)';
COMMENT ON COLUMN USERS.SURNAME               IS 'Last name (french: nom de famille)';
COMMENT ON COLUMN USERS.LANG_CODE             IS 'Preferred language (ex: EN, FR, DE, etc.)';
COMMENT ON COLUMN USERS.USERNAME              IS 'User login. MANDATORY. This must be unique, even if the user is disabled no one can take his username';
COMMENT ON COLUMN USERS.EMAIL                 IS 'User email. MANDATORY. This must be unique both in EMAIL and BACKUP_EMAIL columns. User must validate his email before using the application';
COMMENT ON COLUMN USERS.PHONE_NUMBER          IS 'User phone number, required for 2 factor authentication. It must include the country code. ex: +352 for Luxembourg ; +33 for France';
COMMENT ON COLUMN USERS.STATUS                IS 'User status. MANDATORY. This represents his current status if enabled';
COMMENT ON COLUMN USERS.ACTIVATION_KEY        IS 'Activation key. MANDATORY. This is required to confirm the user registration and activate the account';
COMMENT ON COLUMN USERS.DATE_EMAIL_CONFIRMED  IS 'To know when the user confirmed his email - if he did so. This is required for anti-phishing reasons';
COMMENT ON COLUMN USERS.PASSWORD_HASH         IS 'User password. This can be let NULL if user wants to validate his email first. Important: like all modern apps, we do NOT store the user password';
COMMENT ON COLUMN USERS.PASSWORD_SALT         IS 'Security salt. MANDATORY. (random value) that is required to compute the user password hash. Every user have different salts';
COMMENT ON COLUMN USERS.HASH_ALGORITHM        IS 'Password hash algorithm. MANDATORY. This is required to compute the password hash. Security can change over time, that is why we must store the algorithm used for each user';
COMMENT ON COLUMN USERS.SALT_ALGORITHM        IS 'Salt (secure random) algorithm. MANDATORY. This is required to compute the password hash. Security can change over time, that is why we must store the algorithm used for each user';
COMMENT ON COLUMN USERS.PASSWORD_LAST_CHANGE  IS 'Date time of the last password change. This is important to notify user to change his password periodically for security reasons';
COMMENT ON COLUMN USERS.IS_ACTIVE             IS 'Boolean flag. MANDATORY. "1" to use the object, "0" to block usage';
COMMENT ON COLUMN USERS.CREATION_DATE         IS 'Object creation date-time';
COMMENT ON COLUMN USERS.MODIFICATION_DATE     IS 'Object last modification date-time';
COMMENT ON COLUMN USERS.VERSION               IS 'Object version. This is incremented at each operation';