# StudyBuddy

An AI-powered study companion Android app — chat with an AI assistant, upload documents for context, and keep a history of past study sessions. Built with Kotlin and Jetpack Compose, backed by Firebase.

## ✨ Features

- 🤖 AI chat assistant for study help (`ChatScreen`, `ChatMessage`)
- 📄 Document upload for context-aware answers (`DocumentUploader`)
- 🕘 Chat history view to revisit past sessions (`HistoryScreen`)
- 🔐 Authentication flow — sign up / log in (`AuthScreens`, `AuthViewModel`)
- 🎨 Fully declarative UI built with Jetpack Compose

## 🛠️ Tech Stack

| Category | Tech |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Backend/Cloud | Firebase (Google Services) |
| Architecture | MVVM (ViewModel + Compose state) |
| Build system | Gradle (Kotlin DSL) |

## 📂 Project Structure

```
StudyBuddy/
├── app/
│   ├── google-services.json                       # Firebase config (per-project, keep private)
│   ├── build.gradle.kts                            # App-level dependencies & config
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   └── java/com/company/studybuddy/
│       │       ├── MainActivity.kt                 # App entry point, sets Compose content
│       │       ├── data/
│       │       │   └── DocumentUploader.kt          # Handles uploading study documents
│       │       ├── model/
│       │       │   └── ChatMessage.kt               # Chat message data model
│       │       ├── viewmodel/
│       │       │   ├── AuthViewModel.kt              # Auth state & login/signup logic
│       │       │   └── StudyBuddyViewModel.kt         # Core chat/session state
│       │       └── ui/theme/
│       │           ├── AuthScreens.kt                # Login / signup composables
│       │           ├── ChatScreen.kt                 # Main chat UI
│       │           ├── HistoryScreen.kt              # Past chat sessions
│       │           ├── Color.kt                      # Compose color palette
│       │           ├── Theme.kt                      # Compose theme setup
│       │           └── Type.kt                       # Typography
│       ├── androidTest/                             # Instrumented tests
│       └── test/                                    # Unit tests
├── gradle/
│   └── libs.versions.toml                          # Version catalog
├── build.gradle.kts                                 # Top-level build config
└── settings.gradle.kts
```

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable)
- JDK 17+
- A Firebase project with **Authentication** enabled (and Firestore/Storage if you're persisting chats/documents)

### Setup

```bash
git clone https://github.com/lokeshnathsingh/StudyBuddy.git
cd StudyBuddy
```

1. Open the project in Android Studio.
2. Create a Firebase project at the [Firebase Console](https://console.firebase.google.com/) and register the app with package name `com.company.studybuddy`.
3. Download your own `google-services.json` and place it in `app/` (replace the sample one — don't commit real Firebase keys to a public repo).
4. Sync Gradle.
5. Run on an emulator or physical device.

### CLI Build

```bash
./gradlew assembleDebug
```

> ⚠️ **Security note:** a real `google-services.json` appears to be committed under `app/`. Rotate/restrict those Firebase API keys and add `app/google-services.json` to `.gitignore` if this repo is public.

## 🗺️ Roadmap

- [ ] Reminders for assignments & study sessions
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
