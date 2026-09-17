Movie Watchlist Android App

Overview

Movie Watchlist is a simple Android application developed using Kotlin
and XML in Android Studio. It allows users to log in and maintain a
personal movie watchlist. Users can add movie details, select a poster,
choose a watch date and time, mark a movie as watched, give it a rating,
edit existing movies, and delete movies. The app also provides a
reminder feature that can notify the user 30 minutes before the
scheduled movie time.

Technologies Used

Android Studio

Kotlin

XML

Android SDK

AndroidX

Material Components

ConstraintLayout

SharedPreferences

JSON

AlarmManager

BroadcastReceiver

ListView and BaseAdapter

Main Features

Login

The application starts with a login screen where the user enters a
username and password. After successful validation, the username is
stored locally and the user is taken to the main watchlist screen.

Movie Watchlist

The main screen displays all movies saved for the current user. Each
movie shows its poster, name, genre, year, watched/unwatched status,
rating when watched, and scheduled date and time when available.

Add Movie

Users can add a movie by entering its name, genre, release year,
description, poster, watch date, watch time, watched status, and rating.
Required fields are validated, and the year must be between 1888 and
2100.

Edit Movie

Tapping a movie opens the movie form in edit mode. Existing information
is loaded and can be updated.

Delete Movie

A long press on a movie opens a confirmation dialog. Selecting Delete
removes the movie from the watchlist.

Watched Status and Rating

Users can mark a movie as watched. When the Watched checkbox is
selected, a RatingBar becomes visible so the user can give a rating from
0 to 5.

Movie Poster Selection

The application provides four local poster images that can be selected
while adding or editing a movie.

Reminder

Users can select a watch date and time. Android AlarmManager schedules a
reminder 30 minutes before the selected time. ReminderReceiver receives
the alarm and displays a reminder message.

Logout

The user can log out from the main screen. The current user is removed
from local storage and the application returns to the login screen.

Project Structure

movie_watchlist/
├── app/
│   └── src/main/
│       ├── java/com/new_app/movie_watchlist/
│       │   ├── LoginActivity.kt
│       │   ├── MainActivity.kt
│       │   ├── AddMovieActivity.kt
│       │   ├── DataManager.kt
│       │   ├── Movie.kt
│       │   └── ReminderReceiver.kt
│       ├── res/
│       │   ├── drawable/
│       │   ├── layout/
│       │   │   ├── activity_login.xml
│       │   │   ├── activity_main.xml
│       │   │   ├── activity_add_movie.xml
│       │   │   └── item_movie.xml
│       │   ├── mipmap/
│       │   ├── values/
│       │   └── xml/
│       └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/

Important Classes

LoginActivity: Handles login and validates the demo credentials.

MainActivity: Displays the movie watchlist using ListView and a
custom MovieAdapter. It handles adding, editing, deleting, and logging
out.

AddMovieActivity: Provides the form for creating and editing movie
records. It also handles poster selection, date/time selection, watched
status, rating, and reminder scheduling.

Movie: Kotlin data class representing a movie and converting movie
data to and from JSON.

DataManager: Handles local storage using SharedPreferences and JSON.
It provides add, load, update, and delete operations.

ReminderReceiver: BroadcastReceiver that receives scheduled alarms
and displays the movie reminder.

Data Storage

The application uses SharedPreferences for local storage. The current
username is stored under current_user. Movie data is stored separately
for each username using a username-based key. Movie objects are
converted to JSON objects and stored inside a JSON array.

CRUD Operations

Create: Add a new movie.

Read: Load and display saved movies.

Update: Edit an existing movie.

Delete: Remove a movie.

Reminder Flow

User selects a movie date and time.

The application calculates a reminder time 30 minutes earlier.

AlarmManager schedules the alarm.

ReminderReceiver receives the alarm.

A Toast message displays the reminder.

Requirements

Android Studio

Android SDK with compile SDK 37

Minimum Android SDK 26

Java 11 compatible environment

Android device or emulator

How to Run

Extract the project ZIP file.

Open the movie_watchlist folder in Android Studio.

Allow Gradle to sync.

Connect an Android device or start an emulator.

Click Run.

The application opens on the Login screen.

Demo Login Credentials

Username   Password

prem       prem@123
prem1      prem@123
prem2      prem@123

These credentials are hard-coded for the current academic/demo project.

Future Improvements

Use Firebase or Room/SQLite for data storage.

Add secure user authentication.

Add movie search, categories, and filtering.

Add online movie posters.

Use notifications for reminders.

Add sorting by rating, year, or watch date.

Add cloud backup and synchronization.

Conclusion

Movie Watchlist is a student-level Android application demonstrating
Activities, Intents, XML layouts, ListView, custom adapters,
SharedPreferences, JSON handling, dialogs, date/time pickers,
AlarmManager, and BroadcastReceiver. It provides a simple way to manage
a personal movie watchlist locally on an Android device.
