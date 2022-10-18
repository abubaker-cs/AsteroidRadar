# Asteroid Radar App

Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view
all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and
if they are potentially hazardous.

The app consists of two screens: A Main screen with a list of all the detected asteroids and a
Details screen that displays the data of that asteroid once it´s selected in the Main screen list.
The main screen also shows the NASA image of the day to make the app more striking.

The application:

- Includes the Main screen with a list of clickable asteroids
- Includes a Details screen that displays the selected asteroid data once it’s clicked in the Main
  screen
- Downloads and parses data from the NASA NeoWS (Near Earth Object Web Service) API.
- Once an asteroid is saved in the database, the list of asteroids is displayed
- The asteroids data is cached by using a worker, so it downloads and saves week asteroids in
  background when device is charging and wifi is enabled, as well as deleted the asteroids data of
  the previous day
- App works in multiple screen sizes and orientations, also it provides talk back and push button
  navigation.

## Instructions for using API

To build this project the NASA NeoWS (Near Earth Object Web Service) API is used, which can be found
here:
https://api.nasa.gov/

In order to run the app, you need an API Key which is provided for you in that same link, just fill
the fields in the form and click Signup.

## Dependencies

The most important dependencies used are:

- Retrofit to download the data from the Internet.
- Moshi to convert the JSON data we are downloading to usable data in form of custom classes.
- Picasso to download and cache images.
- RecyclerView to display the asteroids in a list.

The following components from the Jetpack library are used:

- ViewModel
- Room
- LiveData
- Data Binding
- Navigation

## Built With

* [Android Studio](https://developer.android.com/studio) - Default IDE used to build android apps
* [Kotlin](https://kotlinlang.org/) - Default language used to build this project
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
    - Android Jetpack's Navigation component, used for Fragment-based navigation
* [Retrofit](https://github.com/square/retrofit) - a type-safe HTTP client for Android and Java
* [Moshi](https://github.com/square/moshi) - a modern JSON library for Android and Java, that makes
  it easy to parse JSON into Java or Kotlin objects
* [Picasso](https://square.github.io/picasso) - a powerful image downloading and caching library for
  Android
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - a
  collection of libraries that help design robust, testable, and maintainable apps: Room (a SQLite
  object mapping library), LiveData (builds data objects that notify views when the underlying
  database changes), ViewModel (stores UI-related data that isn't destroyed on app rotations)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding) - a Jetpack support
  library that allows to bind UI components in your layouts to data sources in your app using a
  declarative format rather than programmatically
* [MVVM](https://developer.android.com/jetpack/guide) - the architecture pattern used in the app (
  Model-View-ViewModel), that incorporates the Android Architecture Components

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