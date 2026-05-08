#!/bin/bash

# Build APK Script for Cute Kana
# This script helps you build the APK file

echo "🌸 Cute Kana - Build Script"
echo "=========================="
echo ""

# Check for Java
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install JDK 17 or higher."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | cut -d'.' -f1)
echo "✅ Java version: $JAVA_VERSION"

# Check for Android SDK
if [ -z "$ANDROID_SDK_ROOT" ] && [ -z "$ANDROID_HOME" ]; then
    echo ""
    echo "⚠️  Android SDK not found!"
    echo ""
    echo "Please install Android Studio and set up the Android SDK:"
    echo "1. Download Android Studio from https://developer.android.com/studio"
    echo "2. Install Android SDK 34 (Android 14.0)"
    echo "3. Set ANDROID_SDK_ROOT environment variable"
    echo ""
    echo "Example (add to ~/.bashrc or ~/.zshrc):"
    echo 'export ANDROID_SDK_ROOT=$HOME/Android/Sdk'
    echo 'export PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin'
    echo ""
    exit 1
fi

echo "✅ Android SDK found"

# Check for Gradle wrapper jar
if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo ""
    echo "📦 Downloading Gradle Wrapper..."
    mkdir -p gradle/wrapper
    
    # Download the gradle wrapper jar
    curl -L -o gradle/wrapper/gradle-wrapper.jar \
        https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradle/wrapper/gradle-wrapper.jar
    
    if [ $? -ne 0 ]; then
        echo "❌ Failed to download Gradle wrapper"
        echo "Please manually download from: https://services.gradle.org/distributions/gradle-8.2-bin.zip"
        exit 1
    fi
    
    echo "✅ Gradle wrapper downloaded"
fi

# Build the APK
echo ""
echo "🔨 Building APK..."
echo ""

./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo ""
    echo "🎉 Build successful!"
    echo ""
    echo "APK location:"
    find app/build/outputs/apk -name "*.apk" -type f
    echo ""
    echo "To install on device:"
    echo "adb install app/build/outputs/apk/debug/app-debug.apk"
else
    echo ""
    echo "❌ Build failed. Check the error messages above."
    exit 1
fi