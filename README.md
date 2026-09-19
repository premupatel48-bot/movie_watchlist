# 🎬 Movie Watchlist

**Movie Watchlist** is an Android application developed using **Kotlin** and **Android Studio**. The application allows users to maintain their personal movie watchlist, add movie details, select movie posters, schedule watch reminders, and keep track of watched movies and ratings.

The project is designed with a simple and user-friendly interface using **Material Design**, making it easy to manage movies directly from an Android device.

## 📱 Features

* 🔐 **User Login**

  * Simple username and password login system.
  * Multiple predefined users are supported.
  * User session is saved locally.

* 🎬 **Movie Watchlist**

  * View all saved movies in a list.
  * Each movie displays its poster, name, genre, year, watched status, rating, and scheduled date/time.

* ➕ **Add Movie**

  * Add a new movie to the watchlist.
  * Enter movie name, genre, release year, and description.
  * Select a poster from the available posters.

* ✏️ **Edit Movie**

  * Tap an existing movie to edit its details.
  * Update movie information, watched status, rating, poster, and schedule.

* 🗑️ **Delete Movie**

  * Long press a movie to delete it.
  * A confirmation dialog is displayed before deletion.

* ⭐ **Movie Rating**

  * Mark a movie as watched.
  * Give a rating from 0 to 5 stars.

* 📅 **Watch Scheduling**

  * Select a date and time for watching a movie.
  * The application can schedule a reminder 30 minutes before the selected watch time.

* 💾 **Local Data Storage**

  * Movie information is stored locally on the device.
  * Data is stored using `SharedPreferences` and JSON.
  * Movie data is maintained separately for each logged-in username.

* 🚪 **Logout**

  * Users can log out from the main screen.
  * The application returns to the login screen.

## 🛠️ Technologies Used

| Technology            | Usage                            |
| --------------------- | -------------------------------- |
| **Kotlin**            | Application programming language |
| **Android Studio**    | Development environment          |
| **Android SDK**       | Android application development  |
| **Material Design**   | UI components and styling        |
| **ConstraintLayout**  | Screen layouts                   |
| **ListView**          | Displaying the movie watchlist   |
| **SharedPreferences** | Local data storage               |
| **JSON**              | Storing movie information        |
| **AlarmManager**      | Scheduling movie reminders       |
| **BroadcastReceiver** | Handling scheduled reminders     |
| **Gradle Kotlin DSL** | Project build configuration      |

## 📂 Project Structure

```text
movie_watchlist/
│
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/new_app/movie_watchlist/
│       │   │       ├── LoginActivity.kt
│       │   │       ├── MainActivity.kt
│       │   │       ├── AddMovieActivity.kt
│       │   │       ├── DataManager.kt
│       │   │       ├── Movie.kt
│       │   │       └── ReminderReceiver.kt
│       │   │
│       │   ├── res/
│       │   │   ├── drawable/
│       │   │   ├── layout/
│       │   │   │   ├── activity_login.xml
│       │   │   │   ├── activity_main.xml
│       │   │   │   ├── activity_add_movie.xml
│       │   │   │   └── item_movie.xml
│       │   │   ├── mipmap/
│       │   │   └── values/
│       │   │
│       │   └── AndroidManifest.xml
│       │
│       └── test/
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```

## 🔑 Login Details

The current project contains the following predefined login credentials:

| Username  | Password      |
| --------- | ------------- |
| `prem`  | `prem@123`  |
| `prem1` | `prem@123` |
| `prem2` | `prem@123` |

> **Note:** These credentials are hardcoded in the application for demonstration/academic purposes. This login system is not intended for production use.

## 🚀 How to Run the Project

### 1. Clone the Repository

```bash
git clone <YOUR-GITHUB-REPOSITORY-URL>
```

### 2. Open in Android Studio

Open the cloned `movie_watchlist` folder in **Android Studio**.

### 3. Sync Gradle

Allow Android Studio to download and configure the required Gradle dependencies.

### 4. Select an Android Device

You can use either:

* Android Emulator
* Physical Android device

