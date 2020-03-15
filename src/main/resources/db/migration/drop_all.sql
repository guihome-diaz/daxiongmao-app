--
-- Script to drop all tables and sequences of the current user
-- (i) this script must be executed as "daxiongmao_owner"
-- version 1.0 (2019/12)
--
DECLARE
    alter_cmd VARCHAR2(200);
BEGIN
    ------------------
    -- Drop all tables
    ------------------
    FOR I IN
        (SELECT table_name FROM user_tables)
        LOOP
            alter_cmd := 'drop table ' || I.table_name || ' cascade constraints';
            DBMS_OUTPUT.PUT_LINE(alter_cmd);
            EXECUTE immediate(alter_cmd);
        END LOOP;
    ---------------------
    -- Drop all sequences
    ---------------------
    FOR I IN
        (SELECT sequence_name FROM user_sequences)
        LOOP
            alter_cmd := 'drop sequence ' || I.sequence_name;
            DBMS_OUTPUT.PUT_LINE(alter_cmd);
            EXECUTE immediate(alter_cmd);
        END LOOP;
END;
/

COMMIT;
EXIT;