#!/bin/bash

# Quick setup script for Cute Kana development environment

echo "🌸 Cute Kana - Development Setup"
echo "=================================="
echo ""

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

check_command() {
    if command -v $1 &> /dev/null; then
        echo -e "${GREEN}✓${NC} $1 found"
        return 0
    else
        echo -e "${RED}✗${NC} $1 not found"
        return 1
    fi
}

echo "Checking prerequisites..."
echo ""

# Check Java
if check_command java; then
    java -version 2>&1 | head -1
else
    echo -e "${RED}Please install JDK 17 or higher${NC}"
    echo "Ubuntu/Debian: sudo apt install openjdk-17-jdk"
    echo "macOS: brew install openjdk@17"
fi

echo ""

# Check Android SDK
if [ -n "$ANDROID_SDK_ROOT" ] || [ -n "$ANDROID_HOME" ]; then
    echo -e "${GREEN}✓${NC} Android SDK environment variable set"
    echo "  SDK Location: ${ANDROID_SDK_ROOT:-$ANDROID_HOME}"
else
    echo -e "${RED}✗${NC} Android SDK not configured"
    echo "  Please install Android Studio or SDK command line tools"
    echo "  https://developer.android.com/studio"
fi

echo ""

# Check ADB
if check_command adb; then
    adb --version | head -1
fi

echo ""

# Check for Gradle wrapper
if [ -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo -e "${GREEN}✓${NC} Gradle wrapper found"
else
    echo -e "${YELLOW}!${NC} Gradle wrapper not found"
    echo "  Will download automatically on first build"
fi

echo ""
echo "=================================="
echo ""

# Print next steps
if [ -n "$ANDROID_SDK_ROOT" ] || [ -n "$ANDROID_HOME" ]; then
    echo "🎉 You're ready to build! Run:"
    echo ""
    echo "   ./build-apk.sh"
    echo ""
    echo "Or manually:"
    echo "   ./gradlew assembleDebug"
    echo ""
else
    echo "⚠️  Setup incomplete. Please:"
    echo ""
    echo "1. Install Android Studio:"
    echo "   https://developer.android.com/studio"
    echo ""
    echo "2. Set environment variables:"
    echo "   export ANDROID_SDK_ROOT=\$HOME/Android/Sdk"
    echo ""
    echo "3. Then run:"
    echo "   ./build-apk.sh"
    echo ""
    echo "See BUILD.md for detailed instructions."
fi

echo ""
echo "For more help, see BUILD.md 📖"