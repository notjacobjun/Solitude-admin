-- include this if for encryption
-- CREATE EXTENSION IF NOT EXISTS "pgcrypto"
SET DATABASE_URL = soltiudeadmin:AQYJ@localhost/soltiudeadmin

DROP TABLE IF EXISTS Users
CREATE TABLE Users(userId BIGINT PRIMARY KEY, userName varchar(255), userEmail varchar(255), phoneNumber integer);

DROP TABLE IF EXISTS BookingEvent
CREATE TABLE BookingEvent(eventId BIGINT PRIMARY KEY, eventName varchar(255), attendeeEmail varchar(255), CONSTRAINT fk_location FOREIGN KEY (locationID) REFERENCES Location(locationID), description varchar(255), partyNumber integer, startTime varchar(255) NOT NULL, endTime varchar(255) NOT NULL);

DROP TABLE IF EXISTS Location
-- TODO configure the events column within this table consider using JSONB or arrays
CREATE TABLE Location(locationID BIGINT PRIMARY KEY, locationName varchar(255), maxCapacity integer, currentNumOfAttendees integer, events Location[]);

DROP TABLE IF EXISTS Admin
CREATE TABLE Admin(adminId BIGINT PRIMARY KEY, adminName varchar(255), adminEmail varchar(255), adminPassword varchar(255));