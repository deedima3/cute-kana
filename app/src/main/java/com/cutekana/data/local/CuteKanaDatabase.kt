package com.cutekana.data.local

import androidx.room.*
import com.cutekana.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE type = 'HIRAGANA' ORDER BY row, id")
    suspend fun getAllHiragana(): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE type = 'KATAKANA' ORDER BY row, id")
    suspend fun getAllKatakana(): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE type = 'KANJI' ORDER BY id")
    suspend fun getAllKanji(): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE type IN ('HIRAGANA', 'KATAKANA')")
    suspend fun getAllKana(): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE type = 'COMPOUND'")
    suspend fun getAllCompounds(): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE type = 'SCENE'")
    suspend fun getAllScenes(): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE type = 'KANJI' AND jlptLevel = :level")
    suspend fun getKanjiByLevel(level: JlptLevel): List<CharacterEntity>
    
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: String): CharacterEntity?
    
    @Query("SELECT * FROM characters WHERE character = :character LIMIT 1")
    suspend fun getCharacterByChar(character: String): CharacterEntity?
    
    @Query("SELECT COUNT(*) FROM characters WHERE isUnlocked = 1 AND type IN ('HIRAGANA', 'KATAKANA')")
    suspend fun getLearnedCharactersCount(): Int
    
    @Query("SELECT COUNT(*) FROM characters WHERE isUnlocked = 1 AND type = 'KANJI'")
    suspend fun getLearnedKanjiCount(): Int
    
    @Query("SELECT COUNT(*) FROM characters WHERE type = 'KANJI' AND jlptLevel = :level AND isUnlocked = 1")
    suspend fun getLearnedKanjiCountByLevel(level: JlptLevel): Int
    
    @Query("SELECT COUNT(*) FROM characters WHERE type = 'KANJI' AND jlptLevel = :level")
    suspend fun getTotalKanjiCountByLevel(level: JlptLevel): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    
    @Update
    suspend fun updateCharacter(character: CharacterEntity)
}

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    suspend fun getUserProgress(): UserProgressEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(progress: UserProgressEntity)
    
    @Update
    suspend fun updateUserProgress(progress: UserProgressEntity)
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements ORDER BY unlockedDate DESC LIMIT :limit")
    suspend fun getRecentAchievements(limit: Int): List<AchievementEntity>
    
    @Query("SELECT * FROM achievements WHERE achievementId = :id")
    suspend fun getAchievement(id: String): AchievementEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<AchievementEntity>)
    
    @Update
    suspend fun updateAchievement(achievement: AchievementEntity)
}

@Dao
interface MockTestDao {
    @Query("SELECT * FROM mock_tests ORDER BY endTime DESC")
    suspend fun getTestHistory(): List<MockTestSessionEntity>
    
    @Query("SELECT * FROM mock_tests WHERE sessionId = :sessionId")
    suspend fun getTestSession(sessionId: String): MockTestSessionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestSession(session: MockTestSessionEntity)
    
    @Update
    suspend fun updateTestSession(session: MockTestSessionEntity)
}

@Database(
    entities = [
        CharacterEntity::class,
        UserProgressEntity::class,
        AchievementEntity::class,
        CollectedCardEntity::class,
        MockTestSessionEntity::class,
        StudyScheduleEntity::class,
        WritingAttemptEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [
        StrokeConverter::class,
        RarityConverter::class,
        JlptLevelConverter::class,
        CharacterTypeConverter::class,
        QuestionListConverter::class,
        TestModeConverter::class,
        SectionTypeConverter::class,
        TaskTypeConverter::class,
        ThemePreferenceConverter::class,
        StringListConverter::class,
        WeeklyPlanListConverter::class,
        SectionScoreMapConverter::class
    ]
)
abstract class CuteKanaDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun mockTestDao(): MockTestDao
}