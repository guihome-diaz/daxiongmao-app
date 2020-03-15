-- Specific Oracle PL/SQL code

-- Indexes (performances). Use "UPPER" or "LOWER" for items that must be unique no matter the case
CREATE UNIQUE INDEX PARAMS_PARAM_NAME_IDX    ON PARAMETERS (UPPER(PARAM_NAME) ASC);
CREATE UNIQUE INDEX PARAMS_ACTIVE_PARAM_IDX  ON PARAMETERS (UPPER(PARAM_NAME) ASC, IS_ACTIVE);

-- Trigger for modification date
CREATE OR REPLACE TRIGGER PARAMETERS_TRIGGER_UPDATE BEFORE UPDATE ON PARAMETERS
for each row
    begin
        :new.MODIFICATION_DATE := sysdate;
        :new.VERSION := :old.VERSION + 1;
    end;
/

