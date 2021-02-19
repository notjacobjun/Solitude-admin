-- include this if for encryption
CREATE EXTENSION IF NOT EXISTS "pgcrypto"

DROP TABLE IF EXISTS Users
CREATE TABLE Users(id PRIMARY KEY, userName varchar(255), email varchar(255), phoneNumber integer);

-- make sure to properly map the foriegn key relatinnships
DROP TABLE IF EXISTS BookingEvent
CREATE TABLE BookingEvent(id PRIMARY KEY, eventName varchar(255), email varchar(255), CONSTRAINT fk_location FOREIGN KEY (locationID) REFERENCES Location(locationID), description varchar(255), partyNumber integer, checkedIn boolean, startTime DATE NOT NULL, endTime DATE NOT NULL); 

DROP TABLE IF EXISTS Location 
CREATE TABLE Location(locationID PRIMARY KEY, locationName varchar(255), maxCapacity integer, current#OfAttendees integer);

DROP TABLE IF EXISTS Admin 
CREATE TABLE Admin(adminName varchar(255), email varchar(255), adminPassword varchar(255));
-- -- include this if for encryption
-- -- CREATE EXTENSION IF NOT EXISTS "pgcrypto"
-- SET DATABASE_URL = soltiudeadmin:AQYJ@localhost/soltiudeadmin
--
-- DROP TABLE IF EXISTS Users
-- CREATE TABLE Users(id PRIMARY KEY, userName varchar(255), email varchar(255), phoneNumber integer);
--
--
-- DROP TABLE IF EXISTS BookingEvent
-- CREATE TABLE BookingEvent(id PRIMARY KEY, eventName varchar(255), email varchar(255), CONSTRAINT fk_location FOREIGN KEY (locationID) REFERENCES Location(locationID), description varchar(255), partyNumber integer, checkedIn boolean, startTime DATE NOT NULL, endTime DATE NOT NULL);
--
-- DROP TABLE IF EXISTS Location
-- CREATE TABLE Location(locationID PRIMARY KEY, locationName varchar(255), maxCapacity integer, current#OfAttendees integer);
--
-- DROP TABLE IF EXISTS Admin
-- CREATE TABLE Admin(adminName varchar(255), email varchar(255), adminPassword varchar(255));
