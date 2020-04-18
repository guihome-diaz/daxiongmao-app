# Oracle DB setup

This page contains key steps to set up the Oracle database locally.

## Requirements

### Oracle XE

This is a full-featured Oracle database server that can be used for development purposes. See [Oracle Database XE](https://www.oracle.com/database/technologies/appdev/xe.html)
* Download the version *18c*
* Install it on your server / workstation 

### Oracle SQL developer

Oracle SQL developer is an excellent SQL client to use with Oracle DB.
* Download the latest version from [Oracle SQL developer](https://www.oracle.com/fr/database/technologies/appdev/sql-developer.html) 
* Install it on your workstation

## Connection to Oracle XE as SYS-ADMIN

### With SQL developer
* Open SQL developer
* If the server is running on the same machine as SQL developer, then the "**XE**" connection is proposed 
* User: ```SYSTEM```
* Password: *secret chose during server setup*
* Host: ```localhost```
* Port: ```1521```
* Service name: ```XE```

**Confirm that the connection is OK** by running the following command: 
```sql
select instance_name, version, status from v$instance;
```

Expectation:

| Instance_name |  Version   | Status |
| ------------- | ---------- | ------ |
| XE            | 18.0.0.0.0 | OPEN   |

Oracle 18c XE can have up to three Pluggable Databases (PDBs), and one has already been created as part of the installation.
```sql
select con_id, name, open_mode from v$containers;
```

Expectations:

|  CON_ID  |  NAME    |
|--------- | -------- |
|     1    | CDB$ROOT |
|     2    | PDB$SEED |
|     3    | XEPDB1   |

In this case :
* `CDB$ROOT` is the Container Database
* `PDB$SEED` is a read-only template for creating PDBS
* `XEPDB1` is a PDB

## Create user and schema

Link to a [good article from ORACLE blog](https://blogs.oracle.com/sql/how-to-create-users-grant-them-privileges-and-remove-them-in-oracle-database) that explains how to configure ORACLE schemas.

### Create OWNER user

In Oracle a schema must be linked to an user. A common practice is to use an "owner" account. 
* **Owner is responsible of the STRUCTURE**, he can create tables, views, procedures, sequences and triggers.
* **Owner must grant** CRUD (Create, Read, Update, Delete) **rights to working user for *each* table** 

Execute the following commands as SYSADMIN: 

```sql
-- Allow SYSTEM user to execute scripts for the current session
alter session set "_ORACLE_SCRIPT" = true;

-- ******* Initialize schemas ******
-- Create user and corresponding schema
CREATE USER daxiongmao_owner IDENTIFIED BY secretPassword;
-- Allow user login
GRANT CREATE SESSION TO daxiongmao_owner;

-- ******** Configure owner *******
-- Allow user to create tables
GRANT CREATE TABLE TO daxiongmao_owner;
-- Allow user to create views, procedures and sequences
GRANT create view, create procedure, create sequence to daxiongmao_owner;
-- Allow user to create trigger (Ex: modification date change on each update)
GRANT create trigger to daxiongmao_owner;
-- Let user write in tablespace
ALTER USER daxiongmao_owner QUOTA UNLIMITED ON USERS;
```

### Create WORKING user

The **working user is responsible of the DATA**, he can create insert / update / delete / select inside tables he has access to.

```sql
-- ********* Configure user *******
-- Create read user
grant create session to daxiongmao_user identified by secretPassword;

-- Let "user" create synonyms on "owner" 
grant create synonym to daxiongma_owner;
grant create synonym to daxiongma_user;
grant create any synonym to daxiongmao_user;
```

## Create tables

To create tables you must use **OWNER** account. See [Flyway scripts](https://github.com/guihome-diaz/daxiongmao-app/tree/master/backend/src/main/resources/db)

## Grant rights (as owner)

As **OWNER** you can grant rights to WORKING user on tables and sequences. 

(i) *This script must be executed after each structure change*

```sql
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
```

## Create synonyms (as user)

As **USER**, to avoid using "owner.tableName" you need to create synonyms.

(i) *This script must be executed after each structure change, AFTER the owner grants*

```sql
--
-- Script to create synonyms from "daxiongmao_owner" to "daxiongmao_user"
-- This avoid to use the syntax "DAXIONGMAO_OWNER"."table"
--
-- (i) this script must be executed as "daxiongmao_user"
--     this script must be executed AFTER the 'grant_rights' script
-- version 1.0 (2019/12)
--
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
```
