
-- ***********************
-- Business settings
-- ***********************
-- Application default language (fallback) in case requested lang is not available
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.DEFAULT_LANG', 'EN', 'java.lang.String', 'Default application language in case translation for requested language does not exists; or that user lang is not available (yet)', 1, 0);
-- Configuration for the public
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.VERSION', '0.1', 'java.lang.String', 'Public version of the application. This is not linked to GIT or Maven versions', 1, 0);



-- ***********************
-- Monitoring settings
-- ***********************


-- ***********************
-- Technical settings
-- ***********************
-- Application mode
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.MODE', 'maintenance', 'java.lang.String', 'Current mode of the application. To get the allowed values see the corresponding Java ENUM in the code', 1, 0);
-- Environment specifics values
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.ENVIRONMENT', 'DEV', 'eu.daxiongmao.travel.model.enums.Environment', 'current environment', 1, 0);
-- Parameters caching
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.CACHE.REFRESH.MINIMUM_DELAY_BETWEEN_REFRESH_IN_SECONDS', '5', 'java.lang.Integer', 'Minimum delay, in seconds, to respect between 2 parameters cache refresh', 1, 0);
-- DB schema version
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.DB.VERSION', '1', 'java.lang.String', 'Current DB schema version', 1, 0);
-- Include exception stacktrace in JSON responses (if "false" stacktrace will not be send)
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'WEB-SERVICES.JSON.INCLUDE_STACKTRACE_ON_ERROR', 'true', 'java.lang.Boolean', 'Boolean flag to include exception stacktrace in JSON error responses or not', 1, 0);





-- ***********************
-- Test settings
-- ***********************
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'APP.DB.PASSWORD', 'fakePassword', 'java.lang.String', 'Current DB password', 1, 1);
