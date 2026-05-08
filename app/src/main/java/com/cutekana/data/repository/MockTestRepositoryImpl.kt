package com.cutekana.data.repository

import com.cutekana.data.local.MockTestDao
import com.cutekana.data.model.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockTestRepositoryImpl @Inject constructor(
    private val mockTestDao: MockTestDao
) : MockTestRepository {
    
    override suspend fun createTestSession(level: JlptLevel, mode: TestMode): MockTestSessionEntity {
        val questions = when (mode) {
            TestMode.FULL -> generateFullTestQuestions(level)
            TestMode.SECTION -> generateSectionQuestions(level, SectionType.VOCABULARY)
            TestMode.QUICK -> generateQuickQuestions(level)
            TestMode.MARATHON -> generateMarathonQuestions(level)
        }
        
        return MockTestSessionEntity(
            sessionId = UUID.randomUUID().toString(),
            jlptLevel = level,
            testMode = mode,
            startTime = System.currentTimeMillis(),
            endTime = null,
            totalScore = 0,
            maxScore = questions.size * 3,
            isPassed = false,
            sectionScores = emptyMap(),
            questions = questions,
            weakAreas = emptyList()
        )
    }
    
    override suspend fun saveTestSession(session: MockTestSessionEntity) {
        mockTestDao.insertTestSession(session)
    }
    
    override suspend fun getTestHistory(): List<MockTestSessionEntity> {
        return mockTestDao.getTestHistory()
    }
    
    override suspend fun getTestSession(sessionId: String): MockTestSessionEntity? {
        return mockTestDao.getTestSession(sessionId)
    }
    
    override suspend fun getQuestionsForLevel(level: JlptLevel): List<QuestionData> {
        return generateFullTestQuestions(level)
    }
    
    override suspend fun getQuestionsForSection(
        level: JlptLevel,
        section: SectionType
    ): List<QuestionData> {
        return generateSectionQuestions(level, section)
    }
    
    private fun generateFullTestQuestions(level: JlptLevel): List<QuestionData> {
        return when (level) {
            JlptLevel.N5 -> JLPTQuestionBank.getN5Questions().take(60)
            JlptLevel.N4 -> JLPTQuestionBank.getN4Questions().take(70)
            else -> JLPTQuestionBank.getN5Questions().take(60)
        }
    }
    
    private fun generateSectionQuestions(level: JlptLevel, section: SectionType): List<QuestionData> {
        val allQuestions = when (level) {
            JlptLevel.N5 -> JLPTQuestionBank.getN5Questions()
            JlptLevel.N4 -> JLPTQuestionBank.getN4Questions()
            else -> JLPTQuestionBank.getN5Questions()
        }
        
        return when (section) {
            SectionType.VOCABULARY -> allQuestions.filter { it is QuestionData.KanjiReadingData }.take(20)
            SectionType.GRAMMAR -> allQuestions.filter { it is QuestionData.GrammarPatternData }.take(20)
            SectionType.READING -> allQuestions.filter { it is QuestionData.ReadingComprehensionData }.take(10)
            SectionType.LISTENING -> allQuestions.filter { it is QuestionData.ListeningData }.take(10)
        }
    }
    
    private fun generateQuickQuestions(level: JlptLevel): List<QuestionData> {
        return when (level) {
            JlptLevel.N5 -> JLPTQuestionBank.getN5Questions().take(10)
            JlptLevel.N4 -> JLPTQuestionBank.getN4Questions().take(10)
            else -> JLPTQuestionBank.getN5Questions().take(10)
        }
    }
    
    private fun generateMarathonQuestions(level: JlptLevel): List<QuestionData> {
        return when (level) {
            JlptLevel.N5 -> JLPTQuestionBank.getN5Questions().take(50)
            JlptLevel.N4 -> JLPTQuestionBank.getN4Questions().take(50)
            else -> JLPTQuestionBank.getN5Questions().take(50)
        }
    }
}