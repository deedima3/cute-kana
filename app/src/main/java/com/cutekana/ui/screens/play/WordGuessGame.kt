package com.cutekana.ui.screens.play

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.cutekana.data.audio.SoundEffectManager
import com.cutekana.ui.components.CuteButton
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.PlayViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordGuessGame(
    onBack: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    
    var gameConfig by remember { mutableStateOf<WordGameConfig?>(null) }
    var gameState by remember { mutableStateOf(WordGuessState()) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var showGameOver by remember { mutableStateOf(false) }
    
    // Initialize sound effects
    DisposableEffect(Unit) {
        SoundEffectManager.initialize()
        onDispose { SoundEffectManager.release() }
    }
    
    // Initialize game based on config
    LaunchedEffect(gameConfig) {
        gameConfig?.let { config ->
            gameState = createWordGuessGame(config)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        when (gameConfig?.mode) {
                            KanaMode.HIRAGANA_ONLY -> "Guess Hiragana Words"
                            KanaMode.KATAKANA_ONLY -> "Guess Katakana Words"
                            KanaMode.BOTH -> "Guess Words"
                            else -> "Word Guess"
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
                    WordGuessModeSelection(
                        onSelectMode = { mode ->
                            gameConfig = WordGameConfig(mode = mode)
                        }
                    )
                }
                showGameOver -> {
                    WordGuessGameOver(
                        score = gameState.score,
                        correctCount = gameState.correctCount,
                        totalQuestions = gameState.totalQuestions,
                        bestStreak = gameState.bestStreak,
                        highScore = maxOf(gameState.score, uiState.kanaGuessHighScore),
                        mode = gameConfig!!.mode,
                        onPlayAgain = {
                            gameState = createWordGuessGame(gameConfig!!)
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
                    WordGuessGameContent(
                        gameState = gameState,
                        selectedAnswer = selectedAnswer,
                        showResult = showResult,
                        onAnswerSelected = { answer ->
                            if (!showResult) {
                                selectedAnswer = answer
                                showResult = true
                                
                                val isCorrect = answer == gameState.currentAnswer
                                gameState = if (isCorrect) {
                                    SoundEffectManager.playCorrect()
                                    gameState.correctAnswer()
                                } else {
                                    SoundEffectManager.playWrong()
                                    gameState.wrongAnswer()
                                }
                                
                                // Check for game over or next question
                                if (gameState.totalQuestions >= 20) {
                                    // End game after 20 questions
                                    coroutineScope.launch {
                                        delay(1000)
                                        SoundEffectManager.playGameOver()
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
fun WordGuessModeSelection(
    onSelectMode: (KanaMode) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📝",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Word Guess",
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
            subtitle = "Hiragana Words",
            description = "あいうえお...",
            icon = "🔤",
            color = SakuraPink,
            onClick = { onSelectMode(KanaMode.HIRAGANA_ONLY) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Katakana only
        GameModeCard(
            title = "カタカナ",
            subtitle = "Katakana Words",
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
fun WordGuessGameContent(
    gameState: WordGuessState,
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
                text = "What does this word mean?",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // The word to guess
        Card(
            modifier = Modifier.fillMaxWidth(),
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = gameState.currentWord,
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                
                // Show reading in small text
                Text(
                    text = "(${gameState.currentReading})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Answer options
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            gameState.options.forEach { option ->
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        disabledContainerColor = buttonColor,
                        disabledContentColor = textColor
                    ),
                    enabled = !showResult,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
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
                    
                    // Show word meaning/info
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${gameState.currentWord} = ${gameState.currentAnswer}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
fun WordGuessGameOver(
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
            text = if (score >= highScore) "🎉" else "📝",
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
                KanaMode.HIRAGANA_ONLY -> "Hiragana Words"
                KanaMode.KATAKANA_ONLY -> "Katakana Words"
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
                    WordGuessStatItem("✓ Correct", "$correctCount/$totalQuestions")
                    WordGuessStatItem("🔥 Best Streak", "$bestStreak")
                    if (highScore > 0) {
                        WordGuessStatItem("🏆 High", "$highScore")
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
private fun WordGuessStatItem(label: String, value: String) {
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

// Data classes
data class WordGameConfig(
    val mode: KanaMode
)

data class WordGuessState(
    val mode: KanaMode = KanaMode.BOTH,
    val currentWord: String = "",
    val currentReading: String = "",
    val currentAnswer: String = "",
    val options: List<String> = emptyList(),
    val score: Int = 0,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val correctCount: Int = 0,
    val totalQuestions: Int = 0,
    val usedWords: List<String> = emptyList()
) {
    fun correctAnswer(): WordGuessState {
        val newStreak = streak + 1
        val points = 10 + (newStreak.coerceAtMost(5) * 2) // Bonus for streaks
        return copy(
            score = score + points,
            streak = newStreak,
            bestStreak = maxOf(bestStreak, newStreak),
            correctCount = correctCount + 1,
            totalQuestions = totalQuestions + 1,
            usedWords = usedWords + currentWord
        )
    }
    
    fun wrongAnswer(): WordGuessState {
        return copy(
            streak = 0,
            totalQuestions = totalQuestions + 1,
            usedWords = usedWords + currentWord
        )
    }
    
    fun skipQuestion(): WordGuessState {
        return copy(
            streak = 0,
            totalQuestions = totalQuestions + 1,
            usedWords = usedWords + currentWord
        )
    }
}

// Word data - 150+ words
private val hiraganaWords = listOf(
    // Basic nouns
    Triple("いぬ", "inu", "dog"),
    Triple("ねこ", "neko", "cat"),
    Triple("とり", "tori", "bird"),
    Triple("さかな", "sakana", "fish"),
    Triple("うま", "uma", "horse"),
    Triple("うし", "ushi", "cow"),
    Triple("ぶた", "buta", "pig"),
    Triple("ひつじ", "hitsuji", "sheep"),
    Triple("さる", "saru", "monkey"),
    Triple("ぞう", "zou", "elephant"),
    Triple("しろ", "shiro", "white"),
    Triple("くろ", "kuro", "black"),
    Triple("あか", "aka", "red"),
    Triple("あお", "ao", "blue"),
    Triple("みどり", "midori", "green"),
    Triple("きいろ", "kiiro", "yellow"),
    Triple("ちゃいろ", "chairo", "brown"),
    Triple("むらさき", "murasaki", "purple"),
    Triple("おんな", "onna", "woman"),
    Triple("おとこ", "otoko", "man"),
    Triple("こども", "kodomo", "child"),
    Triple("ともだち", "tomodachi", "friend"),
    Triple("せんせい", "sensei", "teacher"),
    Triple("がくせい", "gakusei", "student"),
    Triple("いしゃ", "isha", "doctor"),
    Triple("かんごし", "kangoshi", "nurse"),
    Triple("しか", "shika", "dentist"),
    Triple("けいさつ", "keisatsu", "police"),
    Triple("えいぎょう", "eigyou", "business"),
    Triple("しごと", "shigoto", "job"),
    Triple("かいしゃ", "kaisha", "company"),
    Triple("がっこう", "gakkou", "school"),
    Triple("だいがく", "daigaku", "university"),
    Triple("びょういん", "byouin", "hospital"),
    Triple("やくそく", "yakusoku", "promise"),
    Triple("でんわ", "denwa", "telephone"),
    Triple("てがみ", "tegami", "letter"),
    Triple("めんきょ", "menkyo", "license"),
    Triple("かぎ", "kagi", "key"),
    Triple("さいふ", "saifu", "wallet"),
    Triple("かさ", "kasa", "umbrella"),
    Triple("ぼうし", "boushi", "hat"),
    Triple("くつ", "kutsu", "shoes"),
    Triple("ふく", "fuku", "clothes"),
    Triple("こっち", "kocchi", "this way"),
    Triple("そっち", "socchi", "that way"),
    Triple("あっち", "acchi", "that way over there"),
    Triple("どっち", "docchi", "which way"),
    Triple("いつも", "itsumo", "always"),
    Triple("ときどき", "tokidoki", "sometimes"),
    Triple("たいてい", "taitei", "usually"),
    Triple("めったに", "mettani", "rarely"),
    Triple("ぜんぜん", "zenzen", "not at all"),
    Triple("すこし", "sukoshi", "a little"),
    Triple("ちょっと", "chotto", "a little bit"),
    Triple("たくさん", "takusan", "many"),
    Triple("おおぜい", "oozei", "many people"),
    Triple("すぐ", "sugu", "immediately"),
    Triple("ゆっくり", "yukkuri", "slowly"),
    Triple("はやく", "hayaku", "quickly"),
    Triple("まっすぐ", "massugu", "straight"),
    Triple("いっしょ", "issho", "together"),
    Triple("ひとり", "hitori", "alone"),
    Triple("みんな", "minna", "everyone"),
    Triple("かぞく", "kazoku", "family"),
    Triple("りょうしん", "ryoushin", "parents"),
    Triple("きょうだい", "kyoudai", "siblings"),
    Triple("おとうと", "otouto", "younger brother"),
    Triple("いもうと", "imouto", "younger sister"),
    Triple("あね", "ane", "older sister"),
    Triple("あに", "ani", "older brother"),
    Triple("おかあさん", "okaasan", "mother"),
    Triple("おとうさん", "otousan", "father"),
    Triple("おじいさん", "ojiisan", "grandfather"),
    Triple("おばあさん", "obaasan", "grandmother"),
    Triple("いえ", "ie", "house"),
    Triple("うち", "uchi", "home"),
    Triple("へや", "heya", "room"),
    Triple("きっちん", "kicchin", "kitchen"),
    Triple("いま", "ima", "living room"),
    Triple("としょしつ", "toshoshitsu", "library room"),
    Triple("せんたくしつ", "sentakushitsu", "laundry room"),
    Triple("トイレ", "toire", "toilet"),
    Triple("ふろ", "furo", "bath"),
    Triple("かいだん", "kaidan", "stairs"),
    Triple("エレベーター", "erebeetaa", "elevator"),
    Triple("ごはん", "gohan", "rice/meal"),
    Triple("パン", "pan", "bread"),
    Triple("にく", "niku", "meat"),
    Triple("さかな", "sakana", "fish"),
    Triple("やさい", "yasai", "vegetables"),
    Triple("くだもの", "kudamono", "fruit"),
    Triple("みず", "mizu", "water"),
    Triple("おちゃ", "ocha", "tea"),
    Triple("ぎゅうにゅう", "gyuunyuu", "milk"),
    Triple("ジュース", "juusu", "juice"),
    Triple("ビール", "biiru", "beer"),
    Triple("ワイン", "wain", "wine"),
    Triple("コーヒー", "koohii", "coffee"),
    Triple("おさけ", "osake", "alcohol"),
    Triple("しお", "shio", "salt"),
    Triple("さとう", "satou", "sugar"),
    Triple("しょうゆ", "shouyu", "soy sauce"),
    Triple("ソース", "sosu", "sauce"),
    Triple("アイスクリーム", "aisukuriimu", "ice cream"),
    Triple("けーき", "keeki", "cake"),
    Triple("キャンディー", "kyandii", "candy"),
    Triple("チョコレート", "chokoreeto", "chocolate"),
    Triple("あさ", "asa", "morning"),
    Triple("ひる", "hiru", "noon"),
    Triple("ゆうがた", "yuugata", "evening"),
    Triple("よる", "yoru", "night"),
    Triple("こんばん", "konban", "this evening"),
    Triple("きょう", "kyou", "today"),
    Triple("あした", "ashita", "tomorrow"),
    Triple("あさって", "asatte", "day after tomorrow"),
    Triple("きのう", "kinou", "yesterday"),
    Triple("おととい", "ototoi", "day before yesterday"),
    Triple("まいにち", "mainichi", "every day"),
    Triple("まいあさ", "maiasa", "every morning"),
    Triple("まいばん", "maiban", "every night"),
    Triple("しゅうまつ", "shuumatsu", "weekend"),
    Triple("やすみ", "yasumi", "holiday"),
    Triple("きせつ", "kisetsu", "season"),
    Triple("はる", "haru", "spring"),
    Triple("なつ", "natsu", "summer"),
    Triple("あき", "aki", "autumn"),
    Triple("ふゆ", "fuyu", "winter"),
    Triple("てんき", "tenki", "weather"),
    Triple("はれ", "hare", "sunny"),
    Triple("くもり", "kumori", "cloudy"),
    Triple("あめ", "ame", "rain"),
    Triple("ゆき", "yuki", "snow"),
    Triple("かぜ", "kaze", "wind"),
    Triple("きおん", "kion", "temperature"),
    Triple("あつい", "atsui", "hot"),
    Triple("さむい", "samui", "cold"),
    Triple("あたたかい", "atatakai", "warm"),
    Triple("すずしい", "suzushii", "cool")
)

private val katakanaWords = listOf(
    // Foreign/common words
    Triple("ホテル", "hoteru", "hotel"),
    Triple("レストラン", "resutoran", "restaurant"),
    Triple("カフェ", "kafe", "cafe"),
    Triple("バー", "baa", "bar"),
    Triple("スーパー", "suupaa", "supermarket"),
    Triple("コンビニ", "konbini", "convenience store"),
    Triple("デパート", "depaato", "department store"),
    Triple("バス", "basu", "bus"),
    Triple("タクシー", "takushii", "taxi"),
    Triple("エアポート", "eapooto", "airport"),
    Triple("ホーム", "hoomu", "platform"),
    Triple("プラットホーム", "purattohoomu", "platform"),
    Triple("ゲート", "geeto", "gate"),
    Triple("パスポート", "pasupooto", "passport"),
    Triple("ビザ", "biza", "visa"),
    Triple("スーツケース", "suutsukeesu", "suitcase"),
    Triple("カバン", "kaban", "bag"),
    Triple("ポケット", "poketto", "pocket"),
    Triple("ベルト", "beruto", "belt"),
    Triple("メガネ", "megane", "glasses"),
    Triple("コンタクト", "kontakuto", "contact lenses"),
    Triple("サングラス", "sangurasu", "sunglasses"),
    Triple("アクセサリー", "akusesarii", "accessory"),
    Triple("ネックレス", "nekkuresu", "necklace"),
    Triple("イヤリング", "iyaringu", "earrings"),
    Triple("ブレスレット", "buresuretto", "bracelet"),
    Triple("リング", "ringu", "ring"),
    Triple("バッグ", "baggu", "bag"),
    Triple("ポーチ", "poochi", "pouch"),
    Triple("サイズ", "saizu", "size"),
    Triple("エス", "esu", "S size"),
    Triple("エム", "emu", "M size"),
    Triple("エル", "eru", "L size"),
    Triple("エックスエル", "ekkuseru", "XL size"),
    Triple("メートル", "meetoru", "meter"),
    Triple("センチ", "senchi", "centimeter"),
    Triple("グラム", "guramu", "gram"),
    Triple("キロ", "kiro", "kilogram/kilometer"),
    Triple("リットル", "rittoru", "liter"),
    Triple("パーセント", "paasento", "percent"),
    Triple("ドル", "doru", "dollar"),
    Triple("エン", "en", "yen"),
    Triple("ユーロ", "yuuro", "euro"),
    Triple("ポンド", "pondo", "pound"),
    Triple("コンピューター", "konpyuutaa", "computer"),
    Triple("パソコン", "pasokon", "personal computer"),
    Triple("ラップトップ", "rapputoppu", "laptop"),
    Triple("タブレット", "taburetto", "tablet"),
    Triple("スマホ", "sumaho", "smartphone"),
    Triple("アプリ", "apuri", "application"),
    Triple("ゲーム", "geemu", "game"),
    Triple("サイト", "saito", "website"),
    Triple("メール", "meeru", "email"),
    Triple("メッセージ", "messeeji", "message"),
    Triple("チャット", "chatto", "chat"),
    Triple("パスワード", "pasuwaado", "password"),
    Triple("データ", "deeta", "data"),
    Triple("ファイル", "fairu", "file"),
    Triple("ソフト", "sofuto", "software"),
    Triple("ハード", "haado", "hardware"),
    Triple("プリンター", "purintaa", "printer"),
    Triple("スキャナー", "sukyanaa", "scanner"),
    Triple("マウス", "mausu", "mouse"),
    Triple("キーボード", "kiiboodo", "keyboard"),
    Triple("モニター", "monitaa", "monitor"),
    Triple("スピーカー", "supiikaa", "speaker"),
    Triple("カメラ", "kamera", "camera"),
    Triple("テレビ", "terebi", "television"),
    Triple("ラジオ", "rajio", "radio"),
    Triple("ビデオ", "bideo", "video"),
    Triple("オーディオ", "oodio", "audio"),
    Triple("ヘッドホン", "heddohon", "headphones"),
    Triple("イヤホン", "iyahon", "earphones"),
    Triple("マイク", "maiku", "microphone"),
    Triple("リモコン", "rimokon", "remote control"),
    Triple("エアコン", "eakon", "air conditioner"),
    Triple("ヒーター", "hiitaa", "heater"),
    Triple("ファン", "fan", "fan"),
    Triple("ソファー", "sofaa", "sofa"),
    Triple("テーブル", "teeburu", "table"),
    Triple("ベッド", "beddo", "bed"),
    Triple("タンス", "tansu", "chest of drawers"),
    Triple("ドア", "doa", "door"),
    Triple("ウィンドウ", "windou", "window"),
    Triple("エレベーター", "erebeetaa", "elevator"),
    Triple("エスカレーター", "esukareetaa", "escalator"),
    Triple("トイレ", "toire", "toilet"),
    Triple("バスルーム", "basuruumu", "bathroom"),
    Triple("キッチン", "kicchin", "kitchen"),
    Triple("ベランダ", "beranda", "balcony"),
    Triple("ガレージ", "gareeji", "garage"),
    Triple("パーキング", "paakingu", "parking"),
    Triple("ガーデン", "gaaden", "garden"),
    Triple("ペット", "petto", "pet"),
    Triple("キャット", "kyatto", "cat"),
    Triple("ドッグ", "doggu", "dog"),
    Triple("バード", "baado", "bird"),
    Triple("フィッシュ", "fisshu", "fish"),
    Triple("アニマル", "animaru", "animal"),
    Triple("ハンバーガー", "hanbaagaa", "hamburger"),
    Triple("フライドポテト", "furaidopoteto", "french fries"),
    Triple("ピザ", "piza", "pizza"),
    Triple("サンドイッチ", "sandoicchi", "sandwich"),
    Triple("サラダ", "sarada", "salad"),
    Triple("スープ", "suupu", "soup"),
    Triple("ステーキ", "suteeki", "steak"),
    Triple("スパゲッティ", "supagetti", "spaghetti"),
    Triple("カレー", "karee", "curry"),
    Triple("ラーメン", "raamen", "ramen"),
    Triple("そば", "soba", "soba"),
    Triple("うどん", "udon", "udon"),
    Triple("すし", "sushi", "sushi"),
    Triple("てんぷら", "tenpura", "tempura"),
    Triple("やきにく", "yakiniku", "grilled meat"),
    Triple("おこのみやき", "okonomiyaki", "okonomiyaki"),
    Triple("たこやき", "takoyaki", "takoyaki"),
    Triple("パンケーキ", "pankiiki", "pancake"),
    Triple("ワッフル", "waffuru", "waffle"),
    Triple("ドーナツ", "doonatsu", "donut"),
    Triple("クッキー", "kukkii", "cookie"),
    Triple("ビスケット", "bisuketto", "biscuit"),
    Triple("パイ", "pai", "pie"),
    Triple("チョコレート", "chokoreeto", "chocolate"),
    Triple("キャンディー", "kyandii", "candy"),
    Triple("ガム", "gamu", "gum"),
    Triple("スナック", "sunakku", "snack"),
    Triple("チップス", "chippusu", "chips"),
    Triple("ポップコーン", "poppukoon", "popcorn"),
    Triple("アイス", "aisu", "ice"),
    Triple("アイスクリーム", "aisukuriimu", "ice cream"),
    Triple("シャーベット", "shaabetto", "sherbet"),
    Triple("ゼリー", "zerii", "jelly"),
    Triple("プリン", "purin", "pudding"),
    Triple("フルーツ", "furuutsu", "fruit"),
    Triple("アップル", "appuru", "apple"),
    Triple("オレンジ", "orenji", "orange"),
    Triple("バナナ", "banana", "banana"),
    Triple("グレープ", "gureepu", "grape"),
    Triple("レモン", "remon", "lemon"),
    Triple("ストロベリー", "sutoroberii", "strawberry"),
    Triple("メロン", "meron", "melon"),
    Triple("スイカ", "suika", "watermelon"),
    Triple("スポーツ", "supootsu", "sports"),
    Triple("サッカー", "sakkaa", "soccer"),
    Triple("バスケ", "basuke", "basketball"),
    Triple("テニス", "tenisu", "tennis"),
    Triple("ゴルフ", "gorufu", "golf"),
    Triple("スキー", "sukii", "ski"),
    Triple("スケート", "sukeeto", "skate"),
    Triple("スイミング", "suimingu", "swimming"),
    Triple("ボート", "boto", "boat"),
    Triple("サイクリング", "saikuringu", "cycling"),
    Triple("マラソン", "marason", "marathon"),
    Triple("ダンス", "dansu", "dance"),
    Triple("エクササイズ", "ekusasaizu", "exercise"),
    Triple("フィットネス", "fittoneisu", "fitness"),
    Triple("トレーニング", "toreeningu", "training"),
    Triple("ジム", "jimu", "gym"),
    Triple("クラブ", "kurabu", "club"),
    Triple("チーム", "chiimu", "team"),
    Triple("メンバー", "menbaa", "member"),
    Triple("ファン", "fan", "fan"),
    Triple("コーチ", "koochi", "coach"),
    Triple("プレーヤー", "pureiyaa", "player"),
    Triple("ゲーム", "geemu", "game"),
    Triple("ポイント", "pointo", "point"),
    Triple("スコア", "sukoa", "score"),
    Triple("レベル", "reberu", "level"),
    Triple("ランク", "ranku", "rank"),
    Triple("システム", "shisutemu", "system"),
    Triple("スタイル", "sutairu", "style"),
    Triple("デザイン", "dezain", "design"),
    Triple("カラー", "karaa", "color"),
    Triple("ブラック", "burakku", "black"),
    Triple("ホワイト", "howaito", "white"),
    Triple("レッド", "reddo", "red"),
    Triple("ブルー", "buruu", "blue"),
    Triple("グリーン", "guriin", "green"),
    Triple("イエロー", "ieroo", "yellow")
)

fun createWordGuessGame(config: WordGameConfig): WordGuessState {
    val availableWords = when (config.mode) {
        KanaMode.HIRAGANA_ONLY -> hiraganaWords
        KanaMode.KATAKANA_ONLY -> katakanaWords
        KanaMode.BOTH -> hiraganaWords + katakanaWords
    }
    
    val (word, reading, answer) = availableWords.random()
    val wrongOptions = availableWords
        .filter { it.third != answer }
        .shuffled()
        .take(3)
        .map { it.third }
    val options = (listOf(answer) + wrongOptions).shuffled()
    
    return WordGuessState(
        mode = config.mode,
        currentWord = word,
        currentReading = reading,
        currentAnswer = answer,
        options = options
    )
}

// Extension to generate next question
fun WordGuessState.generateNextQuestion(): WordGuessState {
    val availableWords = when (mode) {
        KanaMode.HIRAGANA_ONLY -> hiraganaWords
        KanaMode.KATAKANA_ONLY -> katakanaWords
        KanaMode.BOTH -> hiraganaWords + katakanaWords
    }.filter { it.first !in usedWords }
    
    // If all words used, reset
    val pool = if (availableWords.isEmpty()) {
        when (mode) {
            KanaMode.HIRAGANA_ONLY -> hiraganaWords
            KanaMode.KATAKANA_ONLY -> katakanaWords
            KanaMode.BOTH -> hiraganaWords + katakanaWords
        }
    } else availableWords
    
    val (word, reading, answer) = pool.random()
    val wrongOptions = (when (mode) {
        KanaMode.HIRAGANA_ONLY -> hiraganaWords
        KanaMode.KATAKANA_ONLY -> katakanaWords
        KanaMode.BOTH -> hiraganaWords + katakanaWords
    })
        .filter { it.third != answer }
        .shuffled()
        .take(3)
        .map { it.third }
    val options = (listOf(answer) + wrongOptions).shuffled()
    
    return copy(
        currentWord = word,
        currentReading = reading,
        currentAnswer = answer,
        options = options
    )
}
