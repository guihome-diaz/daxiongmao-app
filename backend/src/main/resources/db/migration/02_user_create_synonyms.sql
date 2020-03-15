--
-- Script to create synonyms from "daxiongmao_owner" to "daxiongmao_user"
-- This avoid to use the syntax "DAXIONGMAO_OWNER"."table"
--
-- (i) this script must be executed as "daxiongmao_user"
--     this script must be executed AFTER the 'grant_rights' script
-- version 1.0 (2019/12)
--
SET serveroutput ON;

-- tables
create or replace synonym USERS for daxiongmao_owner.USERS;
create or replace synonym PARAMETERS for daxiongmao_owner.PARAMETERS;
create or replace synonym MONITORING_EVENTS for daxiongmao_owner.MONITORING_EVENTS;
create or replace synonym LABELS for daxiongmao_owner.LABELS;
-- sequences
create or replace synonym SEQ_USERS for daxiongmao_owner.SEQ_USERS;
create or replace synonym SEQ_PARAMETERS for daxiongmao_owner.SEQ_PARAMETERS;
create or replace synonym SEQ_MONITORING_EVENTS for daxiongmao_owner.SEQ_MONITORING_EVENTS;
create or replace synonym SEQ_LABELS for daxiongmao_owner.SEQ_LABELS;


declare
    cursor c1 is select table_name from all_tables where owner='daxiongmao_owner';
    cursor c2 is select sequence_name from all_sequences where sequence_owner='daxiongmao_owner';
    cmd varchar2(200);
begin
    /* script for TABLES */
    for c in c1 loop
            cmd := 'CREATE OR REPLACE SYNONYM '||c.table_name||' FOR DAXIONGMAO_OWNER.'||c.table_name;
            execute immediate cmd;
        end loop;
    /* script for SYNONYMS */
    for c in c2 loop
            cmd := 'CREATE OR REPLACE SYNONYM '||c.sequence_name||' FOR DAXIONGMAO_OWNER.'||c.sequence_name;
            execute immediate cmd;
        end loop;
end;


