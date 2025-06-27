# Users-App

Android project built with focus on Clean Architecture, Jetpack Compose, and automated testing.

---

## ✨ Overview

**Users-App** is a demo Android application that loads and displays a list of users, allowing navigation to a detail screen. It implements:

* Clean Architecture (layers: domain, data, presentation)
* Jetpack Compose with `Navigation` 2.9+
* ViewModel + StateFlow
* Unit and UI tests
* CI with Kover, Detekt, and GitHub Actions
* Dependency injection with Koin
* Local persistence and remote sync

---

## 🎓 Features

* Home screen with user list
* Detail screen on user click
* Remote sync logic
* Local cache and fallback
* Loading, error, and refresh UI states

---

## ⚙️ Technologies and Libraries

* **Jetpack Compose**: Modern declarative UI
* **Navigation 2.9+**: Typed destination routes (`composable<Destination>`)
* **Kotlinx Serialization**: For route arguments
* **Koin**: Dependency injection
* **Room (or Fake LocalDataSource)**: Simulated local persistence
* **StateFlow + SharedFlow**: Reactive state management
* **Kotlin Coroutines**: Concurrency
* **Kover**: Test coverage tracking
* **Detekt**: Static code analysis
* **JUnit + Mockk + Turbine**: Unit testing
* **Jetpack Compose UI Test + TestNavHostController**: Instrumented UI testing

---

## 📊 Project Structure

```
/core
  /data
  /domain
  /utils
/features
  /home
    /data
    /domain
    /presentation
  /details
    /data
    /domain
    /presentation
/ui
  /navigation
  /theme
```

Tests:

```
/src
  /test/...           // Unit tests
  /androidTest/...
    /fake/            // Fakes and Mocks
    /screen/          // UI and navigation tests
```

---

## ✅ Testing

### Unit Tests (ViewModel, UseCase, Repository)

* `HomeViewModelTest`
* `DetailsViewModelTest`
* `LoadAndSyncUsersTest`

### UI Tests

* Button click and UI assertions with Compose
* `FakeHomeViewModel` to simulate state
* `TestNavHostController` to test navigation

### Coverage and Quality

* Kover enforces minimum 50% test coverage
* Detekt configured to ignore `@Composable` functions

---

## ⚖️ CI/CD

* GitHub Actions runs:

    * Unit and UI tests
    * Detekt with failure on violations
    * Kover for test coverage enforcement

---

## 🔧 How to Run Locally

1. Clone the repository:

   ```bash
   git clone https://github.com/joao01sb/Users-App.git
   ```
2. Open in Android Studio
3. Run on emulator or physical device
4. Execute tests:

   ```bash
   ./gradlew testDebugUnitTest
   ./gradlew connectedAndroidTest
   ```
---

## 🚀 Author

Created by [@joao01sb](https://github.com/joao01sb) as a practical study project on architecture, testing, and best practices in modern Android development with Jetpack Compose.
