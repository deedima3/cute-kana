// Top-level build file for Cute Kana - Japanese Learning App
// Using K2 Compiler for enhanced performance

plugins {
    id("com.android.application") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
}

buildscript {
    extra.apply {
        set("compose_compiler", "1.5.7")
        set("compose_bom", "2023.10.01")
        set("room_version", "2.6.1")
        set("hilt_version", "2.48")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}