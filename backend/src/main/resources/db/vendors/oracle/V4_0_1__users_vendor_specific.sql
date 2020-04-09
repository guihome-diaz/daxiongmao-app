-- Specific Oracle PL/SQL code

-- Indexes (performances). Use "UPPER" or "LOWER" for items that must be unique no matter the case
CREATE UNIQUE INDEX USERS_USERNAME_IDX          ON USERS (UPPER(USERNAME));
CREATE UNIQUE INDEX USERS_ACTIVE_USERNAME_IDX   ON USERS (UPPER(USERNAME), IS_ACTIVE);
CREATE UNIQUE INDEX USERS_EMAIL_IDX             ON USERS (LOWER(EMAIL));
CREATE UNIQUE INDEX USERS_ACTIVE_EMAIL_IDX      ON USERS (LOWER(EMAIL), IS_ACTIVE);

-- Modification date event handling
CREATE OR REPLACE TRIGGER USERS_TRIGGER_UPDATE BEFORE UPDATE ON USERS
for each row
    begin
        :new.MODIFICATION_DATE := sysdate;
        :new.VERSION := :old.VERSION + 1;
    end;
/

