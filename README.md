# Project description
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.3.50-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

Loqui is a project that presents modern, 2019 approach to
[Android](https://en.wikipedia.org/wiki/Android_(operating_system)) application development using
[Kotlin](https://kotlinlang.org/) and latest tech-stack.

The goal of the project is to demonstrate best practices and present modern Android
application architecture that is modular, scalable, maintainable and testable. The goal is to design it in a manner, that will set the rock-solid foundation of the larger app suitable for bigger teams and
long [application lifecycle](https://en.wikipedia.org/wiki/Application_lifecycle_management). Many of the project design
decisions follow official Google recommendations.

## Project characteristics

This project brings to table set of best practices, tools, and solutions:

* 100% [Kotlin](https://kotlinlang.org/)
* Modern architecture (Clean Architecture, Model-View-ViewModel)
* [Android Jetpack](https://developer.android.com/jetpack)
* A single-activity architecture, using the Navigation component to manage fragment operations
  * Reactive UIs
* Firebase
* CI pipeline (TODO)
* Testing (TODO)
* Dependency Injection
* Material design

## Tech-stack

Min API level is set to [`21`](https://android-arsenal.com/api?level=21), so the presented approach is suitable for over
[85% of devices](https://developer.android.com/about/dashboards) running Android. This project takes advantage of many
popular libraries and tools of the Android ecosystem.

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Dagger2](https://dagger.dev/) - dependency injection
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
 *   [Firebase](https://firebase.google.com/) - Authentication, storing data and files
 *   [Glide](https://bumptech.github.io/glide/) - image loading library
 * [and more...](https://github.com/andrzejbogacz/LoquiCleanArchitecture/blob/master/versions.gradle)
* Architecture
    * Clean Architecture (at module level)
    * MVVM (presentation layer)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation), [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) plugin)
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/)) (TODO)
    * [Mockito](https://github.com/mockito/mockito) + [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin) (TODO)
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

## Architecture

TODO


## Upcoming improvements

* Implementing chat rooms
* Add test coverage
* Improve error handling
* UI tests
* Improve databinding with two-way data binding using InverseAdapters
* Add Room (?)
* Support for DayNight MaterialTheme
* and much more…

## Getting started

There are a few ways to open this project.

### Android Studio

1. Android Studio -> File -> New -> From Version control -> Git
2. Enter `https://github.com/andrzejbogacz/LoquiCleanArchitecture.git` into URL field

### Command line + Android Studio

1. Run `git clone https://github.com/andrzejbogacz/LoquiCleanArchitecture.git`
2. Android Studio -> File -> Open

## Inspiration

* [Android Architecture Blueprints v2](https://github.com/googlesamples/android-architecture) - showcase of different
  Android architecture approaches
* [Android sunflower](https://github.com/googlesamples/android-sunflower) complete `Jetpack` sample covering all
  libraries
* [GithubBrowserSample](https://github.com/googlesamples/android-architecture-components) - multiple small projects
  demonstrating usage of Android Architecture Components
* [Android - Clean Architecture - Kotlin](https://github.com/android10/Android-CleanArchitecture-Kotlin) - movies sample app in Kotlin with usage of coroutines as a background threading of UseCases
