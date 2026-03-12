# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

SmartNest is an Android smart home control application built with Jetpack Compose and Kotlin. It provides device management, scene automation, mall/shopping features, and user profile management.

## Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Run tests
./gradlew test

# Clean build
./gradlew clean

# Run a single test
./gradlew test --tests "TestClassName.testMethodName"
```

## Architecture

The app follows MVVM architecture with the following layers:

- **UI Layer**: Jetpack Compose screens in `ui/screens/`, UI components in `ui/components/`, theme in `ui/theme/`
- **ViewModel Layer**: ViewModels in `ui/viewmodel/` - manages UI state
- **Data Layer**: Repositories in `data/repository/`, API interfaces in `data/remote/api/`, DTOs in `data/remote/dto/`, models in `data/model/`
- **DI Layer**: Koin modules in `di/AppModule.kt`

### Dependency Injection

Uses Koin (not Hilt). Modules are defined in `di/AppModule.kt`:
- `networkModule`: OkHttpClient, Retrofit instance
- `repositoryModule`: DeviceRepository, AuthRepository, ProductRepository, SceneRepository
- `viewModelModule`: HomeViewModel, AuthViewModel, DeviceViewModel, SceneViewModel, MallViewModel, ProfileViewModel

## Key Technical Details

- **Target SDK**: 36 (Android 15)
- **Min SDK**: 24 (Android 7.0)
- **Language**: Kotlin 2.0.21
- **Compose BOM**: 2024.09.00
- **Navigation**: Navigation Compose 2.8.0
- **Networking**: Retrofit 2.9.0 + OkHttp 4.12.0 with Moshi converter
- **Async**: Kotlin Coroutines 1.8.0
- **Preferences**: DataStore Preferences 1.1.1
- **Image Loading**: Coil 2.7.0

## Theme & Internationalization

- Theme switching: Light/Dark/System - managed by `ThemeManager` in `ui/theme/`
- Language switching: Chinese/English - managed by `LanguageManager` in `ui/theme/`
- String resources: `res/values/strings.xml` (Chinese), `res/values-en/strings.xml` (English)

## Main Screens

Navigation is defined in `SmartNestApp.kt`. Main screens include:
- SplashScreen → HomeScreen → SceneScreen / MallScreen / ProfileScreen
- SettingsScreen, CartScreen, PaymentScreen, OrderScreen, FavoriteScreen
- DeviceManagementScreen, EnergyAnalysisScreen, MonitorPreviewScreen
- FamilyMemberScreen, CommunityScreen, NotificationScreen

## Data Models

Core models in `data/model/Models.kt`: Device, Scene, Product, User, EnergyUsage, CommunityPost

## API

API interface is defined in `data/remote/api/SmartNestApi.kt`. Base URL is configured in both `AppModule.kt` and `build.gradle.kts` as `https://api.smartnest.example.com/`

## Testing

Uses JUnit 4 and MockK for unit testing. Test files should be placed in `app/src/test/`.