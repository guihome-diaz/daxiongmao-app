-- UNDO script
-- ***********************
-- Business settings
-- ***********************
DELETE FROM PARAMETERS WHERE PARAM_NAME IN (
        'APP.DEFAULT_LANG',
        'APP.VERSION'
);


-- ***********************
-- Monitoring settings
-- ***********************


-- ***********************
-- Technical settings
-- ***********************
DELETE FROM PARAMETERS WHERE PARAM_NAME IN (
        'APP.MODE',
        'APP.ENVIRONMENT',
        'APP.CACHE.REFRESH.MINIMUM_DELAY_BETWEEN_REFRESH_IN_SECONDS',
        'APP.DB.VERSION',
        'WEB-SERVICES.JSON.INCLUDE_STACKTRACE_ON_ERROR'
);


-- ***********************
-- Test settings
-- ***********************
DELETE FROM PARAMETERS WHERE PARAM_NAME IN (
        'APP.DB.PASSWORD'
);
