# Solitude-admin
Hack 2021 hackathon project

## Background

This project uses spring-boot(2.4.2), postgres and firebase auth.

It generates BookingEvents from the appUser so that the admin can verify and check in users while also having the ability to checkout the users. 

The admin can also register a Location to the app and set the maximum number of attendees.

The admin also has the ability to manage each location and view the current statistics such as the current number of attendees

## API

We are using the Google Calendar API for the event creation and management of these events. 

We also implemented the check in and checkout API. This checks the appUser's email with the email used to register for the event. 

## Local build

Run `mvn -f pom.xml clean package` to build the target jar file, provided that Maven is installed.

Then run `java -jar target/Solitude-admin-0.0.1.jar`

# Docker :whale:

To build the jar file in a Maven Container:

`$ docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn package`

This application requires Firebase Admin SDK included. Get the service account from Firebase console and then place it in the root of your cloned project and name it `solitude-credentials.json`

Then,

`docker build .`

Then to run this entire app with a dependency of Postgresql on your local machine via Docker Compose

`docker-compose up -d`

# Firebase

This requires Firebase Admin SDK loaded and saved into a file `solitude-credentials.json` and placed in the root of the project directory. Generate a secret key from your Firebase Project settings > Service Accounts, save it as a JSON file.