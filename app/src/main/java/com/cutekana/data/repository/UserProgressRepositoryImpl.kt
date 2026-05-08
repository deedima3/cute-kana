package com.cutekana.data.repository

import android.content.SharedPreferences
import com.cutekana.data.local.AchievementDao
import com.cutekana.data.local.UserProgressDao
import com.cutekana.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProgressRepositoryImpl @Inject constructor(
    private val userProgressDao: UserProgressDao,
    private val achievementDao: AchievementDao,
    private val highScoresPrefs: SharedPreferences
) : UserProgressRepository {
    
    override suspend fun getUserProgress(): UserProgressEntity {
        return userProgressDao.getUserProgress() ?: UserProgressEntity()
    }
    
    override suspend fun updateUserProgress(progress: UserProgressEntity) {
        userProgressDao.updateUserProgress(progress)
    }
    
    override suspend fun getRecentAchievements(limit: Int): List<AchievementEntity> {
        return achievementDao.getRecentAchievements(limit)
    }
    
    override suspend fun unlockAchievement(achievementId: String) {
        val achievement = achievementDao.getAchievement(achievementId)
        if (achievement != null && !achievement.isUnlocked) {
            val updated = achievement.copy(
                isUnlocked = true,
                unlockedDate = System.currentTimeMillis()
            )
            achievementDao.updateAchievement(updated)
        }
    }
    
    override suspend fun getHighScores(): Map<String, Int> {
        return mapOf(
            "radical_builder" to highScoresPrefs.getInt("radical_builder", 0),
            "kanji_story" to highScoresPrefs.getInt("kanji_story", 0),
            "match_pairs" to highScoresPrefs.getInt("match_pairs", 0),
            "speed_quiz" to highScoresPrefs.getInt("speed_quiz", 0),
            "kana_guess" to highScoresPrefs.getInt("kana_guess", 0)
        )
    }

    override suspend fun updateHighScore(game: String, score: Int) {
        val currentHigh = highScoresPrefs.getInt(game, 0)
        if (score > currentHigh) {
            highScoresPrefs.edit().putInt(game, score).apply()
        }
    }
    
    override suspend fun getCollectedCards(): List<CollectedCardEntity> {
        // Implementation would query a collected cards DAO
        return emptyList()
    }
    
    override suspend fun collectCard(card: CollectedCardEntity) {
        // Implementation would insert into collected cards DAO
    }
    
    override suspend fun getStudySchedule(): StudyScheduleEntity? {
        // Implementation would query study schedule DAO
        return null
    }
    
    override suspend fun createStudySchedule(schedule: StudyScheduleEntity) {
        // Implementation would insert study schedule
    }

    override suspend fun spendOrbs(amount: Int) {
        val currentProgress = getUserProgress()
        val updated = currentProgress.copy(
            premiumOrbs = (currentProgress.premiumOrbs - amount).coerceAtLeast(0)
        )
        updateUserProgress(updated)
    }
}