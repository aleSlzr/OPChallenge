# 🎵 OP Challenge — Android App

An Android application built with **Kotlin**, **Jetpack Compose**, **MVVM** and **Clean Architecture**.  
The app displays a list of artists, their albums, and track details fetched from the **Spotify API**.

---

## 🏗️ Technologies used for this project 

- **Kotlin**
- **Jetpack Compose**
- **MVVM + Clean Architecture**
- **Hilt (Dependency Injection)**
- **Retrofit + OkHttp (Networking)**
- **Coroutines + Flow**
- **DataStore (Local Storage)**
- **Compose Navigation**

---

## 🚀 Getting Started

### 1. Prerequisites
Before running the project, ensure you have:
- **Android Studio Giraffe (or newer)**
- **JDK 21+**
  - Validate if you're running the Gradle JDK = 21.0.8
  - To validate that go to
  ```
    Build, Excecution and Deployment
    Build tools
    Gradle
    ```
- **Gradle 8+**
- An **Android device** or **emulator** (API level 24+)

---

### 2. Clone the repository
- If you have installed Git run this command
```bash
git clone https://github.com/aleSlzr/OPChallenge.git
cd OPChallenge
```
- If not use this https://github.com/aleSlzr/OPChallenge.git
- Go to the green ``Code`` button and select ``Download Zip``
- Save it in you Documents or Desktop Folder

---

### 3. Open Android Studio
- 

---

### 4. Set the local.properties file
- When the Gradle build finished open the `local.properties` file that the project creates
and create two variables
```bash
CLIENT_ID
CLIENT_SECRET
```
- The values for these two variables will be delivered to you via email

---

### 4. Create an emulator to run the app
-  To create an Android emulator go to the right panel, you will see a small elephant icon
- Below of that icon is the once to create an emulator, click on it
- You will see a small plus icon (+), click on that and select ``Create Virtual Device``
- You will see a new window
  - Select phone
  - Pixel 9 Pro
  - Hit Next and Finish

---

### 5. Run the app
- Click the green play button to run the app
- This will launch a new window with the Android Emulator