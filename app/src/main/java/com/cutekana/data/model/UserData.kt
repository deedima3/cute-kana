package com.cutekana.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey
    val id: Int = 1,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastStudyDate: Long? = null,
    val totalStudyTimeMinutes: Int = 0,
    val totalCharactersLearned: Int = 0,
    val totalKanjiLearned: Int = 0,
    val totalMockTestsTaken: Int = 0,
    val highestN5Score: Int = 0,
    val highestN4Score: Int = 0,
    val kanaOrbs: Int = 0,
    val kanjiOrbs: Int = 0,
    val premiumOrbs: Int = 0,
    val hasCompletedOnboarding: Boolean = false,
    val preferredDailyGoal: Int = 15,  // minutes
    val enableNotifications: Boolean = true,
    val enableSound: Boolean = true,
    val enableHaptic: Boolean = true,
    val themePreference: ThemePreference = ThemePreference.SYSTEM
)

@Serializable
enum class ThemePreference {
    LIGHT,
    DARK,
    SYSTEM
}

@Entity(tableName = "collected_cards")
data class CollectedCardEntity(
    @PrimaryKey
    val cardId: String,
    val characterId: String,
    val rarity: Rarity,
    val collectedDate: Long,
    val isFavorite: Boolean = false,
    val viewedCount: Int = 0
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val achievementId: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false,
    val unlockedDate: Long? = null,
    val progress: Int = 0,
    val maxProgress: Int = 1
)

@Entity(tableName = "writing_attempts")
data class WritingAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val characterId: String,
    val attemptDate: Long,
    val accuracy: Float,
    val strokeOrderCorrect: Boolean,
    val timeTakenMs: Long,
    val wasCorrect: Boolean
)

@Entity(tableName = "mock_tests")
data class MockTestSessionEntity(
    @PrimaryKey
    val sessionId: String,
    val jlptLevel: JlptLevel,
    val testMode: TestMode,
    val startTime: Long,
    val endTime: Long?,
    val totalScore: Int,
    val maxScore: Int,
    val isPassed: Boolean,
    val sectionScores: Map<SectionType, SectionScoreData>,
    val questions: List<QuestionData>,
    val weakAreas: List<String>
)

@Serializable
enum class TestMode {
    FULL,
    SECTION,
    QUICK,
    MARATHON
}

@Serializable
enum class SectionType {
    VOCABULARY,
    GRAMMAR,
    READING,
    LISTENING
}

@Serializable
data class SectionScoreData(
    val correctCount: Int,
    val incorrectCount: Int,
    val unansweredCount: Int,
    val score: Int,
    val maxScore: Int,
    val percentage: Float,
    val averageTimePerQuestionMs: Long
)

@Serializable
sealed class QuestionData {
    abstract val id: String
    abstract val correctAnswer: String
    abstract val userAnswer: String?
    abstract val isCorrect: Boolean
    abstract val timeTakenMs: Long
    
    @Serializable
    data class KanjiReadingData(
        override val id: String,
        val kanji: String,
        val contextSentence: String,
        val options: List<String>,
        override val correctAnswer: String,
        override val userAnswer: String?,
        override val isCorrect: Boolean,
        override val timeTakenMs: Long,
        val hint: String?
    ) : QuestionData()
    
    @Serializable
    data class GrammarPatternData(
        override val id: String,
        val sentence: String,
        val blankPosition: Int,
        val options: List<String>,
        override val correctAnswer: String,
        override val userAnswer: String?,
        override val isCorrect: Boolean,
        override val timeTakenMs: Long,
        val grammarPoint: String
    ) : QuestionData()
    
    @Serializable
    data class ReadingComprehensionData(
        override val id: String,
        val passage: String,
        val subQuestions: List<ReadingSubQuestion>,
        override val correctAnswer: String,
        override val userAnswer: String?,
        override val isCorrect: Boolean,
        override val timeTakenMs: Long
    ) : QuestionData()
    
    @Serializable
    data class ListeningData(
        override val id: String,
        val audioScript: String,
        val question: String,
        val options: List<String>,
        override val correctAnswer: String,
        override val userAnswer: String?,
        override val isCorrect: Boolean,
        override val timeTakenMs: Long,
        val speakers: List<String>
    ) : QuestionData()
}

@Serializable
data class ReadingSubQuestion(
    val id: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val userAnswer: String?,
    val isCorrect: Boolean
)

@Entity(tableName = "study_schedule")
data class StudyScheduleEntity(
    @PrimaryKey
    val scheduleId: String,
    val targetLevel: JlptLevel,
    val targetDate: Long,
    val weeklyPlans: List<WeeklyPlanData>,
    val isActive: Boolean = true,
    val createdDate: Long
)

@Serializable
data class WeeklyPlanData(
    val weekNumber: Int,
    val focus: String,
    val dailyGoals: List<DailyGoalData>
)

@Serializable
data class DailyGoalData(
    val dayOfWeek: Int,
    val tasks: List<TaskData>,
    val estimatedMinutes: Int,
    val isCompleted: Boolean = false
)

@Serializable
data class TaskData(
    val taskId: String,
    val type: TaskType,
    val description: String,
    val targetCount: Int,
    val completedCount: Int = 0
)

@Serializable
enum class TaskType {
    LEARN_NEW,
    REVIEW,
    WRITING_PRACTICE,
    MOCK_TEST,
    QUIZ
}