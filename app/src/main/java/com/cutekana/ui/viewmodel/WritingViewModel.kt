package com.cutekana.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutekana.data.repository.CharacterRepository
import com.cutekana.data.repository.UserProgressRepository
import com.cutekana.ui.screens.home.AchievementUiModel
import com.cutekana.ui.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserProgressRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            // Load user progress
            val userProgress = userRepository.getUserProgress()
            val recentAchievements = userRepository.getRecentAchievements(5)
            
            // Load study stats
            val totalLearned = characterRepository.getLearnedCharactersCount()
            val totalKanji = characterRepository.getLearnedKanjiCount()
            val n5Progress = characterRepository.getJlptProgress(com.cutekana.data.model.JlptLevel.N5)
            val n4Progress = characterRepository.getJlptProgress(com.cutekana.data.model.JlptLevel.N4)
            
            _uiState.value = HomeUiState(
                currentStreak = userProgress.currentStreak,
                kanaOrbs = userProgress.kanaOrbs,
                kanjiOrbs = userProgress.kanjiOrbs,
                dailyProgress = calculateDailyProgress(userProgress),
                completedMinutes = userProgress.totalStudyTimeMinutes % userProgress.preferredDailyGoal,
                currentLevel = getCurrentLevelName(),
                currentLesson = getCurrentLessonName(),
                lessonProgress = 0.35f,
                totalCharactersLearned = totalLearned,
                totalKanjiLearned = totalKanji,
                totalStudyTimeMinutes = userProgress.totalStudyTimeMinutes,
                n5Progress = n5Progress,
                n4Progress = n4Progress,
                recentAchievements = recentAchievements.map { 
                    AchievementUiModel(
                        icon = getAchievementEmoji(it.iconName),
                        title = it.title,
                        description = it.description,
                        unlockedDate = it.unlockedDate ?: 0L
                    )
                }
            )
        }
    }

    private fun calculateDailyProgress(userProgress: com.cutekana.data.model.UserProgressEntity): Float {
        return (userProgress.totalStudyTimeMinutes % userProgress.preferredDailyGoal).toFloat() / 
               userProgress.preferredDailyGoal
    }

    private fun getCurrentLevelName(): String {
        return "Hiragana"
    }

    private fun getCurrentLessonName(): String {
        return "Lesson 1: あ-row"
    }
    
    private fun getAchievementEmoji(iconName: String): String {
        return when (iconName) {
            "emoji_footprints" -> "👣"
            "emoji_flower" -> "🌸"
            "emoji_sakura" -> "🌸"
            "emoji_star" -> "⭐"
            "emoji_diamond" -> "💎"
            "emoji_book" -> "📚"
            "emoji_scroll" -> "📜"
            "emoji_crown" -> "👑"
            "emoji_fire" -> "🔥"
            "emoji_calendar" -> "📅"
            "emoji_bolt" -> "⚡"
            "emoji_card" -> "🎴"
            "emoji_cards" -> "🃏"
            "emoji_pencil" -> "✏️"
            "emoji_certificate" -> "📜"
            "emoji_moon" -> "🌙"
            "emoji_sun" -> "☀️"
            else -> "🏆"
        }
    }
}

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val strokeRecognizer: com.cutekana.data.ml.StrokeRecognizer
) : ViewModel() {

    private val _uiState = MutableStateFlow(com.cutekana.ui.screens.writing.WritingUiState())
    val uiState: StateFlow<com.cutekana.ui.screens.writing.WritingUiState> = _uiState.asStateFlow()

    fun setMode(mode: com.cutekana.ui.screens.writing.WritingScreenMode) {
        _uiState.value = _uiState.value.copy(mode = mode)
        if (mode != com.cutekana.ui.screens.writing.WritingScreenMode.MENU) {
            loadCharactersForMode(mode)
        }
    }

    private fun loadCharactersForMode(mode: com.cutekana.ui.screens.writing.WritingScreenMode) {
        viewModelScope.launch {
            val characters = when (mode) {
                com.cutekana.ui.screens.writing.WritingScreenMode.GUIDED -> 
                    characterRepository.getCharactersForGuidedPractice()
                com.cutekana.ui.screens.writing.WritingScreenMode.FREE -> 
                    characterRepository.getCharactersForFreeWriting()
                com.cutekana.ui.screens.writing.WritingScreenMode.SPEED -> 
                    characterRepository.getCharactersForSpeedChallenge()
                com.cutekana.ui.screens.writing.WritingScreenMode.MEMORY -> 
                    characterRepository.getCharactersForMemoryMode()
                else -> emptyList()
            }
            
            if (characters.isNotEmpty()) {
                loadCharacter(characters[0])
            }
        }
    }

    private fun loadCharacter(charId: String) {
        viewModelScope.launch {
            val char = characterRepository.getCharacterById(charId)
            val strokes = com.cutekana.data.model.KanaStrokeData.getStrokes(char.character)
            
            _uiState.value = _uiState.value.copy(
                currentCharacter = char.character,
                currentRomaji = char.romaji,
                currentStrokes = strokes,
                currentStrokeIndex = 0,
                totalStrokes = strokes.size,
                recognitionResult = null
            )
        }
    }

    // Store the last drawn stroke for manual checking
    private var lastDrawnStroke: List<com.cutekana.data.model.PointF> = emptyList()
    
    fun onStrokeCollected(points: List<com.cutekana.data.model.PointF>) {
        // Just store the points, don't auto-check
        lastDrawnStroke = points
        // Clear previous result when drawing new stroke
        _uiState.value = _uiState.value.copy(recognitionResult = null)
    }
    
    fun checkCurrentStroke() {
        val currentStroke = _uiState.value.currentStrokeIndex
        val targetStrokes = _uiState.value.currentStrokes
        
        if (currentStroke < targetStrokes.size && lastDrawnStroke.isNotEmpty()) {
            val result = strokeRecognizer.recognize(
                userStrokes = listOf(lastDrawnStroke),
                targetCharacter = _uiState.value.currentCharacter
            )
            
            _uiState.value = _uiState.value.copy(recognitionResult = result)
            
            if (result.isCorrect) {
                // Move to next stroke or character automatically on correct
                if (currentStroke < targetStrokes.size - 1) {
                    _uiState.value = _uiState.value.copy(
                        currentStrokeIndex = currentStroke + 1,
                        recognitionResult = null
                    )
                    lastDrawnStroke = emptyList()
                }
            }
        }
    }
    
    @Deprecated("Use onStrokeCollected + checkCurrentStroke instead")
    fun onStrokeComplete(points: List<com.cutekana.data.model.PointF>) {
        lastDrawnStroke = points
        checkCurrentStroke()
    }

    fun nextCharacter() {
        val currentIndex = _uiState.value.currentIndex
        if (currentIndex < _uiState.value.totalCharacters - 1) {
            _uiState.value = _uiState.value.copy(
                currentIndex = currentIndex + 1,
                currentStrokeIndex = 0,
                recognitionResult = null
            )
            // Load next character
        } else {
            // Finish practice
            setMode(com.cutekana.ui.screens.writing.WritingScreenMode.MENU)
        }
    }

    fun resetCurrent() {
        _uiState.value = _uiState.value.copy(
            currentStrokeIndex = 0,
            recognitionResult = null
        )
    }
}