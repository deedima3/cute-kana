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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.ui.components.CuteButton
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.PlayViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanaGuessGame(
    onBack: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    
    var gameConfig by remember { mutableStateOf<GameConfig?>(null) }
    var gameState by remember { mutableStateOf(KanaGuessState()) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var showGameOver by remember { mutableStateOf(false) }
    
    // Initialize game based on config
    LaunchedEffect(gameConfig) {
        gameConfig?.let { config ->
            gameState = createKanaGuessGame(config)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        when (gameConfig?.mode) {
                            KanaMode.HIRAGANA_ONLY -> "Guess Hiragana"
                            KanaMode.KATAKANA_ONLY -> "Guess Katakana"
                            KanaMode.BOTH -> "Guess Kana"
                            else -> "Kana Guess"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (gameConfig != null) {
                        // Streak display
                        if (gameState.streak > 0) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = StarYellow.copy(alpha = 0.2f)
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = "🔥 ${gameState.streak}",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = StarYellow
                                )
                            }
                        }
                        
                        // Score
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Mint.copy(alpha = 0.2f)
                            )
                        ) {
                            Text(
                                text = "🏆 ${gameState.score}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.titleMedium,
                                color = Mint
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
                gameConfig == null -> {
                    // Mode selection screen
                    KanaGuessModeSelection(
                        onSelectMode = { mode ->
                            gameConfig = GameConfig(mode = mode)
                        }
                    )
                }
                showGameOver -> {
                    KanaGuessGameOver(
                        score = gameState.score,
                        correctCount = gameState.correctCount,
                        totalQuestions = gameState.totalQuestions,
                        bestStreak = gameState.bestStreak,
                        highScore = maxOf(gameState.score, uiState.kanaGuessHighScore),
                        mode = gameConfig!!.mode,
                        onPlayAgain = {
                            gameState = createKanaGuessGame(gameConfig!!)
                            selectedAnswer = null
                            showResult = false
                            showGameOver = false
                        },
                        onChangeMode = {
                            gameConfig = null
                            showGameOver = false
                        },
                        onBack = onBack
                    )
                }
                else -> {
                    // Active game
                    KanaGuessGameContent(
                        gameState = gameState,
                        selectedAnswer = selectedAnswer,
                        showResult = showResult,
                        onAnswerSelected = { answer ->
                            if (!showResult) {
                                selectedAnswer = answer
                                showResult = true
                                
                                val isCorrect = answer == gameState.currentAnswer
                                gameState = if (isCorrect) {
                                    gameState.correctAnswer()
                                } else {
                                    gameState.wrongAnswer()
                                }
                                
                                // Check for game over or next question
                                if (gameState.totalQuestions >= 20) {
                                    // End game after 20 questions
                                    coroutineScope.launch {
                                        delay(1000)
                                        showGameOver = true
                                        viewModel.updateHighScore(PlayGameMode.KANA_GUESS, gameState.score)
                                    }
                                } else {
                                    // Next question after delay
                                    coroutineScope.launch {
                                        delay(1500)
                                        if (!showGameOver) {
                                            gameState = gameState.generateNextQuestion()
                                            selectedAnswer = null
                                            showResult = false
                                        }
                                    }
                                }
                            }
                        },
                        onSkip = {
                            gameState = gameState.skipQuestion()
                            selectedAnswer = null
                            showResult = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun KanaGuessModeSelection(
    onSelectMode: (KanaMode) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎌",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Kana Guess",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Choose your challenge:",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Hiragana only
        GameModeCard(
            title = "ひらがな",
            subtitle = "Hiragana Only",
            description = "あいうえお...",
            icon = "🔤",
            color = SakuraPink,
            onClick = { onSelectMode(KanaMode.HIRAGANA_ONLY) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Katakana only
        GameModeCard(
            title = "カタカナ",
            subtitle = "Katakana Only",
            description = "アイウエオ...",
            icon = "🔠",
            color = Lavender,
            onClick = { onSelectMode(KanaMode.KATAKANA_ONLY) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Both
        GameModeCard(
            title = "両方",
            subtitle = "Hiragana & Katakana",
            description = "Mix of both!",
            icon = "🎌",
            color = Mint,
            onClick = { onSelectMode(KanaMode.BOTH) }
        )
    }
}

@Composable
fun GameModeCard(
    title: String,
    subtitle: String,
    description: String,
    icon: String,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 32.sp)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    color = color
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = color
            )
        }
    }
}

@Composable
fun KanaGuessGameContent(
    gameState: KanaGuessState,
    selectedAnswer: String?,
    showResult: Boolean,
    onAnswerSelected: (String) -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress
        LinearProgressIndicator(
            progress = gameState.totalQuestions.toFloat() / 20f,
            modifier = Modifier.fillMaxWidth(),
            color = when (gameState.mode) {
                KanaMode.HIRAGANA_ONLY -> SakuraPink
                KanaMode.KATAKANA_ONLY -> Lavender
                KanaMode.BOTH -> Mint
            }
        )
        
        Text(
            text = "${gameState.totalQuestions}/20",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Question type indicator
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = "What is the reading of this character?",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // The character to guess
        Card(
            modifier = Modifier.size(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (gameState.mode) {
                    KanaMode.HIRAGANA_ONLY -> SakuraPink.copy(alpha = 0.2f)
                    KanaMode.KATAKANA_ONLY -> Lavender.copy(alpha = 0.2f)
                    KanaMode.BOTH -> Mint.copy(alpha = 0.2f)
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gameState.currentCharacter,
                    fontSize = 120.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Character type hint
        Text(
            text = if (gameState.currentCharacter.isHiragana()) "Hiragana" else "Katakana",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = if (gameState.mode == KanaMode.BOTH) Modifier else Modifier.alpha(0f)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Answer options
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            gameState.options.chunked(2).forEach { rowOptions ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowOptions.forEach { option ->
                        val isSelected = selectedAnswer == option
                        val isCorrect = option == gameState.currentAnswer
                        val buttonColor = when {
                            !showResult -> MaterialTheme.colorScheme.surface
                            isCorrect -> Mint
                            isSelected && !isCorrect -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.surface
                        }
                        val textColor = when {
                            !showResult -> MaterialTheme.colorScheme.onSurface
                            isCorrect -> Color.Black
                            isSelected && !isCorrect -> Color.White
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                        
                        Button(
                            onClick = { onAnswerSelected(option) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                disabledContainerColor = buttonColor,
                                disabledContentColor = textColor
                            ),
                            enabled = !showResult,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.headlineSmall,
                                color = textColor,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Feedback
        AnimatedVisibility(visible = showResult) {
            val isCorrect = selectedAnswer == gameState.currentAnswer
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) 
                        Mint.copy(alpha = 0.2f)
                    else 
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isCorrect) "✓ Correct!" else "✗ Wrong!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (isCorrect) Mint else MaterialTheme.colorScheme.error
                    )
                    
                    if (!isCorrect) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "The correct answer was: ${gameState.currentAnswer}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Mint
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Skip button
        TextButton(
            onClick = onSkip,
            enabled = !showResult,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Skip →")
        }
    }
}

@Composable
fun KanaGuessGameOver(
    score: Int,
    correctCount: Int,
    totalQuestions: Int,
    bestStreak: Int,
    highScore: Int,
    mode: KanaMode,
    onPlayAgain: () -> Unit,
    onChangeMode: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (score >= highScore) "🎉" else "🎌",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (score >= highScore) "New High Score!" else "Quiz Complete!",
            style = MaterialTheme.typography.headlineLarge,
            color = if (score >= highScore) StarYellow else Mint
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = when (mode) {
                KanaMode.HIRAGANA_ONLY -> "Hiragana Mode"
                KanaMode.KATAKANA_ONLY -> "Katakana Mode"
                KanaMode.BOTH -> "Mixed Mode"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    text = "$score",
                    style = MaterialTheme.typography.displayLarge,
                    color = Mint
                )
                Text(
                    text = "points",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    KanaGuessStatItem("✓ Correct", "$correctCount/$totalQuestions")
                    KanaGuessStatItem("🔥 Best Streak", "$bestStreak")
                    if (highScore > 0) {
                        KanaGuessStatItem("🏆 High", "$highScore")
                    }
                }
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
            onClick = onChangeMode,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Mode")
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Games")
        }
    }
}

@Composable
private fun KanaGuessStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Extension function to check if character is Hiragana
fun String.isHiragana(): Boolean {
    return this.all { it in '\u3040'..'\u309F' }
}

fun String.isKatakana(): Boolean {
    return this.all { it in '\u30A0'..'\u30FF' }
}

// Data classes
data class GameConfig(
    val mode: KanaMode
)

enum class KanaMode {
    HIRAGANA_ONLY,
    KATAKANA_ONLY,
    BOTH
}

data class KanaGuessState(
    val mode: KanaMode = KanaMode.BOTH,
    val currentCharacter: String = "",
    val currentAnswer: String = "",
    val options: List<String> = emptyList(),
    val score: Int = 0,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val correctCount: Int = 0,
    val totalQuestions: Int = 0,
    val usedCharacters: List<String> = emptyList()
) {
    fun correctAnswer(): KanaGuessState {
        val newStreak = streak + 1
        val points = 10 + (newStreak.coerceAtMost(5) * 2) // Bonus for streaks
        return copy(
            score = score + points,
            streak = newStreak,
            bestStreak = maxOf(bestStreak, newStreak),
            correctCount = correctCount + 1,
            totalQuestions = totalQuestions + 1,
            usedCharacters = usedCharacters + currentCharacter
        )
    }
    
    fun wrongAnswer(): KanaGuessState {
        return copy(
            streak = 0,
            totalQuestions = totalQuestions + 1,
            usedCharacters = usedCharacters + currentCharacter
        )
    }
    
    fun skipQuestion(): KanaGuessState {
        return copy(
            streak = 0,
            totalQuestions = totalQuestions + 1,
            usedCharacters = usedCharacters + currentCharacter
        )
    }
    
    fun nextQuestion(): KanaGuessState {
        // This would generate a new question - implemented in the create function
        return this
    }
}

// Kana data - Comprehensive including tenten, maru, and combinations
private val hiraganaData = listOf(
    // Basic vowels
    "あ" to "a", "い" to "i", "う" to "u", "え" to "e", "お" to "o",
    // K-group (ka)
    "か" to "ka", "き" to "ki", "く" to "ku", "け" to "ke", "こ" to "ko",
    // S-group (sa)
    "さ" to "sa", "し" to "shi", "す" to "su", "せ" to "se", "そ" to "so",
    // T-group (ta)
    "た" to "ta", "ち" to "chi", "つ" to "tsu", "て" to "te", "と" to "to",
    // N-group (na)
    "な" to "na", "に" to "ni", "ぬ" to "nu", "ね" to "ne", "の" to "no",
    // H-group (ha)
    "は" to "ha", "ひ" to "hi", "ふ" to "fu", "へ" to "he", "ほ" to "ho",
    // M-group (ma)
    "ま" to "ma", "み" to "mi", "む" to "mu", "め" to "me", "も" to "mo",
    // Y-group (ya)
    "や" to "ya", "ゆ" to "yu", "よ" to "yo",
    // R-group (ra)
    "ら" to "ra", "り" to "ri", "る" to "ru", "れ" to "re", "ろ" to "ro",
    // W-group (wa)
    "わ" to "wa", "を" to "wo", "ん" to "n",
    
    // G-group (ga) - with tenten
    "が" to "ga", "ぎ" to "gi", "ぐ" to "gu", "げ" to "ge", "ご" to "go",
    // Z-group (za) - with tenten
    "ざ" to "za", "じ" to "ji", "ず" to "zu", "ぜ" to "ze", "ぞ" to "zo",
    // D-group (da) - with tenten
    "だ" to "da", "ぢ" to "ji", "づ" to "zu", "で" to "de", "ど" to "do",
    // B-group (ba) - with tenten
    "ば" to "ba", "び" to "bi", "ぶ" to "bu", "べ" to "be", "ぼ" to "bo",
    // P-group (pa) - with maru
    "ぱ" to "pa", "ぴ" to "pi", "ぷ" to "pu", "ぺ" to "pe", "ぽ" to "po",
    
    // Ky-group (kya) - combined
    "きゃ" to "kya", "きゅ" to "kyu", "きょ" to "kyo",
    // Sh-group (sha) - combined
    "しゃ" to "sha", "しゅ" to "shu", "しょ" to "sho",
    // Ch-group (cha) - combined
    "ちゃ" to "cha", "ちゅ" to "chu", "ちょ" to "cho",
    // Ny-group (nya) - combined
    "にゃ" to "nya", "にゅ" to "nyu", "にょ" to "nyo",
    // Hy-group (hya) - combined
    "ひゃ" to "hya", "ひゅ" to "hyu", "ひょ" to "hyo",
    // My-group (mya) - combined
    "みゃ" to "mya", "みゅ" to "myu", "みょ" to "myo",
    // Ry-group (rya) - combined
    "りゃ" to "rya", "りゅ" to "ryu", "りょ" to "ryo",
    // Gy-group (gya) - combined with tenten
    "ぎゃ" to "gya", "ぎゅ" to "gyu", "ぎょ" to "gyo",
    // J-group (ja) - combined with tenten
    "じゃ" to "ja", "じゅ" to "ju", "じょ" to "jo",
    // By-group (bya) - combined with tenten
    "びゃ" to "bya", "びゅ" to "byu", "びょ" to "byo",
    // Py-group (pya) - combined with maru
    "ぴゃ" to "pya", "ぴゅ" to "pyu", "ぴょ" to "pyo"
)

private val katakanaData = listOf(
    // Basic vowels
    "ア" to "a", "イ" to "i", "ウ" to "u", "エ" to "e", "オ" to "o",
    // K-group (ka)
    "カ" to "ka", "キ" to "ki", "ク" to "ku", "ケ" to "ke", "コ" to "ko",
    // S-group (sa)
    "サ" to "sa", "シ" to "shi", "ス" to "su", "セ" to "se", "ソ" to "so",
    // T-group (ta)
    "タ" to "ta", "チ" to "chi", "ツ" to "tsu", "テ" to "te", "ト" to "to",
    // N-group (na)
    "ナ" to "na", "ニ" to "ni", "ヌ" to "nu", "ネ" to "ne", "ノ" to "no",
    // H-group (ha)
    "ハ" to "ha", "ヒ" to "hi", "フ" to "fu", "ヘ" to "he", "ホ" to "ho",
    // M-group (ma)
    "マ" to "ma", "ミ" to "mi", "ム" to "mu", "メ" to "me", "モ" to "mo",
    // Y-group (ya)
    "ヤ" to "ya", "ユ" to "yu", "ヨ" to "yo",
    // R-group (ra)
    "ラ" to "ra", "リ" to "ri", "ル" to "ru", "レ" to "re", "ロ" to "ro",
    // W-group (wa)
    "ワ" to "wa", "ヲ" to "wo", "ン" to "n",
    
    // G-group (ga) - with tenten
    "ガ" to "ga", "ギ" to "gi", "グ" to "gu", "ゲ" to "ge", "ゴ" to "go",
    // Z-group (za) - with tenten
    "ザ" to "za", "ジ" to "ji", "ズ" to "zu", "ゼ" to "ze", "ゾ" to "zo",
    // D-group (da) - with tenten
    "ダ" to "da", "ヂ" to "ji", "ヅ" to "zu", "デ" to "de", "ド" to "do",
    // B-group (ba) - with tenten
    "バ" to "ba", "ビ" to "bi", "ブ" to "bu", "ベ" to "be", "ボ" to "bo",
    // P-group (pa) - with maru
    "パ" to "pa", "ピ" to "pi", "プ" to "pu", "ペ" to "pe", "ポ" to "po",
    
    // Ky-group (kya) - combined
    "キャ" to "kya", "キュ" to "kyu", "キョ" to "kyo",
    // Sh-group (sha) - combined
    "シャ" to "sha", "シュ" to "shu", "ショ" to "sho",
    // Ch-group (cha) - combined
    "チャ" to "cha", "チュ" to "chu", "チョ" to "cho",
    // Ny-group (nya) - combined
    "ニャ" to "nya", "ニュ" to "nyu", "ニョ" to "nyo",
    // Hy-group (hya) - combined
    "ヒャ" to "hya", "ヒュ" to "hyu", "ヒョ" to "hyo",
    // My-group (mya) - combined
    "ミャ" to "mya", "ミュ" to "myu", "ミョ" to "myo",
    // Ry-group (rya) - combined
    "リャ" to "rya", "リュ" to "ryu", "リョ" to "ryo",
    // Gy-group (gya) - combined with tenten
    "ギャ" to "gya", "ギュ" to "gyu", "ギョ" to "gyo",
    // J-group (ja) - combined with tenten
    "ジャ" to "ja", "ジュ" to "ju", "ジョ" to "jo",
    // By-group (bya) - combined with tenten
    "ビャ" to "bya", "ビュ" to "byu", "ビョ" to "byo",
    // Py-group (pya) - combined with maru
    "ピャ" to "pya", "ピュ" to "pyu", "ピョ" to "pyo"
)

fun createKanaGuessGame(config: GameConfig): KanaGuessState {
    val availableKana = when (config.mode) {
        KanaMode.HIRAGANA_ONLY -> hiraganaData
        KanaMode.KATAKANA_ONLY -> katakanaData
        KanaMode.BOTH -> hiraganaData + katakanaData
    }
    
    val (char, answer) = availableKana.random()
    val wrongOptions = availableKana
        .filter { it.second != answer }
        .shuffled()
        .take(3)
        .map { it.second }
    val options = (listOf(answer) + wrongOptions).shuffled()
    
    return KanaGuessState(
        mode = config.mode,
        currentCharacter = char,
        currentAnswer = answer,
        options = options
    )
}

// Extension to generate next question
fun KanaGuessState.generateNextQuestion(): KanaGuessState {
    val availableKana = when (mode) {
        KanaMode.HIRAGANA_ONLY -> hiraganaData
        KanaMode.KATAKANA_ONLY -> katakanaData
        KanaMode.BOTH -> hiraganaData + katakanaData
    }.filter { it.first !in usedCharacters }
    
    // If all characters used, reset
    val pool = if (availableKana.isEmpty()) {
        when (mode) {
            KanaMode.HIRAGANA_ONLY -> hiraganaData
            KanaMode.KATAKANA_ONLY -> katakanaData
            KanaMode.BOTH -> hiraganaData + katakanaData
        }
    } else availableKana
    
    val (char, answer) = pool.random()
    val wrongOptions = (when (mode) {
        KanaMode.HIRAGANA_ONLY -> hiraganaData
        KanaMode.KATAKANA_ONLY -> katakanaData
        KanaMode.BOTH -> hiraganaData + katakanaData
    })
        .filter { it.second != answer }
        .shuffled()
        .take(3)
        .map { it.second }
    val options = (listOf(answer) + wrongOptions).shuffled()
    
    return copy(
        currentCharacter = char,
        currentAnswer = answer,
        options = options
    )
}
