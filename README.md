# _Band and venue tracker_

#### _Establishes a many-to-many relationship between bands and venues they have played at, saving the info to a sql database, 4/13/2016_

#### By _Kyle Wolfson_

## Description

_Establishes a many-to-many relationship between bands and venues they have played at, saving the info to a sql database and allowing manipulation of the bands, their relationships to the venues and the ability to delete them, 4/13/2016_

## Setup/Installation Requirements

* _Open psql_
* _run command in sql: CREATE DATABASE band_tracker;_
* _run command in terminal (from the band_tracker directory): psql band_tracker < band_tracker.sql_
* _run command from terminal: gradle run_
* _access the app via a browser at localhost:4567_

_The above instructions will import the database with a few initial entries, feel free to empty the tables out if you would prefer. You may also CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker; if you would like to run tests with gradle_

## Known Bugs

_No bugs at this time_

## Support and contact details

_wolfsonk@gmail.com_

## Technologies Used

_gradle, java, psql, Spark, Velocity_

### License

*MIT license*

Copyright (c) 2016 **_Kyle Wolfson_**
