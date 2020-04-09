# Daxiongmao application

Application to quick start projects and demonstrate skills.
Licensed under [The MIT License](https://choosealicense.com/licenses/mit/)

--------------------------

## Status

[![Build Status](https://travis-ci.org/guihome-diaz/daxiongmao-app.svg?branch=master)](https://travis-ci.org/guihome-diaz/daxiongmao-app)

[![codecov](https://codecov.io/gh/guihome-diaz/daxiongmao-app/branch/master/graph/badge.svg)](https://codecov.io/gh/guihome-diaz/daxiongmao-app)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bdfa706c3ea347c8b3814243ef026903)](https://www.codacy.com/manual/guihome-diaz/daxiongmao-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=guihome-diaz/daxiongmao-app&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bdfa706c3ea347c8b3814243ef026903)](https://www.codacy.com/manual/guihome-diaz/daxiongmao-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=guihome-diaz/daxiongmao-app&amp;utm_campaign=Badge_Coverage)

## Application purpose and use-cases
This quick-start application contains the following use-cases:
* User self-registration (sign-up)
* User login (sign-in)
* User password reset

Each use-case includes security, monitoring and tests by design.

## Key points

### Design 
* The application is split in 2 parts: backend (Spring-boot 2) / frontend
* All services are stateless

### Security
* In accordance to GDPR, this application only saves needed user data: nothing more. Most of user data is not even mandatory.
* In accordance to GDPR, all actions that are not crucial for business (such as billing) are saved in an anonymous way
* Web-services do not expose the data model but custom DTOs
* Each user password has a dedicated salt generated with **DRBG secure random** (available since Java9)
* Each user password is hashed with **Argon2** algorithm

### Monitoring
* Actions are traced for audit purposes
* Business actions, such as log-in / billing / ordering, are audited: WHO does WHAT and WHEN
* Generic actions, such as page browsing / DB queries / UI actions, are logged in anonymous way for performances and usage tracking

### Testing
* The application contains *unit tests* (methods tests, mocks) and *integration tests* (Spring tests)
* Unit tests are fast
* Integration tests are slower, they are done against an H2 database (in memory).
* H2 database is setup for each tests using Flyway. H2 specifics scripts are available in [backend/src/main/resources/vendors/h2]

### Implementation highlights/specifics
* Parameters are generic. You can have any kind of parameter and dynamically cast it to a particular Object.  
* DB entities <-> DTOs mapping is done manually. .. Yes: this is cumbersome but much faster and easier to debug

## Application setup

### Database and core data
* This application requires an Oracle database to start
* Database setup is done using **Flyway**. DB scripts are in [backend/src/main/resources/db]

## Development tips and tricks

### Debug maven tests

To execute an Unit Test like maven and debug it:
* Pattern: ```mvn package -Dtest=Class#method -Dmaven.surefire.debug```
* Example: ```mvn package -Dtest=Argon2PasswordHashTest#hashEuropeanPassword -Dmaven.surefire.debug```

This is important because sometimes the IDE (Eclipse / IntelliJ) performs hidden magic.

## Author(s)
- [**Guillaume Diaz**](guillaume@qin-diaz.com), repository owner and core developer