The project is configured with:

```text
Compile SDK: 37
Target SDK: 37
Minimum SDK: 26
Java Version: 11
```

### 5. Run the Application

Click the **Run ▶** button in Android Studio and select your Android device.

## 📖 Application Flow

```text
Start Application
       ↓
   Login Screen
       ↓
   Valid Login
       ↓
   My Watchlist
       ↓
 ┌─────┴──────────┐
 ↓                ↓
Add Movie       View Movies
 ↓                ↓
Movie Details     ↓
 ↓             Tap Movie
Poster           ↓
Date/Time      Edit Movie
 ↓
Save Movie
       ↓
Movie Added to Watchlist
```

## 🎥 Adding a Movie

To add a movie:

1. Log in to the application.
2. Tap the **+** button on the main screen.
3. Enter the movie name.
4. Enter the genre.
5. Enter the release year.
6. Add a description or notes if required.
7. Select a movie poster.
8. Select the watching date.
9. Select the watching time.
10. Mark the movie as **Watched** if you have already watched it.
11. Add a rating if the movie is watched.
12. Tap **SAVE MOVIE**.

## 💾 Data Management

The application does not require an external database or internet connection for its basic functionality.

Movie information is converted into JSON format and stored using Android `SharedPreferences`.

The `DataManager` class handles:

* Saving the current user
* Loading the current user
* Logging out
* Saving movies
* Loading movies
* Adding movies
* Updating movies
* Deleting movies

The `Movie` data class handles conversion between movie objects and JSON objects.

## ⏰ Movie Reminder

The application uses Android's `AlarmManager` to schedule movie reminders.

When a movie is scheduled, the application creates a reminder for **30 minutes before the selected watch time**.

The `ReminderReceiver` receives the scheduled alarm and displays a message containing the movie name and scheduled time.

## 🎨 User Interface

The application uses a dark movie-themed interface with:

* Dark background
* Red primary color
* White primary text
* Gray secondary text
* Gold accent color
* Material Cards
* Floating Action Button
* Movie poster cards

The main screens include:

### Login Screen

Allows the user to enter their username and password.

### My Watchlist

Displays the user's saved movies in a list with movie posters, details, watched status, ratings, and schedules.

### Add Movie Screen

Provides a form for entering movie information and selecting posters, dates, times, and ratings.

## 🔒 Permissions

The application uses Android alarm permissions for scheduling exact movie reminders:

```xml
android.permission.SCHEDULE_EXACT_ALARM
android.permission.USE_EXACT_ALARM
```

## 📸 Screenshots

<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/bf8024e8-8a83-4f40-aac9-6f70a337137f" />
<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/6fa1ed6a-f56a-41a1-897e-eae883a118fc" />
<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/ffce1119-e39b-452d-9e45-2b882ef7ac08" />
<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/c47e9fd4-8138-45c2-ba96-35229c6b084e" />
<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/8edfadd3-aa42-4b7b-9a8d-ee347ccc7271" />
<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/79309dc9-f845-4f33-b014-d1cade05268a" />
<img width="350" height="778" alt="image" src="https://github.com/user-attachments/assets/bb34aeee-0872-41c6-a7d4-32e08c10b9c5" />




## 🔮 Future Improvements

The project can be further improved by adding:

* 🌐 Online movie database/API integration
* 🔍 Movie search functionality
* 🎞️ Automatic movie posters from an API
* ❤️ Favorite movies
* 📊 Movie statistics and watch history
* 🔔 Android notification reminders instead of Toast messages
* 👤 Proper user registration
* 🔐 Secure authentication
* ☁️ Cloud database synchronization
* 🎭 Movie cast and director information
* ⭐ Reviews and personal notes
* 🎯 Movie recommendations
* 🌓 Light/Dark theme switching

## 👨‍💻 Project Information

**Project Name:** Movie Watchlist
**Platform:** Android
**Language:** Kotlin
**IDE:** Android Studio
**Minimum SDK:** 26
**Target SDK:** 37
**Compile SDK:** 37
**Version:** 1.0
