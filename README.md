

Requirements:
* This application requires an Oracle database to start
* 

Design key points: 
* Standalone application
* All services are stateless
* Database setup is done using **Flyway**. DB scripts are in [src/main/resources/db]


Implementation highlights/specifics:
* Parameters are generic. You can have any kind of parameter and dynamically cast it to a particular Object.  
* DB entities <-> DTOs mapping is done manually


Security:
* Web-services do not expose the data model but custom DTOs
* 


Testing
* The application contains *unit tests* (methods tests, mocks) and *integration tests* (Spring tests)
* Unit tests are fast
* Integration tests are slower, they are done against an H2 database.
* H2 database is setup for each tests using Flyway. H2 specifics scripts are available in [src/main/resources/vendors/h2]


Uses cases:
* 


Tricks: 
to execute unit test like maven and debug it:
mvn package -Dtest=Class#method -Dmaven.surefire.debug

ex:
mvn package -Dtest=Argon2PasswordHashTest#hashEuropeanPassword -Dmaven.surefire.debug
