# Daxiongma application

Application to quick start projects and demonstrate skills.

Licensed under The MIT License

--------------------------

## Status

[![Build Status](https://travis-ci.org/guihome-diaz/daxiongmao-app.svg?branch=master)](https://travis-ci.org/guihome-diaz/daxiongmao-app)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bdfa706c3ea347c8b3814243ef026903)](https://www.codacy.com/manual/guihome-diaz/daxiongmao-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=guihome-diaz/daxiongmao-app&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bdfa706c3ea347c8b3814243ef026903)](https://www.codacy.com/manual/guihome-diaz/daxiongmao-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=guihome-diaz/daxiongmao-app&amp;utm_campaign=Badge_Coverage)

## Authors
Project history:
- Created by **Daniel Glasson** on 18/05/2014
- Fork by **Guillaume Diaz** on 22/07/2019



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
