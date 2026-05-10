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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiStoryGame(
    onBack: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var currentScene by remember { mutableIntStateOf(0) }
    var selectedAnswers by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
    var showResults by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    
    // Initialize sound effects
    DisposableEffect(Unit) {
        SoundEffectManager.initialize()
        onDispose { SoundEffectManager.release() }
    }
    
    val scenes = kanjiStoryScenes
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kanji Story") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!showResults) {
                        // Progress
                        Text(
                            text = "${currentScene + 1}/${scenes.size}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    } else {
                        // Score
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Mint.copy(alpha = 0.2f)
                            )
                        ) {
                            Text(
                                text = "🏆 $score",
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
        ) {
            when {
                showResults -> {
                    KanjiStoryResults(
                        score = score,
                        totalPossible = scenes.size * 20,
                        highScore = maxOf(score, uiState.kanjiStoryHighScore),
                        selectedAnswers = selectedAnswers,
                        scenes = scenes,
                        onPlayAgain = {
                            currentScene = 0
                            selectedAnswers = emptyMap()
                            showResults = false
                            score = 0
                        },
                        onBack = onBack
                    )
                }
                else -> {
                    // Scene view
                    val scene = scenes[currentScene]
                    
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        // Scene card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Mint.copy(alpha = 0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                // Scene title
                                Text(
                                    text = scene.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Mint
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                // Story text with kanji blanks
                                Text(
                                    text = buildAnnotatedStory(scene, selectedAnswers[currentScene]),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                // Character image/emoji representation
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Mint.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = scene.emoji,
                                        fontSize = 80.sp
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Question
                        Text(
                            text = "Fill in the blank:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Which kanji fits: ${scene.targetWord}?",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Options
                        scene.options.forEach { option ->
                            val isSelected = selectedAnswers[currentScene] == option.kanji
                            
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        selectedAnswers = selectedAnswers + (currentScene to option.kanji)
                                        SoundEffectManager.playClick()
                                    },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) 
                                        Mint.copy(alpha = 0.3f)
                                    else 
                                        MaterialTheme.colorScheme.surface
                                ),
                                border = if (isSelected) {
                                    androidx.compose.foundation.BorderStroke(
                                        2.dp, Mint
                                    )
                                } else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Kanji
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Mint.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = option.kanji,
                                            fontSize = 32.sp
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(16.dp))
                                    
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = option.meaning,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Text(
                                            text = option.reading,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = Mint
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Navigation
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = { 
                                    if (currentScene > 0) currentScene-- 
                                },
                                enabled = currentScene > 0
                            ) {
                                Text("← Previous")
                            }
                            
                            if (currentScene < scenes.size - 1) {
                                CuteButton(
                                    text = "Next →",
                                    onClick = { currentScene++ },
                                    enabled = selectedAnswers.containsKey(currentScene)
                                )
                            } else {
                                CuteButton(
                                    text = "✓ Complete Story",
                                    onClick = {
                                        // Calculate score
                                        score = scenes.mapIndexed { index, scene ->
                                            if (selectedAnswers[index] == scene.correctAnswer) 20 else 0
                                        }.sum()
                                        // Play appropriate sound
                                        val correctCount = scenes.filterIndexed { index, scene ->
                                            selectedAnswers[index] == scene.correctAnswer
                                        }.size
                                        when {
                                            correctCount == scenes.size -> SoundEffectManager.playVictory()
                                            correctCount >= scenes.size / 2 -> SoundEffectManager.playCorrect()
                                            else -> SoundEffectManager.playGameOver()
                                        }
                                        showResults = true
                                        viewModel.updateHighScore(PlayGameMode.KANJI_STORY, score)
                                    },
                                    enabled = selectedAnswers.size == scenes.size
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun buildAnnotatedStory(scene: StoryScene, selectedKanji: String?): String {
    return if (selectedKanji != null) {
        scene.story.replace("___", selectedKanji)
    } else {
        scene.story
    }
}

@Composable
fun KanjiStoryResults(
    score: Int,
    totalPossible: Int,
    highScore: Int,
    selectedAnswers: Map<Int, String>,
    scenes: List<StoryScene>,
    onPlayAgain: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (score == totalPossible) "🌟" else "📖",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (score == totalPossible) "Perfect Score!" else "Story Complete!",
            style = MaterialTheme.typography.headlineLarge,
            color = if (score == totalPossible) StarYellow else Mint
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
                    text = "$score / $totalPossible",
                    style = MaterialTheme.typography.displayMedium,
                    color = Mint
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "${(score * 100 / totalPossible)}% Correct",
                    style = MaterialTheme.typography.titleMedium
                )
                
                if (highScore > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "High Score: $highScore",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Review answers
        Text(
            text = "Review",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        scenes.forEachIndexed { index, scene ->
            val selected = selectedAnswers[index]
            val isCorrect = selected == scene.correctAnswer
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCorrect) 
                        Mint.copy(alpha = 0.2f)
                    else 
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isCorrect) "✓" else "✗",
                        fontSize = 24.sp,
                        color = if (isCorrect) Mint else MaterialTheme.colorScheme.error
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = scene.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Your answer: ${selected ?: "-"}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        if (!isCorrect) {
                            Text(
                                text = "Correct: ${scene.correctAnswer} (${scene.targetWord})",
                                style = MaterialTheme.typography.bodySmall,
                                color = Mint
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        CuteButton(
            text = "🔄 Read Another Story",
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

// Story data
data class StoryOption(
    val kanji: String,
    val meaning: String,
    val reading: String
)

data class StoryScene(
    val title: String,
    val story: String,
    val emoji: String,
    val targetWord: String,
    val correctAnswer: String,
    val options: List<StoryOption>
)

private val kanjiStoryScenes = listOf(
    // Scene 1: 朝の日課
    StoryScene(
        title = "朝の日課",
        story = "毎日、___に早起きします。窓から___が差し込みます。___を飲んでから、会社へ行きます。",
        emoji = "🌅☀️🍵",
        targetWord = "朝・日・茶",
        correctAnswer = "朝",
        options = listOf(
            StoryOption("朝", "あさ", "あさ (asa) - 朝"),
            StoryOption("昼", "ひる", "ひる (hiru) - 昼"),
            StoryOption("夜", "よる", "よる (yoru) - 夜"),
            StoryOption("夕", "ゆう", "ゆう (yuu) - 夕")
        )
    ),
    // Scene 2: 公園での一日
    StoryScene(
        title = "公園での一日",
        story = "今日、___へ行きました。大きな___が日陰を作っていました。子供たちは___のそばで楽しそうに遊んでいました。",
        emoji = "🌳🌲⛲",
        targetWord = "公園・木・池",
        correctAnswer = "公園",
        options = listOf(
            StoryOption("公園", "こうえん", "こうえん (kouen) - 公園"),
            StoryOption("学校", "がっこう", "がっこう (gakkou) - 学校"),
            StoryOption("図書館", "としょかん", "としょかん (toshokan) - 図書館"),
            StoryOption("映画館", "えいがかん", "えいがかん (eigakan) - 映画館")
        )
    ),
    // Scene 3: 美味しい食事
    StoryScene(
        title = "美味しい食事",
        story = "私の好きな___は寿司です。海から取れた新鮮な___を使っています。___が腕を振るって作ります。",
        emoji = "🍣🐟👨‍🍳",
        targetWord = "食べ物・魚・料理人",
        correctAnswer = "食",
        options = listOf(
            StoryOption("食", "たべる", "たべる (taberu) - 食べる"),
            StoryOption("飲", "のむ", "のむ (nomu) - 飲む"),
            StoryOption("作", "つくる", "つくる (tsukuru) - 作る"),
            StoryOption("焼", "やく", "やく (yaku) - 焼く")
        )
    ),
    // Scene 4: 学びの旅
    StoryScene(
        title = "学びの旅",
        story = "毎日新しいことを___のが好きです。___を開くと、新しい世界に旅できます。___は多くのことを理解するのに役立ちます。",
        emoji = "📚📖✏️",
        targetWord = "学ぶ・本・勉強",
        correctAnswer = "学",
        options = listOf(
            StoryOption("学", "まなぶ", "まなぶ (manabu) - 学ぶ"),
            StoryOption("教", "おしえる", "おしえる (oshieru) - 教える"),
            StoryOption("書", "かく", "かく (kaku) - 書く"),
            StoryOption("読", "よむ", "よむ (yomu) - 読む")
        )
    ),
    // Scene 5: 家族との時間
    StoryScene(
        title = "家族との時間",
        story = "私にとって___は大切です。両親と___と一緒に暮らしています。週末は一緒に___を楽しみます。",
        emoji = "👨‍👩‍👧‍👦🏠❤️",
        targetWord = "家族・妹・食事",
        correctAnswer = "家",
        options = listOf(
            StoryOption("家", "いえ", "いえ (ie) - 家"),
            StoryOption("族", "ぞく", "ぞく (zoku) - 族"),
            StoryOption("親", "おや", "おや (oya) - 親"),
            StoryOption("庭", "にわ", "にわ (niwa) - 庭")
        )
    ),
    // Scene 6: 雨の日
    StoryScene(
        title = "雨の日",
        story = "今日は___が降っています。___を持って出かけました。___で靴が濡れないように注意しました。",
        emoji = "☔🌧️👢",
        targetWord = "雨・傘・道",
        correctAnswer = "雨",
        options = listOf(
            StoryOption("雨", "あめ", "あめ (ame) - 雨"),
            StoryOption("雪", "ゆき", "ゆき (yuki) - 雪"),
            StoryOption("風", "かぜ", "かぜ (kaze) - 風"),
            StoryOption("雲", "くも", "くも (kumo) - 雲")
        )
    ),
    // Scene 7: お買い物
    StoryScene(
        title = "お買い物",
        story = "近くの___で買い物をしました。新鮮な___を買いました。お___さんが親切に対応してくれました。",
        emoji = "🛒🥬🛍️",
        targetWord = "店・野菜・店員",
        correctAnswer = "店",
        options = listOf(
            StoryOption("店", "みせ", "みせ (mise) - 店"),
            StoryOption("市", "いち", "いち (ichi) - 市"),
            StoryOption("場", "ば", "ば (ba) - 場"),
            StoryOption("館", "かん", "かん (kan) - 館")
        )
    ),
    // Scene 8: 電車での移動
    StoryScene(
        title = "電車での移動",
        story = "今日は___で学校へ行きました。___が混んでいました。窓から___の景色が見えました。",
        emoji = "🚃🚆🌆",
        targetWord = "電車・車内・街",
        correctAnswer = "電",
        options = listOf(
            StoryOption("電", "でんしゃ", "でんしゃ (densha) - 電車"),
            StoryOption("汽", "きしゃ", "きしゃ (kisha) - 汽車"),
            StoryOption("馬", "うま", "うま (uma) - 馬"),
            StoryOption("船", "ふね", "ふね (fune) - 船")
        )
    ),
    // Scene 9: 友達との約束
    StoryScene(
        title = "友達との約束",
        story = "___と待ち合わせをしました。___の前で会うことにしました。___に行く予定です。",
        emoji = "🤝🎬🍔",
        targetWord = "友達・映画館・レストラン",
        correctAnswer = "友",
        options = listOf(
            StoryOption("友", "ともだち", "ともだち (tomodachi) - 友達"),
            StoryOption("同", "おなじ", "おなじ (onaji) - 同じ"),
            StoryOption("先", "せん", "せん (sen) - 先"),
            StoryOption("彼", "かれ", "かれ (kare) - 彼")
        )
    ),
    // Scene 10: 山登り
    StoryScene(
        title = "山登り",
        story = "週末に___へ登りました。___の頂上から見る景色は素晴らしかったです。___でリフレッシュできました。",
        emoji = "⛰️🏔️🌲",
        targetWord = "山・山頂・自然",
        correctAnswer = "山",
        options = listOf(
            StoryOption("山", "やま", "やま (yama) - 山"),
            StoryOption("丘", "おか", "おか (oka) - 丘"),
            StoryOption("谷", "たに", "たに (tani) - 谷"),
            StoryOption("川", "かわ", "かわ (kawa) - 川")
        )
    ),
    // Scene 11: 病院での出来事
    StoryScene(
        title = "病院での出来事",
        story = "___が痛かったので病院へ行きました。___に診てもらいました。お___さんが優しく対応してくれました。",
        emoji = "🏥💊👨‍⚕️",
        targetWord = "頭・医者・看護師",
        correctAnswer = "頭",
        options = listOf(
            StoryOption("頭", "あたま", "あたま (atama) - 頭"),
            StoryOption("手", "て", "て (te) - 手"),
            StoryOption("足", "あし", "あし (ashi) - 足"),
            StoryOption("目", "め", "め (me) - 目")
        )
    ),
    // Scene 12: お祭り
    StoryScene(
        title = "お祭り",
        story = "近所で___が開かれました。___を着た人がたくさんいました。___を見て楽しみました。",
        emoji = "🎆🎎🎪",
        targetWord = "お祭り・浴衣・花火",
        correctAnswer = "祭",
        options = listOf(
            StoryOption("祭", "まつり", "まつり (matsuri) - 祭り"),
            StoryOption("礼", "れい", "れい (rei) - 礼"),
            StoryOption("式", "しき", "しき (shiki) - 式"),
            StoryOption("式", "しき", "しき (shiki) - 式")
        )
    ),
    // Scene 13: 銀行での手続き
    StoryScene(
        title = "銀行での手続き",
        story = "___でお金をおろしました。___を記入しました。___の手続きが必要でした。",
        emoji = "🏦💴📝",
        targetWord = "銀行・用紙・身分証明",
        correctAnswer = "銀",
        options = listOf(
            StoryOption("銀", "ぎんこう", "ぎんこう (ginkou) - 銀行"),
            StoryOption("郵", "ゆうびん", "ゆうびん (yuubin) - 郵便"),
            StoryOption("保", "ほけん", "ほけん (hoken) - 保険"),
            StoryOption("券", "けん", "けん (ken) - 券")
        )
    ),
    // Scene 14: 海での休日
    StoryScene(
        title = "海での休日",
        story = "夏休みに___へ行きました。___で泳ぎました。___を拾ってお土産にしました。",
        emoji = "🏖️🏊‍♀️🐚",
        targetWord = "海・海・貝殻",
        correctAnswer = "海",
        options = listOf(
            StoryOption("海", "うみ", "うみ (umi) - 海"),
            StoryOption("池", "いけ", "いけ (ike) - 池"),
            StoryOption("湖", "みずうみ", "みずうみ (mizuumi) - 湖"),
            StoryOption("浜", "はま", "はま (hama) - 浜")
        )
    ),
    // Scene 15: 空港での旅立ち
    StoryScene(
        title = "空港での旅立ち",
        story = "海外旅行で___から出発しました。___を受けました。___へ向かいました。",
        emoji = "✈️🛄🗺️",
        targetWord = "空港・検査・外国",
        correctAnswer = "空",
        options = listOf(
            StoryOption("空", "くうこう", "くうこう (kuukou) - 空港"),
            StoryOption("港", "みなと", "みなと (minato) - 港"),
            StoryOption("駅", "えき", "えき (eki) - 駅"),
            StoryOption("停", "てい", "てい (tei) - 停")
        )
    ),
    // Scene 16: カフェでの午後
    StoryScene(
        title = "カフェでの午後",
        story = "___で友達と会いました。美味しい___を飲みながらおしゃべりしました。___の音楽が流れていました。",
        emoji = "☕🍰🎵",
        targetWord = "喫茶店・コーヒー・軽い",
        correctAnswer = "喫",
        options = listOf(
            StoryOption("喫", "きっさ", "きっさ (kissa) - 喫茶"),
            StoryOption("飲", "のみ", "のみ (nomi) - 飲み"),
            StoryOption("食", "たべ", "たべ (tabe) - 食べ"),
            StoryOption("館", "かん", "かん (kan) - 館")
        )
    ),
    // Scene 17: 図書館で勉強
    StoryScene(
        title = "図書館で勉強",
        story = "試験のために___へ行きました。___で静かに勉強しました。___を借りて帰りました。",
        emoji = "📚📖📝",
        targetWord = "図書館・机・本",
        correctAnswer = "図",
        options = listOf(
            StoryOption("図", "としょ", "としょ (tosho) - 図書"),
            StoryOption("書", "しょ", "しょ (sho) - 書"),
            StoryOption("室", "しつ", "しつ (shitsu) - 室"),
            StoryOption("店", "みせ", "みせ (mise) - 店")
        )
    ),
    // Scene 18: 温泉旅行
    StoryScene(
        title = "温泉旅行",
        story = "___へ旅行に行きました。お___に入ってリラックスしました。___がとても美味しかったです。",
        emoji = "♨️🧖‍♀️🍱",
        targetWord = "温泉・風呂・料理",
        correctAnswer = "温",
        options = listOf(
            StoryOption("温", "おんせん", "おんせん (onsen) - 温泉"),
            StoryOption("湯", "ゆ", "ゆ (yu) - 湯"),
            StoryOption("浴", "よく", "よく (yoku) - 浴"),
            StoryOption("泉", "いずみ", "いずみ (izumi) - 泉")
        )
    ),
    // Scene 19: 桜の季節
    StoryScene(
        title = "桜の季節",
        story = "___が満開になりました。___の下でお花見をしました。___がとても綺麗でした。",
        emoji = "🌸🌳🍡",
        targetWord = "桜・木・花",
        correctAnswer = "桜",
        options = listOf(
            StoryOption("桜", "さくら", "さくら (sakura) - 桜"),
            StoryOption("梅", "うめ", "うめ (ume) - 梅"),
            StoryOption("桃", "もも", "もも (momo) - 桃"),
            StoryOption("杏", "あんず", "あんず (anzu) - 杏")
        )
    ),
    // Scene 20: 運動会
    StoryScene(
        title = "運動会",
        story = "学校の___に参加しました。___で走って競争しました。___をかけて応援しました。",
        emoji = "🏃‍♂️🏅🎌",
        targetWord = "運動会・競技・声",
        correctAnswer = "運",
        options = listOf(
            StoryOption("運", "うんどう", "うんどう (undou) - 運動"),
            StoryOption("動", "どう", "どう (dou) - 動"),
            StoryOption("試", "し", "し (shi) - 試"),
            StoryOption("技", "ぎ", "ぎ (gi) - 技")
        )
    ),
    // Scene 21: お弁当作り
    StoryScene(
        title = "お弁当作り",
        story = "朝早く起きて___を作りました。___を詰めました。___も入れました。",
        emoji = "🍱🍙🥢",
        targetWord = "お弁当・おにぎり・卵",
        correctAnswer = "弁",
        options = listOf(
            StoryOption("弁", "べんとう", "べんとう (bentou) - 弁当"),
            StoryOption("飯", "めし", "めし (meshi) - 飯"),
            StoryOption("食", "しょく", "しょく (shoku) - 食"),
            StoryOption("夕", "ゆう", "ゆう (yuu) - 夕")
        )
    ),
    // Scene 22: 花火大会
    StoryScene(
        title = "花火大会",
        story = "夏の___へ行きました。夜空に___が打ち上がりました。___を着て楽しみました。",
        emoji = "🎆👘🍧",
        targetWord = "花火大会・花火・浴衣",
        correctAnswer = "花",
        options = listOf(
            StoryOption("花", "はなび", "はなび (hanabi) - 花火"),
            StoryOption("夏", "なつ", "なつ (natsu) - 夏"),
            StoryOption("祭", "まつり", "まつり (matsuri) - 祭"),
            StoryOption("光", "ひかり", "ひかり (hikari) - 光")
        )
    ),
    // Scene 23: 雪遊び
    StoryScene(
        title = "雪遊び",
        story = "冬になって___が降りました。___を作って遊びました。___を滑って楽しみました。",
        emoji = "⛄❄️🛷",
        targetWord = "雪・雪だるま・そり",
        correctAnswer = "雪",
        options = listOf(
            StoryOption("雪", "ゆき", "ゆき (yuki) - 雪"),
            StoryOption("氷", "こおり", "こおり (koori) - 氷"),
            StoryOption("霜", "しも", "しも (shimo) - 霜"),
            StoryOption("雹", "ひょう", "ひょう (hyou) - 雹")
        )
    ),
    // Scene 24: お風呂上り
    StoryScene(
        title = "お風呂上り",
        story = "___に入ってすっきりしました。___を飲みました。___でくつろぎました。",
        emoji = "🛁🥛📺",
        targetWord = "お風呂・牛乳・ソファ",
        correctAnswer = "風",
        options = listOf(
            StoryOption("風", "ふろ", "ふろ (furo) - 風呂"),
            StoryOption("湯", "ゆ", "ゆ (yu) - 湯"),
            StoryOption("泉", "いずみ", "いずみ (izumi) - 泉"),
            StoryOption("水", "みず", "みず (mizu) - 水")
        )
    ),
    // Scene 25: 散歩道
    StoryScene(
        title = "散歩道",
        story = "夕方に___をしました。___の周りを歩きました。___が綺麗に咲いていました。",
        emoji = "🚶‍♀️🌊🌻",
        targetWord = "散歩・池・花",
        correctAnswer = "散",
        options = listOf(
            StoryOption("散", "さんぽ", "さんぽ (sanpo) - 散歩"),
            StoryOption("歩", "ほ", "ほ (ho) - 歩"),
            StoryOption("道", "みち", "みち (michi) - 道"),
            StoryOption("走", "はし", "はし (hashi) - 走")
        )
    ),
    // Scene 26: 仕事の一日
    StoryScene(
        title = "仕事の一日",
        story = "___で働いています。___で資料を作りました。___と会議をしました。",
        emoji = "💼🖥️📊",
        targetWord = "会社・パソコン・上司",
        correctAnswer = "会",
        options = listOf(
            StoryOption("会", "かいしゃ", "かいしゃ (kaisha) - 会社"),
            StoryOption("社", "しゃ", "しゃ (sha) - 社"),
            StoryOption("室", "しつ", "しつ (shitsu) - 室"),
            StoryOption("店", "てん", "てん (ten) - 店")
        )
    ),
    // Scene 27: 週末の掃除
    StoryScene(
        title = "週末の掃除",
        story = "___になったので掃除をしました。___を使って掃きました。___を拭きました。",
        emoji = "🧹🧽✨",
        targetWord = "土曜日・箒・窓",
        correctAnswer = "土",
        options = listOf(
            StoryOption("土", "どよう", "どよう (doyou) - 土曜"),
            StoryOption("日", "にち", "にち (nichi) - 日"),
            StoryOption("月", "げつ", "げつ (getsu) - 月"),
            StoryOption("火", "か", "か (ka) - 火")
        )
    ),
    // Scene 28: 郵便局へ
    StoryScene(
        title = "郵便局へ",
        story = "___に荷物を出しに行きました。___を書きました。___を貼りました。",
        emoji = "📮📦✉️",
        targetWord = "郵便局・住所・切手",
        correctAnswer = "郵",
        options = listOf(
            StoryOption("郵", "ゆうびん", "ゆうびん (yuubin) - 郵便"),
            StoryOption("便", "びん", "びん (bin) - 便"),
            StoryOption("送", "そう", "そう (sou) - 送"),
            StoryOption("局", "きょく", "きょく (kyoku) - 局")
        )
    ),
    // Scene 29: 動物園
    StoryScene(
        title = "動物園",
        story = "___へ行きました。大きな___を見ました。___が可愛かったです。",
        emoji = "🦁🐼🐧",
        targetWord = "動物園・象・ペンギン",
        correctAnswer = "動",
        options = listOf(
            StoryOption("動", "どうぶつ", "どうぶつ (doubutsu) - 動物"),
            StoryOption("物", "もの", "もの (mono) - 物"),
            StoryOption("獣", "けもの", "けもの (kemono) - 獣"),
            StoryOption("鳥", "とり", "とり (tori) - 鳥")
        )
    ),
    // Scene 30: 誕生日パーティー
    StoryScene(
        title = "誕生日パーティー",
        story = "友達の___を祝いました。___を贈りました。___を食べてお祝いしました。",
        emoji = "🎂🎁🎉",
        targetWord = "誕生日・プレゼント・ケーキ",
        correctAnswer = "誕",
        options = listOf(
            StoryOption("誕", "たんじょう", "たんじょう (tanjou) - 誕生"),
            StoryOption("生", "せい", "せい (sei) - 生"),
            StoryOption("祝", "いわい", "いわい (iwai) - 祝い"),
            StoryOption("祭", "まつり", "まつり (matsuri) - 祭")
        )
    ),
    // Scene 31: 寝る前の時間
    StoryScene(
        title = "寝る前の時間",
        story = "___を着替えました。___を磨きました。___を読んで寝ました。",
        emoji = "🌙🦷📖",
        targetWord = "パジャマ・歯・本",
        correctAnswer = "歯",
        options = listOf(
            StoryOption("歯", "は", "は (ha) - 歯"),
            StoryOption("髪", "かみ", "かみ (kami) - 髪"),
            StoryOption("肌", "はだ", "はだ (hada) - 肌"),
            StoryOption("目", "め", "め (me) - 目")
        )
    ),
    // Scene 32: 朝ごはん
    StoryScene(
        title = "朝ごはん",
        story = "___に目が覚めました。___を食べました。___を飲みました。",
        emoji = "🍚🥢🍵",
        targetWord = "朝・ご飯・味噌汁",
        correctAnswer = "朝",
        options = listOf(
            StoryOption("朝", "あさ", "あさ (asa) - 朝"),
            StoryOption("昼", "ひる", "ひる (hiru) - 昼"),
            StoryOption("夕", "ゆう", "ゆう (yuu) - 夕"),
            StoryOption("晩", "ばん", "ばん (ban) - 晩")
        )
    ),
    // Scene 33: 学校の行事
    StoryScene(
        title = "学校の行事",
        story = "___でコンサートがありました。___を演奏しました。___が素敵でした。",
        emoji = "🎼🎹🎸",
        targetWord = "体育館・ピアノ・音楽",
        correctAnswer = "体",
        options = listOf(
            StoryOption("体", "たいいく", "たいいく (taiiku) - 体育"),
            StoryOption("育", "いく", "いく (iku) - 育"),
            StoryOption("館", "かん", "かん (kan) - 館"),
            StoryOption("室", "しつ", "しつ (shitsu) - 室")
        )
    ),
    // Scene 34: 秋の紅葉
    StoryScene(
        title = "秋の紅葉",
        story = "___を見に行きました。___が赤く色づいていました。___を写真に撮りました。",
        emoji = "🍁🍂📸",
        targetWord = "紅葉・葉・景色",
        correctAnswer = "紅",
        options = listOf(
            StoryOption("紅", "こうよう", "こうよう (kouyou) - 紅葉"),
            StoryOption("葉", "は", "は (ha) - 葉"),
            StoryOption("秋", "あき", "あき (aki) - 秋"),
            StoryOption("色", "いろ", "いろ (iro) - 色")
        )
    ),
    // Scene 35: ペットの世話
    StoryScene(
        title = "ペットの世話",
        story = "家の___を散歩に連れて行きました。___をあげました。___をしてあげました。",
        emoji = "🐕🦴🧼",
        targetWord = "犬・餌・洗濯",
        correctAnswer = "犬",
        options = listOf(
            StoryOption("犬", "いぬ", "いぬ (inu) - 犬"),
            StoryOption("猫", "ねこ", "ねこ (neko) - 猫"),
            StoryOption("鳥", "とり", "とり (tori) - 鳥"),
            StoryOption("魚", "さかな", "さかな (sakana) - 魚")
        )
    ),
    // Scene 36: 初詣
    StoryScene(
        title = "初詣",
        story = "お正月に___へ行きました。___をお願いしました。___を食べました。",
        emoji = "⛩️🙏🍡",
        targetWord = "神社・願い・お餅",
        correctAnswer = "神",
        options = listOf(
            StoryOption("神", "じんじゃ", "じんじゃ (jinjia) - 神社"),
            StoryOption("社", "しゃ", "しゃ (sha) - 社"),
            StoryOption("寺", "てら", "てら (tera) - 寺"),
            StoryOption("堂", "どう", "どう (dou) - 堂")
        )
    ),
    // Scene 37: 料理教室
    StoryScene(
        title = "料理教室",
        story = "___に通っています。___を使って料理します。___の作り方を学びました。",
        emoji = "👩‍🍳🔪🍳",
        targetWord = "料理教室・包丁・寿司",
        correctAnswer = "料",
        options = listOf(
            StoryOption("料", "りょうり", "りょうり (ryouri) - 料理"),
            StoryOption("理", "り", "り (ri) - 理"),
            StoryOption("教", "きょう", "きょう (kyou) - 教"),
            StoryOption("習", "しゅう", "しゅう (shuu) - 習")
        )
    ),
    // Scene 38: ホームパーティー
    StoryScene(
        title = "ホームパーティー",
        story = "家で___を開きました。___を作って招待しました。___で盛り上がりました。",
        emoji = "🏠🍕🎮",
        targetWord = "パーティー・料理・ゲーム",
        correctAnswer = "料",
        options = listOf(
            StoryOption("料", "りょうり", "りょうり (ryouri) - 料理"),
            StoryOption("食", "しょく", "しょく (shoku) - 食"),
            StoryOption("飲", "のみ", "のみ (nomi) - 飲み"),
            StoryOption("遊", "あそ", "あそ (aso) - 遊び")
        )
    ),
    // Scene 39: 電話の会話
    StoryScene(
        title = "電話の会話",
        story = "___が鳴りました。___を取りました。___について話しました。",
        emoji = "📞💬📋",
        targetWord = "電話・受話器・仕事",
        correctAnswer = "電",
        options = listOf(
            StoryOption("電", "でんわ", "でんわ (denwa) - 電話"),
            StoryOption("話", "はなし", "はなし (hanashi) - 話"),
            StoryOption("言", "い", "い (i) - 言"),
            StoryOption("語", "ご", "ご (go) - 語")
        )
    ),
    // Scene 40: 庭仕事
    StoryScene(
        title = "庭仕事",
        story = "___で花を植えました。___を使って土を掘りました。___に水をやりました。",
        emoji = "🌻🌱💦",
        targetWord = "庭・シャベル・植物",
        correctAnswer = "庭",
        options = listOf(
            StoryOption("庭", "にわ", "にわ (niwa) - 庭"),
            StoryOption("園", "えん", "えん (en) - 園"),
            StoryOption("畑", "はたけ", "はたけ (hatake) - 畑"),
            StoryOption("野", "の", "の (no) - 野")
        )
    ),
    // Scene 41: 野球観戦
    StoryScene(
        title = "野球観戦",
        story = "___で野球を見ました。___を応援しました。___がヒットを打ちました。",
        emoji = "⚾🥜📣",
        targetWord = "球場・チーム・選手",
        correctAnswer = "球",
        options = listOf(
            StoryOption("球", "きゅうじょう", "きゅうじょう (kyuujou) - 球場"),
            StoryOption("場", "じょう", "じょう (jou) - 場"),
            StoryOption("野", "や", "や (ya) - 野"),
            StoryOption("村", "むら", "むら (mura) - 村")
        )
    ),
    // Scene 42: 引っ越しの日
    StoryScene(
        title = "引っ越しの日",
        story = "___に引っ越しました。___を運びました。___に荷物を置きました。",
        emoji = "🚚📦🏠",
        targetWord = "新居・ダンボール・部屋",
        correctAnswer = "居",
        options = listOf(
            StoryOption("居", "きょ", "きょ (kyo) - 居"),
            StoryOption("新", "しん", "しん (shin) - 新"),
            StoryOption("住", "じゅう", "じゅう (juu) - 住"),
            StoryOption("宅", "たく", "たく (taku) - 宅")
        )
    ),
    // Scene 43: 夏休みの計画
    StoryScene(
        title = "夏休みの計画",
        story = "___を立てています。___へ行きたいです。___で泳ぎたいです。",
        emoji = "✈️🏝️🌊",
        targetWord = "計画・沖縄・海",
        correctAnswer = "計",
        options = listOf(
            StoryOption("計", "けいかく", "けいかく (keikaku) - 計画"),
            StoryOption("画", "かく", "かく (kaku) - 画"),
            StoryOption("予", "よ", "よ (yo) - 予"),
            StoryOption("定", "てい", "てい (tei) - 定")
        )
    ),
    // Scene 44: お月見
    StoryScene(
        title = "お月見",
        story = "秋の___をしました。___が綺麗に見えました。___を食べました。",
        emoji = "🌕🐇🍡",
        targetWord = "お月見・満月・団子",
        correctAnswer = "月",
        options = listOf(
            StoryOption("月", "つき", "つき (tsuki) - 月"),
            StoryOption("秋", "あき", "あき (aki) - 秋"),
            StoryOption("満", "まん", "まん (man) - 満"),
            StoryOption("夜", "よる", "よる (yoru) - 夜")
        )
    ),
    // Scene 45: 買い物モール
    StoryScene(
        title = "買い物モール",
        story = "___へ買い物に行きました。___を試着しました。___で休憩しました。",
        emoji = "🛍️👗☕",
        targetWord = "ショッピングモール・服・カフェ",
        correctAnswer = "服",
        options = listOf(
            StoryOption("服", "ふく", "ふく (fuku) - 服"),
            StoryOption("衣", "い", "い (i) - 衣"),
            StoryOption("装", "そう", "そう (sou) - 装"),
            StoryOption("洋", "よう", "よう (you) - 洋")
        )
    ),
    // Scene 46: 映画鑑賞
    StoryScene(
        title = "映画鑑賞",
        story = "___で映画を見ました。___が迫力ありました。___に感動しました。",
        emoji = "🎬🍿🎭",
        targetWord = "映画館・映像・物語",
        correctAnswer = "映",
        options = listOf(
            StoryOption("映", "えいが", "えいが (eiga) - 映画"),
            StoryOption("画", "が", "が (ga) - 画"),
            StoryOption("演", "えん", "えん (en) - 演"),
            StoryOption("劇", "げき", "げき (geki) - 劇")
        )
    ),
    // Scene 47: 音楽の練習
    StoryScene(
        title = "音楽の練習",
        story = "___の練習をしました。___を弾きました。___を歌いました。",
        emoji = "🎸🎵🎤",
        targetWord = "ギター・楽譜・歌",
        correctAnswer = "楽",
        options = listOf(
            StoryOption("楽", "がくふ", "がくふ (gakufu) - 楽譜"),
            StoryOption("譜", "ふ", "ふ (fu) - 譜"),
            StoryOption("曲", "きょく", "きょく (kyoku) - 曲"),
            StoryOption("音", "おん", "おん (on) - 音")
        )
    ),
    // Scene 48: 手紙を書く
    StoryScene(
        title = "手紙を書く",
        story = "___に手紙を書きました。___を使って書きました。___を封筒に入れました。",
        emoji = "✉️🖊️📮",
        targetWord = "友人・ペン・便箋",
        correctAnswer = "友",
        options = listOf(
            StoryOption("友", "ゆうじん", "ゆうじん (yuujin) - 友人"),
            StoryOption("人", "じん", "じん (jin) - 人"),
            StoryOption("紙", "かみ", "かみ (kami) - 紙"),
            StoryOption("墨", "すみ", "すみ (sumi) - 墨")
        )
    ),
    // Scene 49: 天気予報
    StoryScene(
        title = "天気予報",
        story = "___を見ました。明日は___だそうです。___を持って出かけます。",
        emoji = "📺☀️👕",
        targetWord = "テレビ・晴れ・帽子",
        correctAnswer = "晴",
        options = listOf(
            StoryOption("晴", "はれ", "はれ (hare) - 晴れ"),
            StoryOption("曇", "くもり", "くもり (kumori) - 曇り"),
            StoryOption("雨", "あめ", "あめ (ame) - 雨"),
            StoryOption("霧", "きり", "きり (kiri) - 霧")
        )
    ),
    // Scene 50: 博物館
    StoryScene(
        title = "博物館",
        story = "___へ見学に行きました。___を見て回りました。___について学びました。",
        emoji = "🏛️🦖🏺",
        targetWord = "博物館・展示品・歴史",
        correctAnswer = "博",
        options = listOf(
            StoryOption("博", "はくぶつ", "はくぶつ (hakubutsu) - 博物"),
            StoryOption("物", "ぶつ", "ぶつ (butsu) - 物"),
            StoryOption("館", "かん", "かん (kan) - 館"),
            StoryOption("図", "ず", "ず (zu) - 図")
        )
    ),
    // Scene 51: 忘年会
    StoryScene(
        title = "忘年会",
        story = "会社の___に参加しました。___で乾杯しました。___を食べました。",
        emoji = "🍻🍶🍗",
        targetWord = "忘年会・ビール・料理",
        correctAnswer = "忘",
        options = listOf(
            StoryOption("忘", "ぼうねん", "ぼうねん (bounen) - 忘年"),
            StoryOption("年", "ねん", "ねん (nen) - 年"),
            StoryOption("新", "しん", "しん (shin) - 新"),
            StoryOption("旧", "きゅう", "きゅう (kyuu) - 旧")
        )
    ),
    // Scene 52: 花見の準備
    StoryScene(
        title = "花見の準備",
        story = "___の準備をしました。___を広げました。___を持って行きました。",
        emoji = "🌸🧺🍱",
        targetWord = "お花見・レジャーシート・弁当",
        correctAnswer = "花",
        options = listOf(
            StoryOption("花", "はなみ", "はなみ (hanami) - 花見"),
            StoryOption("見", "み", "み (mi) - 見"),
            StoryOption("桜", "さくら", "さくら (sakura) - 桜"),
            StoryOption("梅", "うめ", "うめ (ume) - 梅")
        )
    ),
    // Scene 53: 防災訓練
    StoryScene(
        title = "防災訓練",
        story = "学校で___がありました。___の仕方を練習しました。___の場所を確認しました。",
        emoji = "🚨🧯🚪",
        targetWord = "避難訓練・消火器・出口",
        correctAnswer = "避",
        options = listOf(
            StoryOption("避", "ひなん", "ひなん (hinan) - 避難"),
            StoryOption("難", "なん", "なん (nan) - 難"),
            StoryOption("訓", "くん", "くん (kun) - 訓"),
            StoryOption("練", "れん", "れん (ren) - 練")
        )
    ),
    // Scene 54: 居酒屋
    StoryScene(
        title = "居酒屋",
        story = "仕事の後に___に行きました。___を注文しました。___で話しました。",
        emoji = "🏮🍺🍢",
        targetWord = "居酒屋・お刺身・上司",
        correctAnswer = "居",
        options = listOf(
            StoryOption("居", "いざかや", "いざかや (izakaya) - 居酒屋"),
            StoryOption("酒", "さけ", "さけ (sake) - 酒"),
            StoryOption("屋", "や", "や (ya) - 屋"),
            StoryOption("店", "みせ", "みせ (mise) - 店")
        )
    ),
    // Scene 55: 卒業式
    StoryScene(
        title = "卒業式",
        story = "___が行われました。___をもらいました。___と別れを告げました。",
        emoji = "🎓📜👋",
        targetWord = "卒業式・卒業証書・友達",
        correctAnswer = "卒",
        options = listOf(
            StoryOption("卒", "そつぎょう", "そつぎょう (sotsugyou) - 卒業"),
            StoryOption("業", "ぎょう", "ぎょう (gyou) - 業"),
            StoryOption("式", "しき", "しき (shiki) - 式"),
            StoryOption("入", "にゅう", "にゅう (nyuu) - 入")
        )
    ),
    // Scene 56: 梅雨の季節
    StoryScene(
        title = "梅雨の季節",
        story = "___が始まりました。___が降り続いています。___が生えてきました。",
        emoji = "☔🐌🌿",
        targetWord = "梅雨・雨・雑草",
        correctAnswer = "梅",
        options = listOf(
            StoryOption("梅", "つゆ", "つゆ (tsuyu) - 梅雨"),
            StoryOption("雨", "あめ", "あめ (ame) - 雨"),
            StoryOption("夏", "なつ", "なつ (natsu) - 夏"),
            StoryOption("湿", "しっ", "しっ (shi) - 湿")
        )
    ),
    // Scene 57: 自動車教習所
    StoryScene(
        title = "自動車教習所",
        story = "___に通っています。___の運転を練習しました。___で確認テストを受けました。",
        emoji = "🚗🚦📝",
        targetWord = "教習所・車・学科",
        correctAnswer = "教",
        options = listOf(
            StoryOption("教", "きょうしゅう", "きょうしゅう (kyoushuu) - 教習"),
            StoryOption("習", "しゅう", "しゅう (shuu) - 習"),
            StoryOption("所", "しょ", "しょ (sho) - 所"),
            StoryOption("場", "じょう", "じょう (jou) - 場")
        )
    ),
    // Scene 58: クリスマス
    StoryScene(
        title = "クリスマス",
        story = "___を飾りました。___を交換しました。___を食べました。",
        emoji = "🎄🎁🍗",
        targetWord = "クリスマスツリー・プレゼント・チキン",
        correctAnswer = "木",
        options = listOf(
            StoryOption("木", "き", "き (ki) - 木"),
            StoryOption("樹", "じゅ", "じゅ (ju) - 樹"),
            StoryOption("松", "まつ", "まつ (matsu) - 松"),
            StoryOption("杉", "すぎ", "すぎ (sugi) - 杉")
        )
    ),
    // Scene 59: 大相撲
    StoryScene(
        title = "大相撲",
        story = "___を見に行きました。大きな___が戦いました。___を観客が沸きました。",
        emoji = "🥁🤼‍♂️🏆",
        targetWord = "大相撲・力士・土俵",
        correctAnswer = "相",
        options = listOf(
            StoryOption("相", "おおずもう", "おおずもう (oozumou) - 大相撲"),
            StoryOption("撲", "ずもう", "ずもう (zumou) - 相撲"),
            StoryOption("武", "ぶ", "ぶ (bu) - 武"),
            StoryOption("道", "どう", "どう (dou) - 道")
        )
    ),
    // Scene 60: お彼岸
    StoryScene(
        title = "お彼岸",
        story = "___にお墓参りに行きました。___を供えました。___をしばきました。",
        emoji = "🙏🌸🍡",
        targetWord = "お彼岸・お花・お団子",
        correctAnswer = "彼岸",
        options = listOf(
            StoryOption("彼", "ひがん", "ひがん (higan) - 彼岸"),
            StoryOption("岸", "がん", "がん (gan) - 岸"),
            StoryOption("墓", "はか", "はか (haka) - 墓"),
            StoryOption("参", "まい", "まい (mai) - 参")
        )
    ),
    // Scene 61: 七五三
    StoryScene(
        title = "七五三",
        story = "___のお祝いをしました。___を着ました。___で写真を撮りました。",
        emoji = "👘📸🍬",
        targetWord = "七五三・着物・神社",
        correctAnswer = "七",
        options = listOf(
            StoryOption("七", "しちごさん", "しちごさん (shichigosan) - 七五三"),
            StoryOption("五", "ご", "ご (go) - 五"),
            StoryOption("三", "さん", "さん (san) - 三"),
            StoryOption("衣", "い", "い (i) - 衣")
        )
    ),
    // Scene 62: 茶道の体験
    StoryScene(
        title = "茶道の体験",
        story = "___を体験しました。___を使ってお茶を立てました。___をいただきました。",
        emoji = "🍵🥄🍪",
        targetWord = "茶道・茶筅・和菓子",
        correctAnswer = "茶",
        options = listOf(
            StoryOption("茶", "さどう", "さどう (sadou) - 茶道"),
            StoryOption("道", "どう", "どう (dou) - 道"),
            StoryOption("湯", "ゆ", "ゆ (yu) - 湯"),
            StoryOption("葉", "は", "は (ha) - 葉")
        )
    ),
    // Scene 63: 盆踊り
    StoryScene(
        title = "盆踊り",
        story = "夏の___に参加しました。___を囲んで踊りました。___を着て踊りました。",
        emoji = "👘💃🎐",
        targetWord = "お盆・盆踊り・浴衣",
        correctAnswer = "盆",
        options = listOf(
            StoryOption("盆", "ぼん", "ぼん (bon) - 盆"),
            StoryOption("踊", "おどり", "おどり (odori) - 踊り"),
            StoryOption("輪", "わ", "わ (wa) - 輪"),
            StoryOption("魂", "たましい", "たましい (tamashii) - 魂")
        )
    ),
    // Scene 64: 初日の出
    StoryScene(
        title = "初日の出",
        story = "___を見に行きました。海から___が昇りました。___をお祈りしました。",
        emoji = "🌅🌊🙏",
        targetWord = "初日の出・太陽・新年",
        correctAnswer = "初",
        options = listOf(
            StoryOption("初", "はつひので", "はつひので (hatsuhinode) - 初日の出"),
            StoryOption("日", "ひ", "ひ (hi) - 日"),
            StoryOption("出", "で", "で (de) - 出"),
            StoryOption("朝", "あさ", "あさ (asa) - 朝")
        )
    ),
    // Scene 65: 節分
    StoryScene(
        title = "節分",
        story = "___の日に豆まきをしました。___を唱えました。___を食べました。",
        emoji = "👹🫘🍣",
        targetWord = "節分・鬼は外・恵方巻",
        correctAnswer = "節",
        options = listOf(
            StoryOption("節", "せつぶん", "せつぶん (setsu-bun) - 節分"),
            StoryOption("分", "ぶん", "ぶん (bun) - 分"),
            StoryOption("豆", "まめ", "まめ (mame) - 豆"),
            StoryOption("鬼", "おに", "おに (oni) - 鬼")
        )
    ),
    // Scene 66: 鯉のぼり
    StoryScene(
        title = "鯉のぼり",
        story = "こどもの日に___を飾りました。大きな___が泳いでいました。___を立てました。",
        emoji = "🎏🌸⚔️",
        targetWord = "鯉のぼり・兜・武者人形",
        correctAnswer = "鯉",
        options = listOf(
            StoryOption("鯉", "こい", "こい (koi) - 鯉"),
            StoryOption("旗", "はた", "はた (hata) - 旗"),
            StoryOption("魚", "さかな", "さかな (sakana) - 魚"),
            StoryOption("波", "なみ", "なみ (nami) - 波")
        )
    ),
    // Scene 67: 夏祭りの露店
    StoryScene(
        title = "夏祭りの露店",
        story = "お祭りの___を見ました。___が並んでいました。___を食べました。",
        emoji = "🎪🍎🍧",
        targetWord = "露店・りんご飴・かき氷",
        correctAnswer = "露",
        options = listOf(
            StoryOption("露", "ろてん", "ろてん (roten) - 露店"),
            StoryOption("店", "みせ", "みせ (mise) - 店"),
            StoryOption("屋", "や", "や (ya) - 屋"),
            StoryOption("席", "せき", "せき (seki) - 席")
        )
    ),
    // Scene 68: 書道教室
    StoryScene(
        title = "書道教室",
        story = "___に習いに行きました。___を使って書きました。___を書きました。",
        emoji = "🖌️📜✨",
        targetWord = "書道・筆・漢字",
        correctAnswer = "書",
        options = listOf(
            StoryOption("書", "しょどう", "しょどう (shodou) - 書道"),
            StoryOption("道", "どう", "どう (dou) - 道"),
            StoryOption("墨", "すみ", "すみ (sumi) - 墨"),
            StoryOption("硯", "すずり", "すずり (suzuri) - 硯")
        )
    ),
    // Scene 69: 回転寿司
    StoryScene(
        title = "回転寿司",
        story = "___へ行きました。___が流れてきました。___を注文しました。",
        emoji = "🍣🔄🥢",
        targetWord = "回転寿司・寿司・マグロ",
        correctAnswer = "回",
        options = listOf(
            StoryOption("回", "かいてん", "かいてん (kaiten) - 回転"),
            StoryOption("転", "てん", "てん (ten) - 転"),
            StoryOption("寿", "すし", "すし (sushi) - 寿司"),
            StoryOption("司", "つかさ", "つかさ (tsukasa) - 司")
        )
    ),
    // Scene 70: 元旦
    StoryScene(
        title = "元旦",
        story = "___を迎えました。___に初詣をしました。___を飲みました。",
        emoji = "🎌⛩️🍶",
        targetWord = "新年・神社・お屠蘇",
        correctAnswer = "元",
        options = listOf(
            StoryOption("元", "がんたん", "がんたん (gantan) - 元旦"),
            StoryOption("旦", "たん", "たん (tan) - 旦"),
            StoryOption("新", "しん", "しん (shin) - 新"),
            StoryOption("旧", "きゅう", "きゅう (kyuu) - 旧")
        )
    ),
    // Scene 71: 午後のティータイム
    StoryScene(
        title = "午後のティータイム",
        story = "___に紅茶を飲みました。___を食べました。___を読みました。",
        emoji = "☕🍪📰",
        targetWord = "午後・クッキー・新聞",
        correctAnswer = "午",
        options = listOf(
            StoryOption("午", "ごご", "ごご (gogo) - 午後"),
            StoryOption("後", "ご", "ご (go) - 後"),
            StoryOption("昼", "ひる", "ひる (hiru) - 昼"),
            StoryOption("夕", "ゆう", "ゆう (yuu) - 夕")
        )
    ),
    // Scene 72: 駄菓子屋
    StoryScene(
        title = "駄菓子屋",
        story = "子供の頃の___を訪ねました。___を買いました。___を思い出しました。",
        emoji = "🍭🍬🧒",
        targetWord = "駄菓子屋・菓子・思い出",
        correctAnswer = "菓",
        options = listOf(
            StoryOption("菓", "だがし", "だがし (dagashi) - 駄菓子"),
            StoryOption("子", "し", "し (shi) - 子"),
            StoryOption("屋", "や", "や (ya) - 屋"),
            StoryOption("店", "みせ", "みせ (mise) - 店")
        )
    ),
    // Scene 73: 風呂敷
    StoryScene(
        title = "風呂敷",
        story = "___で包みを作りました。___を持って行きました。___に入れました。",
        emoji = "🎁👜🍱",
        targetWord = "風呂敷・お弁当・包み",
        correctAnswer = "風",
        options = listOf(
            StoryOption("風", "ふろしき", "ふろしき (furoshiki) - 風呂敷"),
            StoryOption("敷", "しき", "しき (shiki) - 敷"),
            StoryOption("包", "つつみ", "つつみ (tsutsumi) - 包み"),
            StoryOption("布", "ぬの", "ぬの (nuno) - 布")
        )
    ),
    // Scene 74: 華道
    StoryScene(
        title = "華道",
        story = "___の稽古に行きました。___を活けました。___を学びました。",
        emoji = "🌸🎋🪷",
        targetWord = "華道・花・生け花",
        correctAnswer = "華",
        options = listOf(
            StoryOption("華", "かどう", "かどう (kadou) - 華道"),
            StoryOption("道", "どう", "どう (dou) - 道"),
            StoryOption("花", "はな", "はな (hana) - 花"),
            StoryOption("活", "いけ", "いけ (ike) - 活け")
        )
    ),
    // Scene 75: 寿司職人
    StoryScene(
        title = "寿司職人",
        story = "___の仕事を見学しました。___を握りました。___を学びました。",
        emoji = "🍣👨‍🍳🔪",
        targetWord = "寿司職人・寿司・技術",
        correctAnswer = "寿",
        options = listOf(
            StoryOption("寿", "すし", "すし (sushi) - 寿司"),
            StoryOption("司", "つかさ", "つかさ (tsukasa) - 司"),
            StoryOption("職", "しょく", "しょく (shoku) - 職"),
            StoryOption("人", "にん", "にん (nin) - 人")
        )
    ),
    // Scene 76: 絵馬
    StoryScene(
        title = "絵馬",
        story = "神社で___を書きました。___をお願いしました。___に吊るしました。",
        emoji = "⛩️🙏🎋",
        targetWord = "絵馬・合格祈願・絵",
        correctAnswer = "絵",
        options = listOf(
            StoryOption("絵", "えま", "えま (ema) - 絵馬"),
            StoryOption("馬", "うま", "うま (uma) - 馬"),
            StoryOption("願", "ねがい", "ねがい (negai) - 願い"),
            StoryOption("図", "ず", "ず (zu) - 図")
        )
    ),
    // Scene 77: 通夜
    StoryScene(
        title = "通夜",
        story = "___に参列しました。___を唱えました。___を焼きました。",
        emoji = "🕯️🙏🕊️",
        targetWord = "通夜・お経・線香",
        correctAnswer = "通",
        options = listOf(
            StoryOption("通", "つや", "つや (tsuya) - 通夜"),
            StoryOption("夜", "や", "や (ya) - 夜"),
            StoryOption("葬", "そう", "そう (sou) - 葬"),
            StoryOption("式", "しき", "しき (shiki) - 式")
        )
    ),
    // Scene 78: 通販
    StoryScene(
        title = "通販",
        story = "___で買い物をしました。___で届きました。___を開けました。",
        emoji = "📦🚚📱",
        targetWord = "通販・宅配便・箱",
        correctAnswer = "販",
        options = listOf(
            StoryOption("販", "つうはん", "つうはん (tsuuhan) - 通販"),
            StoryOption("通", "つう", "つう (tsuu) - 通"),
            StoryOption("売", "うり", "うり (uri) - 売"),
            StoryOption("買", "かい", "かい (kai) - 買")
        )
    ),
    // Scene 79: 団子
    StoryScene(
        title = "団子",
        story = "___を食べました。___を串に刺しました。___を買いました。",
        emoji = "🍡🍵🌸",
        targetWord = "団子・三色・みたらし",
        correctAnswer = "団",
        options = listOf(
            StoryOption("団", "だんご", "だんご (dango) - 団子"),
            StoryOption("子", "こ", "こ (ko) - 子"),
            StoryOption("球", "きゅう", "きゅう (kyuu) - 球"),
            StoryOption("菓", "か", "か (ka) - 菓")
        )
    ),
    // Scene 80: 稲荷寿司
    StoryScene(
        title = "稲荷寿司",
        story = "___を作りました。___で包みました。___を食べました。",
        emoji = "🍣🦊🍚",
        targetWord = "稲荷寿司・油揚げ・酢飯",
        correctAnswer = "稲",
        options = listOf(
            StoryOption("稲", "いなり", "いなり (inari) - 稲荷"),
            StoryOption("荷", "に", "に (ni) - 荷"),
            StoryOption("狐", "きつね", "きつね (kitsune) - 狐"),
            StoryOption("油", "あぶら", "あぶら (abura) - 油")
        )
    )
)
