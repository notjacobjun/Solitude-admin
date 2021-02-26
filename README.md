# Solitude-admin
Hack 2021 hackathon project

## Background

This project uses spring-boot postgres and firebase.

It generates BookingEvents from the user so that the admin can verify and check in users while also having the ability to checkout the users. 

The admin can also register a Location to the app and set the maximum number of attendees.

The admin also has the ability to manage each location and view the current statistics such as the current number of attendees

## API

We are using the Google Calendar API for the event creation and management of these events. 

We also implemented the check in and checkout API. This checks the user's email with the email used to register for the event. 

## Firebase

This requires Firebase Admin SDK loaded and saved into a file `solitude-credentials.json` and placed in the root of the project directory. Generate a secret key from your Firebase Project settings > Service Accounts, save it as a JSON file.