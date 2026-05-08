# 🌸 Cute Kana - Japanese Learning App

A comprehensive Japanese language learning application built with **Kotlin** and **Jetpack Compose** featuring kawaii (cute) UI design, anime character associations, handwriting recognition, and JLPT mock tests.

![Platform](https://img.shields.io/badge/platform-Android-brightgreen)
![Language](https://img.shields.io/badge/language-Kotlin-blue)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-orange)
![Compiler](https://img.shields.io/badge/compiler-K2-red)

## ✨ Features

### 🈳 Character Learning
- **92 Kana Characters** - Complete Hiragana (46) and Katakana (46) with stroke order
- **250 Kanji** - JLPT N5 (80 kanji) and N4 (170 kanji) with meanings and readings
- **Anime Character Associations** - Each character linked to popular anime characters
- **Audio Pronunciation** - Native Japanese pronunciation for each character

### ✍️ Writing Practice
- **ML-Powered Recognition** - TensorFlow Lite model recognizes handwriting
- **Guided Tracing** - Animated stroke order with particle effects
- **Stylus Support** - Pressure-sensitive writing (if device supports)
- **4 Practice Modes**:
  - Guided Practice (follow the animation)
  - Free Writing (with reference outline)
  - Speed Challenge (timed)
  - Memory Mode (no guides)

### 🎮 Mini Games
- **Falling Kana** - Tetris-style character catching game
- **Radical Builder** - Build kanji from component parts
- **Kanji Story** - Read anime scenes with highlighted kanji
- **Match Pairs** - Memory game matching kana, romaji, and meanings
- **Speed Quiz** - Fast-paced recognition quiz

### 📝 JLPT Mock Tests
- **Full Mock Exams** - N5 (90 min) and N4 (110 min) with realistic timing
- **Section Practice** - Vocabulary, Grammar, Reading, Listening
- **Scoring System** - Detailed analytics and section breakdown
- **Test History** - Track your progress over time

### 🏆 Gamification
- **Card Collection** - Gacha-style card collecting with rarities (N, R, SR, SSR, UR)
- **Achievement System** - Unlock achievements as you learn
- **Daily Streaks** - Keep your learning streak alive
- **Leaderboards** - Compete with other learners

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Kotlin 1.9.21 with K2 Compiler |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Architecture** | MVVM with Clean Architecture |
| **Dependency Injection** | Hilt |
| **Local Database** | Room |
| **ML Recognition** | TensorFlow Lite |
| **Image Loading** | Coil |
| **Animations** | Lottie |
| **Data Storage** | DataStore |

## 🎨 UI Design System

- **Kawaii Glassmorphism** aesthetic
- **Pastel Color Palette**: Sakura Pink, Lavender, Mint, Star Yellow
- **Custom Typography**: Quicksand + Nunito fonts
- **Responsive Layout** for phones and tablets
- **Dark/Light Theme** support

## 📱 Screenshots

| Home | Writing | Mock Test |
|------|---------|-----------|
| *Dashboard with daily goals* | *Guided stroke practice* | *JLPT test interface* |

| Collection | Games | Character Detail |
|------------|-------|------------------|
| *Card collection view* | *Mini games menu* | *Character with anime association* |

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34
- Minimum API 26 (Android 8.0)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/cute-kana.git
   cd cute-kana
   ```

2. **Open in Android Studio**
   - Open the project in Android Studio
   - Wait for Gradle sync to complete
   - Install any missing SDK components

3. **Add Custom Fonts (Optional)**
   - Download [Quicksand](https://fonts.google.com/specimen/Quicksand) and [Nunito](https://fonts.google.com/specimen/Nunito)
   - Place font files in `app/src/main/res/font/`
   - If not provided, system fonts will be used

4. **Build and Run**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio

### TensorFlow Lite Model

The app uses a handwriting recognition model. For the ML feature to work:

1. Train a TensorFlow Lite model for Japanese character recognition
2. Place the model file at `app/src/main/assets/ml/stroke_recognition_model.tflite`
3. If no model is provided, the app will use fallback heuristic recognition

## 📁 Project Structure

```
cute-kana/
├── app/
│   ├── src/main/
│   │   ├── java/com/cutekana/
│   │   │   ├── data/
│   │   │   │   ├── local/         # Room database & DAOs
│   │   │   │   ├── model/         # Data models
│   │   │   │   ├── ml/            # ML recognition
│   │   │   │   └── repository/    # Repository implementations
│   │   │   ├── di/                # Hilt modules
│   │   │   ├── ui/
│   │   │   │   ├── components/    # Reusable UI components
│   │   │   │   ├── screens/       # Screen implementations
│   │   │   │   ├── theme/         # Theme & typography
│   │   │   │   └── viewmodel/     # ViewModels
│   │   │   ├── CuteKanaApplication.kt
│   │   │   └── MainActivity.kt
│   │   └── res/                   # Resources
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## 📚 Learning Path

The app guides users through a structured learning path:

### Phase 1: Kana Foundation (Levels 1-50)
- 46 Hiragana characters
- 46 Katakana characters
- Dakuten/Handakuten variations
- Combo sounds

### Phase 2: N5 Kanji (Levels 51-80)
- 80 basic kanji
- Simple radicals
- Basic compounds

### Phase 3: N4 Kanji (Levels 81-150)
- 170 intermediate kanji
- Complex compounds
- JLPT-style questions

### Phase 4: Mastery (Level 150+)
- Mixed reading practice
- Anime subtitle reading
- JLPT N5/N4 mock exams

## 🎯 Character Database

The app includes stroke data for **342 characters**:
- 92 Kana (Hiragana + Katakana)
- 250 Kanji (N5 + N4)

Each character includes:
- Complete stroke order paths
- Anime character associations
- Audio pronunciation files
- Meanings and readings (for kanji)

## 🧠 Writing Recognition

The ML recognition system evaluates:
- **Stroke Order** (40% weight)
- **Stroke Direction** (25% weight)
- **Shape Accuracy** (20% weight)
- **Proportions** (10% weight)
- **Speed** (5% weight)

Uses Dynamic Time Warping (DTW) algorithm for stroke comparison.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **JLPT** - Japanese Language Proficiency Test standards
- **TensorFlow** - ML framework
- **Jetpack Compose** - Modern Android UI toolkit
- **Material Design 3** - Design system

## 📧 Contact

For questions or support, please open an issue on GitHub.

---

Made with 🌸 in Kotlin# cute-kana
