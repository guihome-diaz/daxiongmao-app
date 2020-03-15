--
-- Script to grant data R/W to "daxiongmao_user" on all tables
--
-- (i) this script must be executed as "daxiongmao_owner"
-- version 1.0 (2019/12)
--
declare
    cursor c1 is select table_name from user_tables;
    cursor c2 is select sequence_name from user_sequences;
    cmd varchar2(2000);
begin
    /* Loop over all tables */
    for c in c1 loop
        cmd := 'GRANT SELECT, INSERT, UPDATE, DELETE ON ' || c.table_name || ' TO daxiongmao_user';
        execute immediate cmd;
    end loop;

    /* Loop over all sequences */
    for c in c2 loop
        cmd := 'GRANT SELECT ON ' || c.sequence_name || ' TO daxiongmao_user';
        execute immediate cmd;
    end loop;
end;
/
