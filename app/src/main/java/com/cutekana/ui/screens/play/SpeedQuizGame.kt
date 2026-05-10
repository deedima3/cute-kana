package com.cutekana.ui.screens.play

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.audio.SoundEffectManager
import com.cutekana.ui.components.CuteButton
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.PlayViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedQuizGame(
    onBack: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    
    var gameState by remember { mutableStateOf(SpeedQuizState()) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var gameStarted by remember { mutableStateOf(false) }
    var showGameOver by remember { mutableStateOf(false) }
    
    // Initialize sound effects
    DisposableEffect(Unit) {
        SoundEffectManager.initialize()
        onDispose { SoundEffectManager.release() }
    }
    
    // Timer
    LaunchedEffect(gameStarted, gameState.isActive) {
        if (gameStarted && gameState.isActive && gameState.timeLeft > 0) {
            while (gameState.timeLeft > 0 && gameState.isActive) {
                delay(1000)
                gameState = gameState.tick()
            }
            if (gameState.timeLeft <= 0) {
                gameState = gameState.copy(isActive = false)
                SoundEffectManager.playGameOver()
                showGameOver = true
                viewModel.updateHighScore(PlayGameMode.SPEED_QUIZ, gameState.score)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Speed Quiz") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (gameStarted) {
                        // Timer
                        val timeColor = when {
                            gameState.timeLeft <= 5 -> MaterialTheme.colorScheme.error
                            gameState.timeLeft <= 10 -> StarYellow
                            else -> MaterialTheme.colorScheme.primary
                        }
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = timeColor.copy(alpha = 0.2f)
                            )
                        ) {
                            Text(
                                text = "⏱️ ${gameState.timeLeft}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.titleMedium,
                                color = timeColor
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Score
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = StarYellow.copy(alpha = 0.2f)
                            )
                        ) {
                            Text(
                                text = "🏆 ${gameState.score}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.titleMedium,
                                color = StarYellow
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                !gameStarted -> {
                    SpeedQuizStartScreen(
                        highScore = uiState.speedQuizHighScore,
                        onStart = {
                            gameStarted = true
                            gameState = SpeedQuizState().nextQuestion()
                        }
                    )
                }
                showGameOver -> {
                    SpeedQuizGameOver(
                        score = gameState.score,
                        correctAnswers = gameState.correctAnswers,
                        highScore = maxOf(gameState.score, uiState.speedQuizHighScore),
                        onPlayAgain = {
                            showGameOver = false
                            gameState = SpeedQuizState()
                            selectedAnswer = null
                            showResult = false
                            gameStarted = true
                            gameState = gameState.nextQuestion()
                        },
                        onBack = onBack
                    )
                }
                else -> {
                    // Active game
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Question counter
                        Text(
                            text = "Question ${gameState.currentQuestion + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Question
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.5f),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = gameState.currentKana,
                                    fontSize = 120.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Answer buttons
                        gameState.options.forEach { option ->
                            val isSelected = selectedAnswer == option
                            val buttonColor = when {
                                showResult && option == gameState.correctAnswer -> Mint
                                showResult && isSelected && !isCorrect -> MaterialTheme.colorScheme.error
                                isSelected -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.surface
                            }
                            
                            Button(
                                onClick = {
                                if (!showResult) {
                                    selectedAnswer = option
                                    isCorrect = option == gameState.correctAnswer
                                    showResult = true
                                    
                                    if (isCorrect) {
                                        SoundEffectManager.playCorrect()
                                        gameState = gameState.correct(10 + gameState.timeLeft)
                                    } else {
                                        SoundEffectManager.playWrong()
                                        gameState = gameState.wrong()
                                    }
                                        
                                        // Next question after delay
                                        coroutineScope.launch {
                                            delay(1500)
                                            if (gameState.isActive) {
                                                gameState = gameState.nextQuestion()
                                                selectedAnswer = null
                                                showResult = false
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor
                                ),
                                enabled = !showResult
                            ) {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                        
                        // Result feedback
                        AnimatedVisibility(visible = showResult) {
                            Text(
                                text = if (isCorrect) "✓ Correct!" else "✗ Wrong!",
                                style = MaterialTheme.typography.headlineSmall,
                                color = if (isCorrect) Mint else MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpeedQuizStartScreen(
    highScore: Int,
    onStart: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "⚡",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Speed Quiz",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFFFF9800)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Answer as fast as you can!\n30 seconds, how many can you get?",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        if (highScore > 0) {
            Text(
                text = "High Score: $highScore",
                style = MaterialTheme.typography.titleMedium,
                color = StarYellow
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        CuteButton(
            text = "▶️ Start Quiz",
            onClick = onStart,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SpeedQuizGameOver(
    score: Int,
    correctAnswers: Int,
    highScore: Int,
    onPlayAgain: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (score >= highScore) "🎉" else "⏱️",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (score >= highScore) "New High Score!" else "Time's Up!",
            style = MaterialTheme.typography.headlineLarge,
            color = if (score >= highScore) StarYellow else MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Score: $score",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFFF9800)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Correct: $correctAnswers",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "High Score: $highScore",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        CuteButton(
            text = "🔄 Play Again",
            onClick = onPlayAgain,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Games")
        }
    }
}

// Kana data
private val kanaData = listOf(
    "あ" to "a", "い" to "i", "う" to "u", "え" to "e", "お" to "o",
    "か" to "ka", "き" to "ki", "く" to "ku", "け" to "ke", "こ" to "ko",
    "さ" to "sa", "し" to "shi", "す" to "su", "せ" to "se", "そ" to "so",
    "た" to "ta", "ち" to "chi", "つ" to "tsu", "て" to "te", "と" to "to",
    "な" to "na", "に" to "ni", "ぬ" to "nu", "ね" to "ne", "の" to "no"
)

data class SpeedQuizState(
    val currentQuestion: Int = 0,
    val currentKana: String = "",
    val correctAnswer: String = "",
    val options: List<String> = emptyList(),
    val score: Int = 0,
    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val timeLeft: Int = 30,
    val isActive: Boolean = true
) {
    fun nextQuestion(): SpeedQuizState {
        val (kana, romaji) = kanaData.random()
        val wrongOptions = kanaData.filter { it.second != romaji }
            .shuffled()
            .take(3)
            .map { it.second }
        val allOptions = (listOf(romaji) + wrongOptions).shuffled()
        
        return copy(
            currentQuestion = currentQuestion + 1,
            currentKana = kana,
            correctAnswer = romaji,
            options = allOptions,
            timeLeft = 30,
            isActive = true
        )
    }
    
    fun correct(points: Int): SpeedQuizState {
        return copy(
            score = score + points,
            correctAnswers = correctAnswers + 1
        )
    }
    
    fun wrong(): SpeedQuizState {
        return copy(wrongAnswers = wrongAnswers + 1)
    }
    
    fun tick(): SpeedQuizState {
        return copy(timeLeft = (timeLeft - 1).coerceAtLeast(0))
    }
}
