# 📱 Building Cute Kana APK

This guide explains how to build the Cute Kana Android app into an APK file.

## Prerequisites

### 1. Java Development Kit (JDK)

**Check if Java is installed:**
```bash
java -version
```

**Required:** JDK 17 or higher

**Install if needed:**
- **Ubuntu/Debian:** `sudo apt install openjdk-17-jdk`
- **macOS:** `brew install openjdk@17`
- **Windows:** Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)

### 2. Android SDK

The Android SDK is **required** to build Android apps.

**Option A: Install Android Studio (Recommended)**
1. Download from [developer.android.com/studio](https://developer.android.com/studio)
2. Install Android Studio
3. During setup, install:
   - Android SDK 34 (Android 14.0)
   - Android SDK Build-Tools 34
   - Android Emulator (optional)

**Option B: Command Line Tools Only**
1. Download command line tools from [here](https://developer.android.com/studio#command-tools)
2. Extract to `~/Android/Sdk/cmdline-tools/`
3. Run: `sdkmanager --install "platforms;android-34" "build-tools;34.0.0"`

### 3. Environment Variables

Add to your shell configuration (`~/.bashrc`, `~/.zshrc`, or `~/.bash_profile`):

```bash
# Android SDK
export ANDROID_SDK_ROOT=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools
export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin
```

**Then reload:**
```bash
source ~/.bashrc  # or ~/.zshrc
```

### 4. Verify Setup

```bash
# Check Android SDK
adb --version

# Check SDK Manager
sdkmanager --list
```

## Building the APK

### Option 1: Using the Build Script (Easiest)

```bash
# Navigate to project directory
cd cute-kana

# Run the build script
./build-apk.sh
```

This script will:
1. Check prerequisites
2. Download Gradle wrapper if needed
3. Build the debug APK
4. Show you where the APK is located

### Option 2: Using Gradle Directly

```bash
# Make gradlew executable (Unix/Mac)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing)
./gradlew assembleRelease
```

### Option 3: Using Android Studio

1. Open Android Studio
2. Click "Open" and select the `cute-kana` folder
3. Wait for Gradle sync to complete
4. Click `Build → Build Bundle(s) / APK(s) → Build APK(s)`
5. The APK will be in `app/build/outputs/apk/debug/`

## APK Output Locations

After a successful build:

| Build Type | Location |
|------------|----------|
| Debug | `app/build/outputs/apk/debug/app-debug.apk` |
| Release | `app/build/outputs/apk/release/app-release-unsigned.apk` |

## Installing the APK

### To a Physical Device

```bash
# Enable USB debugging on your device
# Connect via USB
adb install app/build/outputs/apk/debug/app-debug.apk
```

### To an Emulator

```bash
# Start emulator
emulator -avd <AVD_NAME>

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Troubleshooting

### Issue: "Android SDK not found"

**Solution:**
```bash
export ANDROID_SDK_ROOT=/path/to/your/Android/Sdk
# Or on Windows:
set ANDROID_SDK_ROOT=C:\Users\YourName\Android\Sdk
```

### Issue: "JAVA_HOME not set"

**Solution:**
```bash
# Find Java installation
which java

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk  # Linux
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home  # macOS
```

### Issue: "Gradle wrapper not found"

**Solution:**
```bash
# Download wrapper manually
curl -L -o gradle/wrapper/gradle-wrapper.jar \
    https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradle/wrapper/gradle-wrapper.jar
```

### Issue: Build fails with "Could not find androidx..."

**Solution:**
1. Check your internet connection
2. Try: `./gradlew build --refresh-dependencies`
3. Clear Gradle cache: `./gradlew clean`

### Issue: "SDK Build Tools revision (34.0.0) not found"

**Solution:**
```bash
sdkmanager "build-tools;34.0.0"
```

## Release Build (For Google Play)

To create a release APK for distribution:

1. **Create a signing keystore:**
```bash
keytool -genkey -v -keystore cute-kana.keystore -alias cutekana -keyalg RSA -keysize 2048 -validity 10000
```

2. **Add to `~/.gradle/gradle.properties`:**
```properties
RELEASE_STORE_FILE=cute-kana.keystore
RELEASE_KEY_ALIAS=cutekana
RELEASE_STORE_PASSWORD=your_password
RELEASE_KEY_PASSWORD=your_password
```

3. **Build release APK:**
```bash
./gradlew assembleRelease
```

The signed APK will be at:
`app/build/outputs/apk/release/app-release.apk`

## Build Variants

| Command | Output |
|---------|--------|
| `./gradlew assembleDebug` | Debug APK with debugging symbols |
| `./gradlew assembleRelease` | Optimized release APK |
| `./gradlew bundleRelease` | Android App Bundle (AAB) for Play Store |
| `./gradlew clean` | Clean build artifacts |

## Continuous Integration

### GitHub Actions Workflow

Create `.github/workflows/build.yml`:

```yaml
name: Build APK

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
          
      - name: Setup Android SDK
        uses: android-actions/setup-android@v2
        
      - name: Build APK
        run: ./gradlew assembleDebug
        
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/*.apk
```

## Need Help?

If you encounter issues:

1. Check the [Android Developer docs](https://developer.android.com/studio/build)
2. Review the build logs carefully
3. Try `./gradlew clean build --stacktrace` for detailed errors
4. Ensure all prerequisites are correctly installed

---

**Happy Building!** 🌸✨