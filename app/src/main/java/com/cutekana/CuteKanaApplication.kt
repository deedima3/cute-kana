package com.cutekana

import android.app.Application
import com.cutekana.data.local.CuteKanaDatabase
import com.cutekana.data.local.SeedData
import com.cutekana.data.model.AchievementEntity
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class CuteKanaApplication : Application() {

    @Inject
    lateinit var database: CuteKanaDatabase

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // Populate database with seed data on first launch
        applicationScope.launch {
            populateDatabaseIfNeeded()
        }
    }

    private suspend fun populateDatabaseIfNeeded() {
        // Check if we already have characters
        val existingCount = database.characterDao().getAllHiragana().size +
                           database.characterDao().getAllKatakana().size

        if (existingCount == 0) {
            // Insert characters (Kana + Kanji)
            val characters = SeedData.generateCharacters()
            database.characterDao().insertCharacters(characters)

            // Insert achievements
            database.achievementDao().insertAchievements(SeedData.generateAchievements())

            // Insert initial user progress
            val existingProgress = database.userProgressDao().getUserProgress()
            if (existingProgress == null) {
                database.userProgressDao().insertUserProgress(SeedData.getInitialUserProgress())
            }
        }
    }
}