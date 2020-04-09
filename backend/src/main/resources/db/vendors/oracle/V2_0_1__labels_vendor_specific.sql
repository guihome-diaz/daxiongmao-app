-- Specific Oracle PL/SQL code

-- Indexes (performances). Use "UPPER" or "LOWER" for items that must be unique no matter the case
CREATE INDEX LABELS_CODE_IDX               ON LABELS (UPPER(CODE));
CREATE INDEX LABELS_ACTIVE_CODE_IDX        ON LABELS (UPPER(CODE), IS_ACTIVE);

-- Modification date event handling
CREATE OR REPLACE TRIGGER LABELS_TRIGGER_UPDATE BEFORE UPDATE ON LABELS
for each row
    begin
        :new.MODIFICATION_DATE := sysdate;
        :new.VERSION := :old.VERSION + 1;
    end;
/
