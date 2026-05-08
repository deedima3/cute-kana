package com.cutekana.data.repository

import com.cutekana.data.model.*
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllHiragana(): List<CharacterEntity>
    suspend fun getAllKatakana(): List<CharacterEntity>
    suspend fun getAllKanji(): List<CharacterEntity>
    suspend fun getAllKana(): List<CharacterEntity>
    suspend fun getAllCompounds(): List<CharacterEntity>
    suspend fun getAllScenes(): List<CharacterEntity>
    
    suspend fun getKanjiByLevel(level: JlptLevel): List<CharacterEntity>
    suspend fun getCharacterById(id: String): CharacterEntity
    suspend fun getCharacterByChar(character: String): CharacterEntity
    
    suspend fun getCharactersForGuidedPractice(): List<String>
    suspend fun getCharactersForFreeWriting(): List<String>
    suspend fun getCharactersForSpeedChallenge(): List<String>
    suspend fun getCharactersForMemoryMode(): List<String>
    
    suspend fun getLearnedCharactersCount(): Int
    suspend fun getLearnedKanjiCount(): Int
    suspend fun getJlptProgress(level: JlptLevel): Float
    
    suspend fun updateCharacterProgress(characterId: String, masteryLevel: Int)
    suspend fun unlockCharacter(characterId: String)
    
    // Summon/gacha methods
    suspend fun unlockRandomKana(): String?
    suspend fun unlockRandomN5Kanji(): String?
    suspend fun unlockRandomN4Kanji(): String?
}

interface UserProgressRepository {
    suspend fun getUserProgress(): UserProgressEntity
    suspend fun updateUserProgress(progress: UserProgressEntity)
    
    suspend fun getRecentAchievements(limit: Int): List<AchievementEntity>
    suspend fun unlockAchievement(achievementId: String)
    
    suspend fun getHighScores(): Map<String, Int>
    suspend fun updateHighScore(game: String, score: Int)
    
    suspend fun getCollectedCards(): List<CollectedCardEntity>
    suspend fun collectCard(card: CollectedCardEntity)
    
    suspend fun getStudySchedule(): StudyScheduleEntity?
    suspend fun createStudySchedule(schedule: StudyScheduleEntity)
    
    suspend fun spendOrbs(amount: Int)
}

interface MockTestRepository {
    suspend fun createTestSession(level: JlptLevel, mode: TestMode): MockTestSessionEntity
    suspend fun saveTestSession(session: MockTestSessionEntity)
    suspend fun getTestHistory(): List<MockTestSessionEntity>
    suspend fun getTestSession(sessionId: String): MockTestSessionEntity?
    
    suspend fun getQuestionsForLevel(level: JlptLevel): List<QuestionData>
    suspend fun getQuestionsForSection(level: JlptLevel, section: SectionType): List<QuestionData>
}