# Asteroid Radar App
Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view
all the detected asteroids in a period of time, their data and let the user know if downloaded
asteroids are potentially hazardous or not.

## Overview: App's Workflow
The app consists of two screens:
- A Main screen with a list of all the detected asteroids
- A Details screen that displays the NASA image of the day and the detailed information about the
  asteroid once it is selected in the Main screen.
- The main screen also shows the NASA image of the day to make the app more attractive.
- Includes the Main screen with a list of clickable asteroids
- Includes a Details screen that displays the selected asteroid data
- Downloads and parses data from the NASA NeoWS API.
- Once an asteroid is saved in the database, the list of asteroids is displayed
- The asteroids data is cached by using a worker, so it downloads and saves week asteroids in
  background.

## Jetpack Components

The following components from the Jetpack library are used:

- Navigation Component
- Room Database
- ViewModel, LiveData and Data Binding

## Important Dependencies & Features

Some important dependencies and features used are:

- Coroutines for getting off the UI thread
- WorkManager for running predictor tasks in the background
- MutableStateFlow
- GSON Library - A library to serialize and deserialize Java objects to JSON
- Retrofit with Moshi + Scalars Converters to download and convert the data from the Internet.
- Moshi to convert the JSON data we are downloading to usable data in form of custom classes.
- Glide - An image loading and caching library
- CardView, RecyclerView to display the asteroids in a list.
- Secrets Gradle Plugin for Android - A Gradle plugin for providing your secrets securely to your
  Android project.

## Screenshots

### List of Daily / Weekly or Offline Asteroid Records

![screenshot1](screenshots/screen_1.png)

### Filter Menus

![screenshot1](screenshots/screen_0.png)

### Detail Fragment: Asteroid with safe Status

![screenshot1](screenshots/screen_3.png)

### Detail Fragment: Asteroid with Hazard Status

![screenshot1](screenshots/screen_2.png)

### Custom Dialog to display information about Astronomical Unit (au)

![screenshot1](screenshots/screen_4.png)