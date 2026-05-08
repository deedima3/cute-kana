package com.cutekana.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutekana.data.repository.CharacterRepository
import com.cutekana.data.repository.MockTestRepository
import com.cutekana.ui.screens.mocktest.MockTestUiState
import com.cutekana.ui.screens.mocktest.MockTestScreenState
import com.cutekana.data.model.JlptLevel
import com.cutekana.data.model.SectionType
import com.cutekana.data.model.TestMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.delay

@HiltViewModel
class MockTestViewModel @Inject constructor(
    private val mockTestRepository: MockTestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MockTestUiState())
    val uiState: StateFlow<MockTestUiState> = _uiState.asStateFlow()

    private var currentTestId: String = ""
    private var questions: List<com.cutekana.data.model.QuestionData> = emptyList()
    private var userAnswers = mutableMapOf<Int, String>()

    fun startTest(level: JlptLevel, mode: TestMode) {
        viewModelScope.launch {
            val testSession = mockTestRepository.createTestSession(level, mode)
            currentTestId = testSession.sessionId
            questions = testSession.questions
            
            _uiState.value = MockTestUiState(
                screenState = MockTestScreenState.InProgress,
                jlptLevel = level,
                testMode = mode,
                totalQuestions = questions.size,
                timeRemaining = when (level) {
                    JlptLevel.N5 -> 5400 // 90 min
                    JlptLevel.N4 -> 6600 // 110 min
                    else -> 5400
                }
            )
            
            loadQuestion(0)
            startTimer()
        }
    }

    fun startSectionPractice(level: JlptLevel, section: SectionType) {
        // Create section-specific test
        startTest(level, TestMode.SECTION)
    }

    private fun loadQuestion(index: Int) {
        if (index < questions.size) {
            val question = questions[index]
            
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = index,
                currentQuestionText = getQuestionText(question),
                currentPassage = getPassageText(question),
                currentOptions = getOptions(question),
                selectedAnswer = userAnswers[index],
                currentSection = getQuestionSection(question)
            )
        }
    }

    private fun getQuestionText(question: com.cutekana.data.model.QuestionData): String {
        return when (question) {
            is com.cutekana.data.model.QuestionData.KanjiReadingData -> 
                "How to read 「${question.kanji}」 in this context?\n\n${question.contextSentence}"
            is com.cutekana.data.model.QuestionData.GrammarPatternData -> 
                question.sentence.replace("___", "＿＿＿")
            is com.cutekana.data.model.QuestionData.ReadingComprehensionData -> 
                question.subQuestions.firstOrNull()?.questionText ?: ""
            is com.cutekana.data.model.QuestionData.ListeningData -> 
                question.question
            else -> ""
        }
    }

    private fun getPassageText(question: com.cutekana.data.model.QuestionData): String? {
        return when (question) {
            is com.cutekana.data.model.QuestionData.ReadingComprehensionData -> question.passage
            else -> null
        }
    }

    private fun getOptions(question: com.cutekana.data.model.QuestionData): List<String> {
        return when (question) {
            is com.cutekana.data.model.QuestionData.KanjiReadingData -> question.options
            is com.cutekana.data.model.QuestionData.GrammarPatternData -> question.options
            is com.cutekana.data.model.QuestionData.ReadingComprehensionData -> 
                question.subQuestions.firstOrNull()?.options ?: emptyList()
            is com.cutekana.data.model.QuestionData.ListeningData -> question.options
            else -> emptyList()
        }
    }

    private fun getQuestionSection(question: com.cutekana.data.model.QuestionData): SectionType {
        return when (question) {
            is com.cutekana.data.model.QuestionData.KanjiReadingData -> SectionType.VOCABULARY
            is com.cutekana.data.model.QuestionData.GrammarPatternData -> SectionType.GRAMMAR
            is com.cutekana.data.model.QuestionData.ReadingComprehensionData -> SectionType.READING
            is com.cutekana.data.model.QuestionData.ListeningData -> SectionType.LISTENING
            else -> SectionType.VOCABULARY
        }
    }

    fun selectAnswer(answer: String) {
        val currentIndex = _uiState.value.currentQuestionIndex
        userAnswers[currentIndex] = answer
        
        _uiState.value = _uiState.value.copy(selectedAnswer = answer)
    }

    fun nextQuestion() {
        val nextIndex = _uiState.value.currentQuestionIndex + 1
        if (nextIndex < questions.size) {
            loadQuestion(nextIndex)
        }
    }

    fun previousQuestion() {
        val prevIndex = _uiState.value.currentQuestionIndex - 1
        if (prevIndex >= 0) {
            loadQuestion(prevIndex)
        }
    }

    fun submitTest() {
        viewModelScope.launch {
            // Calculate scores
            var totalScore = 0
            var maxScore = 0
            val sectionScores = mutableMapOf<SectionType, com.cutekana.data.model.SectionScoreData>()
            
            questions.forEachIndexed { index, question ->
                val userAnswer = userAnswers[index]
                val correctAnswer = getCorrectAnswer(question)
                val isCorrect = userAnswer == correctAnswer
                val section = getQuestionSection(question)
                
                if (isCorrect) totalScore += 3
                maxScore += 3
            }
            
            val isPassed = totalScore >= (maxScore * 0.45f) // N5 passing threshold
            
            _uiState.value = _uiState.value.copy(
                screenState = MockTestScreenState.Results,
                totalScore = totalScore,
                maxScore = maxScore,
                isPassed = isPassed,
                sectionScores = sectionScores
            )
        }
    }

    private fun getCorrectAnswer(question: com.cutekana.data.model.QuestionData): String {
        return when (question) {
            is com.cutekana.data.model.QuestionData.KanjiReadingData -> question.correctAnswer
            is com.cutekana.data.model.QuestionData.GrammarPatternData -> question.correctAnswer
            is com.cutekana.data.model.QuestionData.ReadingComprehensionData -> 
                question.subQuestions.firstOrNull()?.correctAnswer ?: ""
            is com.cutekana.data.model.QuestionData.ListeningData -> question.correctAnswer
            else -> ""
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_uiState.value.timeRemaining > 0 && 
                   _uiState.value.screenState == MockTestScreenState.InProgress) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    timeRemaining = _uiState.value.timeRemaining - 1
                )
            }
            
            if (_uiState.value.timeRemaining <= 0) {
                submitTest()
            }
        }
    }

    fun returnToMenu() {
        _uiState.value = MockTestUiState()
    }

    fun showHistory() {
        viewModelScope.launch {
            val history = mockTestRepository.getTestHistory()
            _uiState.value = _uiState.value.copy(
                screenState = MockTestScreenState.History,
                testHistory = history.map { 
                    com.cutekana.ui.screens.mocktest.TestHistoryItem(
                        testId = it.sessionId,
                        level = it.jlptLevel,
                        date = formatDate(it.endTime ?: it.startTime),
                        score = it.totalScore,
                        maxScore = it.maxScore,
                        isPassed = it.isPassed
                    )
                }
            )
        }
    }

    fun reviewMistakes() {
        // Navigate to review screen
    }

    fun retakeTest() {
        val level = _uiState.value.jlptLevel
        val mode = _uiState.value.testMode
        startTest(level, mode)
    }

    fun viewTestDetails(testId: String) {
        // Show test details
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }
}