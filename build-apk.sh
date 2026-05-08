#!/bin/bash

# Build APK Script for Cute Kana
# This script helps you build the APK file and optionally create a GitHub release

set -e

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

# Check for gh CLI (GitHub CLI)
if ! command -v gh &> /dev/null; then
    echo "⚠️  GitHub CLI (gh) not found. Install it to enable release creation."
    echo "   See: https://cli.github.com/"
    HAS_GH=false
else
    echo "✅ GitHub CLI found"
    HAS_GH=true
fi

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

# Show latest release information
echo ""
echo "📋 Release Information"
echo "----------------------"
if [ "$HAS_GH" = true ]; then
    LATEST_RELEASE=$(gh release list --limit 1 --json tagName,name,publishedAt 2>/dev/null | grep -o '"tagName":"[^"]*"' | cut -d'"' -f4)
    if [ -n "$LATEST_RELEASE" ]; then
        echo "📝 Latest release: $LATEST_RELEASE"
    else
        echo "📝 No previous releases found"
    fi
else
    echo "📝 Cannot fetch releases (gh CLI not available)"
fi
echo ""

# Ask if user wants to create a release
if [ "$HAS_GH" = true ]; then
    echo "🚀 Would you like to create a GitHub release after building?"
    echo "   Options:"
    echo "   [y] Yes - Create release with APK"
    echo "   [n] No  - Just build the APK (default)"
    echo ""
    read -p "Create release? [y/N]: " CREATE_RELEASE
    CREATE_RELEASE=${CREATE_RELEASE:-n}
    
    if [[ $CREATE_RELEASE =~ ^[Yy]$ ]]; then
        echo ""
        echo "📦 Release Details"
        echo "------------------"
        
        # Get version tag
        read -p "Enter release version tag (e.g., v1.0.0): " RELEASE_TAG
        if [ -z "$RELEASE_TAG" ]; then
            echo "❌ Version tag is required for releases"
            exit 1
        fi
        
        # Get release title
        read -p "Enter release title [Cute Kana $RELEASE_TAG]: " RELEASE_TITLE
        RELEASE_TITLE=${RELEASE_TITLE:-"Cute Kana $RELEASE_TAG"}
        
        # Get release notes/description
        echo ""
        echo "Enter release notes/description (press Ctrl+D when done, or Ctrl+C to skip):"
        RELEASE_NOTES=$(cat)
        if [ -z "$RELEASE_NOTES" ]; then
            RELEASE_NOTES="Release $RELEASE_TAG of Cute Kana"
        fi
        
        echo ""
        echo "📋 Release Summary:"
        echo "   Tag:    $RELEASE_TAG"
        echo "   Title:  $RELEASE_TITLE"
        echo "   Notes:  ${RELEASE_NOTES:0:50}..."
        echo ""
        read -p "Proceed with build and release? [Y/n]: " CONFIRM
        CONFIRM=${CONFIRM:-y}
        
        if [[ ! $CONFIRM =~ ^[Yy]$ ]]; then
            echo "❌ Cancelled by user"
            exit 0
        fi
    fi
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
    
    # Find the APK file
    APK_PATH=$(find app/build/outputs/apk -name "*.apk" -type f | head -1)
    
    if [ -n "$APK_PATH" ]; then
        echo "📱 APK location: $APK_PATH"
        
        # Get APK size
        APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
        echo "📊 APK size: $APK_SIZE"
        echo ""
        
        # Create GitHub release if requested
        if [ "$HAS_GH" = true ] && [[ $CREATE_RELEASE =~ ^[Yy]$ ]]; then
            echo "🚀 Creating GitHub release..."
            echo ""
            
            # Check if release already exists
            EXISTING_RELEASE=$(gh release view "$RELEASE_TAG" 2>/dev/null && echo "exists" || echo "")
            
            if [ -n "$EXISTING_RELEASE" ]; then
                echo "⚠️  Release $RELEASE_TAG already exists!"
                read -p "Update existing release with new APK? [y/N]: " UPDATE_RELEASE
                if [[ $UPDATE_RELEASE =~ ^[Yy]$ ]]; then
                    echo "📤 Uploading APK to existing release..."
                    gh release upload "$RELEASE_TAG" "$APK_PATH" --clobber
                    echo ""
                    echo "✅ APK uploaded to existing release: $RELEASE_TAG"
                else
                    echo "⏭️  Skipping release upload"
                fi
            else
                # Create new release
                echo "📤 Creating new release and uploading APK..."
                
                # Create release with notes
                gh release create "$RELEASE_TAG" \
                    --title "$RELEASE_TITLE" \
                    --notes "$RELEASE_NOTES" \
                    "$APK_PATH"
                
                if [ $? -eq 0 ]; then
                    echo ""
                    echo "✅ Release created successfully!"
                    echo ""
                    echo "🔗 Release URL:"
                    gh release view "$RELEASE_TAG" --json url | grep -o '"url":"[^"]*"' | cut -d'"' -f4
                else
                    echo ""
                    echo "❌ Failed to create release"
                    echo "   You can manually upload the APK from: $APK_PATH"
                fi
            fi
            
            echo ""
        fi
        
        echo "To install on device:"
        echo "  adb install $APK_PATH"
    else
        echo "❌ APK file not found in expected location"
    fi
else
    echo ""
    echo "❌ Build failed. Check the error messages above."
    exit 1
fi

echo ""
echo "🌸 Done!"
