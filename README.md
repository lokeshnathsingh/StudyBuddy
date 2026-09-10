# StudyBuddy

An Android study companion app built to help students stay organized — manage notes, reminders, and study tasks in one place, powered by an AI assistant.

## ✨ Features

- 🤖 AI-powered study assistant to answer questions and help with revision
- 📝 Create and organize study notes
- ⏰ Reminders for assignments, tests, and study sessions
- ☁️ Cloud sync via Firebase (auth, storage/Firestore)
- 🎨 Modern UI built entirely with Jetpack Compose

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Backend/Cloud:** Firebase (Google Services)
- **Architecture:** MVVM / Clean Architecture
- **Build System:** Gradle (Kotlin DSL)

## 📂 Project Structure

```
StudyBuddy/
├── app/                    # Main application module
│   ├── src/main/java/...   # Kotlin source (UI, ViewModels, data layer)
│   └── src/main/res/       # Compose themes, resources
├── gradle/                 # Gradle wrapper & version catalog
├── build.gradle.kts        # Top-level build config
└── settings.gradle.kts
```

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable)
- JDK 17+
- A Firebase project (add your `google-services.json` to `app/`)

### Setup

```bash
git clone https://github.com/lokeshnathsingh/StudyBuddy.git
cd StudyBuddy
```

1. Open the project in Android Studio.
2. Add your own `google-services.json` file to the `app/` directory (get it from the [Firebase Console](https://console.firebase.google.com/)).
3. Sync Gradle.
4. Run on an emulator or physical device.

### CLI Build

```bash
./gradlew assembleDebug
```

## 🗺️ Roadmap

- [ ] Chat-based AI assistant integration
- [ ] Expense tracking module
- [ ] Study planner / calendar view
- [ ] Offline-first support with Room

## 🤝 Contributing

Contributions, issues, and feature requests are welcome. Feel free to check the [issues page](https://github.com/lokeshnathsingh/StudyBuddy/issues).

## 📄 License

This project is open for educational use. Add a license file if you plan to distribute it publicly.

## 👤 Author

**Lokesh Nath Singh**
- GitHub: [@lokeshnathsingh](https://github.com/lokeshnathsingh)
- LinkedIn: [lokesh-nath-singh](https://linkedin.com/in/lokesh-nath-singh/)
