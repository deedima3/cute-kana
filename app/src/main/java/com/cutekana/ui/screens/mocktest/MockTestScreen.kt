package com.cutekana.ui.screens.mocktest

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.model.JlptLevel
import com.cutekana.data.model.SectionType
import com.cutekana.data.model.TestMode
import com.cutekana.ui.components.*
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.MockTestViewModel
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockTestScreen(
    onBack: () -> Unit,
    viewModel: MockTestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JLPT Mock Tests") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        when (uiState.screenState) {
            is MockTestScreenState.Menu -> TestMenu(
                onStartFullTest = { level -> viewModel.startTest(level, TestMode.FULL) },
                onStartSectionPractice = { level, section -> 
                    viewModel.startSectionPractice(level, section) 
                },
                onViewHistory = { viewModel.showHistory() },
                modifier = Modifier.padding(padding)
            )
            
            is MockTestScreenState.InProgress -> TestInProgress(
                state = uiState,
                onSelectAnswer = viewModel::selectAnswer,
                onNextQuestion = viewModel::nextQuestion,
                onPreviousQuestion = viewModel::previousQuestion,
                onSubmit = viewModel::submitTest,
                modifier = Modifier.padding(padding)
            )
            
            is MockTestScreenState.Results -> TestResults(
                state = uiState,
                onBackToMenu = { viewModel.returnToMenu() },
                onReviewMistakes = { viewModel.reviewMistakes() },
                onRetake = { viewModel.retakeTest() },
                modifier = Modifier.padding(padding)
            )
            
            is MockTestScreenState.History -> TestHistory(
                history = uiState.testHistory,
                onBack = { viewModel.returnToMenu() },
                onViewDetails = { viewModel.viewTestDetails(it) },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun TestMenu(
    onStartFullTest: (JlptLevel) -> Unit,
    onStartSectionPractice: (JlptLevel, SectionType) -> Unit,
    onViewHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Choose Test Type",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        
        // N5 Full Test
        item {
            TestTypeCard(
                level = "JLPT N5",
                description = "Beginner level full mock test (90 min)",
                duration = "90 min • 60 questions",
                color = Mint,
                onClick = { onStartFullTest(JlptLevel.N5) }
            )
        }
        
        // N4 Full Test
        item {
            TestTypeCard(
                level = "JLPT N4",
                description = "Elementary level full mock test (110 min)",
                duration = "110 min • 70 questions",
                color = Lavender,
                onClick = { onStartFullTest(JlptLevel.N4) }
            )
        }
        
        item {
            Text(
                text = "Section Practice",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        // Section practice options
        item {
            SectionPracticeGrid(
                onVocabularyClick = { onStartSectionPractice(JlptLevel.N5, SectionType.VOCABULARY) },
                onGrammarClick = { onStartSectionPractice(JlptLevel.N5, SectionType.GRAMMAR) },
                onReadingClick = { onStartSectionPractice(JlptLevel.N5, SectionType.READING) },
                onListeningClick = { onStartSectionPractice(JlptLevel.N5, SectionType.LISTENING) }
            )
        }
        
        item {
            CuteOutlineButton(
                text = "📊 View Test History",
                onClick = onViewHistory,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Bottom spacing to account for bottom navigation bar
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun TestTypeCard(
    level: String,
    description: String,
    duration: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = level,
                        style = MaterialTheme.typography.labelLarge,
                        color = color
                    )
                }
                
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start",
                    tint = color
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = duration,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SectionPracticeGrid(
    onVocabularyClick: () -> Unit,
    onGrammarClick: () -> Unit,
    onReadingClick: () -> Unit,
    onListeningClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionCard(
                title = "Vocabulary",
                icon = "📝",
                color = SakuraPink,
                onClick = onVocabularyClick,
                modifier = Modifier.weight(1f)
            )
            SectionCard(
                title = "Grammar",
                icon = "📐",
                color = Lavender,
                onClick = onGrammarClick,
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionCard(
                title = "Reading",
                icon = "📖",
                color = Mint,
                onClick = onReadingClick,
                modifier = Modifier.weight(1f)
            )
            SectionCard(
                title = "Listening",
                icon = "🎧",
                color = StarYellow,
                onClick = onListeningClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1.3f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = icon, fontSize = 24.sp)
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = color
            )
        }
    }
}

@Composable
fun TestInProgress(
    state: MockTestUiState,
    onSelectAnswer: (String) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Timer and progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Timer
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Time",
                    tint = if (state.timeRemaining < 300) MaterialTheme.colorScheme.error 
                           else MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = formatTime(state.timeRemaining),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (state.timeRemaining < 300) MaterialTheme.colorScheme.error 
                           else MaterialTheme.colorScheme.primary
                )
            }
            
            // Progress
            Text(
                text = "${state.currentQuestionIndex + 1} / ${state.totalQuestions}",
                style = MaterialTheme.typography.labelLarge
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Progress bar
        CuteProgressBar(
            progress = (state.currentQuestionIndex + 1).toFloat() / state.totalQuestions,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Question card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Question type badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (state.currentSection) {
                                SectionType.VOCABULARY -> SakuraPink.copy(alpha = 0.2f)
                                SectionType.GRAMMAR -> Lavender.copy(alpha = 0.2f)
                                SectionType.READING -> Mint.copy(alpha = 0.2f)
                                SectionType.LISTENING -> StarYellow.copy(alpha = 0.2f)
                            }
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = state.currentSection.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelMedium,
                        color = when (state.currentSection) {
                            SectionType.VOCABULARY -> SakuraPink
                            SectionType.GRAMMAR -> Lavender
                            SectionType.READING -> Mint
                            SectionType.LISTENING -> StarYellow
                        }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Question text
                Text(
                    text = state.currentQuestionText,
                    style = MaterialTheme.typography.titleMedium
                )
                
                // Passage for reading questions
                if (state.currentPassage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = state.currentPassage,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Options
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    state.currentOptions.forEach { option ->
                        val isSelected = state.selectedAnswer == option
                        OptionButton(
                            option = option,
                            isSelected = isSelected,
                            onClick = { onSelectAnswer(option) }
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CuteOutlineButton(
                text = "Previous",
                onClick = onPreviousQuestion,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            if (state.currentQuestionIndex < state.totalQuestions - 1) {
                CuteButton(
                    text = "Next",
                    onClick = onNextQuestion,
                    modifier = Modifier.weight(1f)
                )
            } else {
                CuteButton(
                    text = "Submit",
                    onClick = onSubmit,
                    modifier = Modifier.weight(1f),
                    containerColor = Mint
                )
            }
        }
    }
}

@Composable
fun OptionButton(
    option: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else null
    ) {
        Text(
            text = option,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TestResults(
    state: MockTestUiState,
    onBackToMenu: () -> Unit,
    onReviewMistakes: () -> Unit,
    onRetake: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Result header
        Text(
            text = if (state.isPassed) "🎉 Congratulations!" else "Keep Practicing!",
            style = MaterialTheme.typography.headlineMedium,
            color = if (state.isPassed) Mint else MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Score circle
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(75.dp))
                .background(
                    if (state.isPassed) Mint.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.errorContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${state.totalScore}",
                    style = MaterialTheme.typography.displayMedium,
                    color = if (state.isPassed) Mint else MaterialTheme.colorScheme.error
                )
                Text(
                    text = "/ ${state.maxScore} points",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = if (state.isPassed) "✅ PASSED!" else "❌ Did not pass",
            style = MaterialTheme.typography.titleMedium,
            color = if (state.isPassed) Mint else MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Section breakdown
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Section Breakdown",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                state.sectionScores.forEach { (section, score) ->
                    SectionScoreRow(
                        section = section.name.lowercase().replaceFirstChar { it.uppercase() },
                        score = score.score,
                        maxScore = score.maxScore,
                        percentage = score.percentage
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Action buttons
        CuteButton(
            text = "Review Mistakes",
            onClick = onReviewMistakes,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row {
            CuteOutlineButton(
                text = "Retake Test",
                onClick = onRetake,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            CuteOutlineButton(
                text = "Back to Menu",
                onClick = onBackToMenu,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SectionScoreRow(
    section: String,
    score: Int,
    maxScore: Int,
    percentage: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = section,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = "$score / $maxScore",
            style = MaterialTheme.typography.labelMedium
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        CuteProgressBar(
            progress = percentage,
            color = when {
                percentage >= 0.8f -> Mint
                percentage >= 0.6f -> StarYellow
                else -> MaterialTheme.colorScheme.error
            },
            modifier = Modifier.width(100.dp)
        )
    }
}

@Composable
fun TestHistory(
    history: List<TestHistoryItem>,
    onBack: () -> Unit,
    onViewDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Test History",
            style = MaterialTheme.typography.headlineSmall
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (history.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tests taken yet.\nStart your first mock test!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(history) { item ->
                    HistoryCard(
                        item = item,
                        onClick = { onViewDetails(item.testId) }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        CuteOutlineButton(
            text = "Back",
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HistoryCard(
    item: TestHistoryItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (item.isPassed) Mint.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.errorContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (item.isPassed) "✓" else "✗",
                    color = if (item.isPassed) Mint else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "JLPT ${item.level.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = "${item.score}/${item.maxScore}",
                style = MaterialTheme.typography.titleMedium,
                color = if (item.isPassed) Mint else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
}

// UI State
data class MockTestUiState(
    val screenState: MockTestScreenState = MockTestScreenState.Menu,
    val jlptLevel: JlptLevel = JlptLevel.N5,
    val testMode: TestMode = TestMode.FULL,
    val currentSection: SectionType = SectionType.VOCABULARY,
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 60,
    val currentQuestionText: String = "",
    val currentPassage: String? = null,
    val currentOptions: List<String> = emptyList(),
    val selectedAnswer: String? = null,
    val timeRemaining: Int = 5400, // 90 minutes in seconds
    val totalScore: Int = 0,
    val maxScore: Int = 180,
    val isPassed: Boolean = false,
    val sectionScores: Map<SectionType, com.cutekana.data.model.SectionScoreData> = emptyMap(),
    val testHistory: List<TestHistoryItem> = emptyList()
)

sealed class MockTestScreenState {
    object Menu : MockTestScreenState()
    object InProgress : MockTestScreenState()
    object Results : MockTestScreenState()
    object History : MockTestScreenState()
}

data class TestHistoryItem(
    val testId: String,
    val level: JlptLevel,
    val date: String,
    val score: Int,
    val maxScore: Int,
    val isPassed: Boolean
)