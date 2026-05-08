package com.cutekana.ui.screens.learn

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.model.JlptLevel
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.LearnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JLPTStudyScreen(
    level: JlptLevel,
    onBack: () -> Unit,
    viewModel: LearnViewModel = hiltViewModel()
) {
    var selectedSection by remember { mutableStateOf<JLPTStudySection?>(null) }
    
    val title = when (level) {
        JlptLevel.N5 -> "JLPT N5 Study"
        JlptLevel.N4 -> "JLPT N4 Study"
        else -> "JLPT Study"
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val currentSection = selectedSection
            when (currentSection) {
                null -> JLPTStudyMenu(
                    level = level,
                    onSelectSection = { selectedSection = it }
                )
                else -> JLPTStudyContent(
                    section = currentSection,
                    level = level,
                    onBack = { selectedSection = null }
                )
            }
        }
    }
}

@Composable
fun JLPTStudyMenu(
    level: JlptLevel,
    onSelectSection: (JLPTStudySection) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Study Sections",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(getJLPTStudySections(level)) { section ->
            StudySectionCard(
                section = section,
                onClick = { onSelectSection(section) }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun StudySectionCard(
    section: JLPTStudySection,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = section.color.copy(alpha = 0.15f)
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
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(section.color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = section.icon,
                    fontSize = 28.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = section.color
                )
                Text(
                    text = section.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${section.items.size} items",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Open",
                tint = section.color
            )
        }
    }
}

@Composable
fun JLPTStudyContent(
    section: JLPTStudySection,
    level: JlptLevel,
    onBack: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val items = section.items
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Progress
        LinearProgressIndicator(
            progress = (currentIndex + 1).toFloat() / items.size,
            modifier = Modifier.fillMaxWidth(),
            color = section.color
        )
        
        Text(
            text = "${currentIndex + 1} / ${items.size}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Content card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = section.color.copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                when (val item = items[currentIndex]) {
                    is StudyItem.VocabularyItem -> VocabularyCard(item)
                    is StudyItem.GrammarItem -> GrammarCard(item)
                    is StudyItem.ReadingItem -> ReadingCard(item)
                    is StudyItem.KanjiItem -> KanjiCard(item)
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
                    if (currentIndex > 0) currentIndex-- 
                },
                enabled = currentIndex > 0
            ) {
                Text("← Previous")
            }
            
            if (currentIndex < items.size - 1) {
                Button(
                    onClick = { currentIndex++ },
                    colors = ButtonDefaults.buttonColors(containerColor = section.color)
                ) {
                    Text("Next →")
                }
            } else {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Mint)
                ) {
                    Text("✓ Complete")
                }
            }
        }
    }
}

@Composable
fun VocabularyCard(item: StudyItem.VocabularyItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = item.word,
            style = MaterialTheme.typography.displayLarge
        )
        
        Text(
            text = item.reading,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = "Meaning: ${item.meaning}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        if (item.exampleSentence.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Example:",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = item.exampleSentence,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun GrammarCard(item: StudyItem.GrammarItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = item.pattern,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        
        Text(
            text = item.meaning,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Formation:",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = item.formation,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Examples:",
                    style = MaterialTheme.typography.labelMedium
                )
                item.examples.forEach { example ->
                    Text(
                        text = "• $example",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReadingCard(item: StudyItem.ReadingItem) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.passage,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        Text(
            text = "Questions:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        item.questions.forEach { (question, answer) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Q: $question",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "A: $answer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Mint
                    )
                }
            }
        }
    }
}

@Composable
fun KanjiCard(item: StudyItem.KanjiItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = item.kanji,
            style = MaterialTheme.typography.displayLarge
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("On'yomi", style = MaterialTheme.typography.labelSmall)
                    Text(item.onyomi, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Kun'yomi", style = MaterialTheme.typography.labelSmall)
                    Text(item.kunyomi, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        
        Text(
            text = "Meaning: ${item.meaning}",
            style = MaterialTheme.typography.titleMedium
        )
        
        if (item.examples.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Examples:",
                        style = MaterialTheme.typography.labelMedium
                    )
                    item.examples.forEach { example ->
                        Text(
                            text = "• $example",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

// Data classes
sealed class StudyItem {
    data class VocabularyItem(
        val word: String,
        val reading: String,
        val meaning: String,
        val exampleSentence: String = ""
    ) : StudyItem()
    
    data class GrammarItem(
        val pattern: String,
        val meaning: String,
        val formation: String,
        val examples: List<String>
    ) : StudyItem()
    
    data class ReadingItem(
        val passage: String,
        val questions: List<Pair<String, String>>
    ) : StudyItem()
    
    data class KanjiItem(
        val kanji: String,
        val onyomi: String,
        val kunyomi: String,
        val meaning: String,
        val examples: List<String>
    ) : StudyItem()
}

data class JLPTStudySection(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val color: androidx.compose.ui.graphics.Color,
    val items: List<StudyItem>
)

fun getJLPTStudySections(level: JlptLevel): List<JLPTStudySection> {
    return when (level) {
        JlptLevel.N5 -> getN5StudySections()
        JlptLevel.N4 -> getN4StudySections()
        else -> getN5StudySections()
    }
}

fun getN5StudySections(): List<JLPTStudySection> {
    return listOf(
        JLPTStudySection(
            id = "n5_kanji",
            title = "Basic Kanji",
            description = "Master 100 essential N5 kanji",
            icon = "🈳",
            color = SakuraPink,
            items = listOf(
                StudyItem.KanjiItem("日", "ニチ", "ひ", "day/sun", listOf("今日 (today)", "日本 (Japan)")),
                StudyItem.KanjiItem("月", "ゲツ", "つき", "month/moon", listOf("一月 (January)", "月曜日 (Monday)")),
                StudyItem.KanjiItem("火", "カ", "ひ", "fire", listOf("火曜日 (Tuesday)", "花火 (fireworks)")),
                StudyItem.KanjiItem("水", "スイ", "みず", "water", listOf("水曜日 (Wednesday)", "水色 (light blue)")),
                StudyItem.KanjiItem("木", "モク", "き", "tree/wood", listOf("木曜日 (Thursday)", "大木 (big tree)")),
                StudyItem.KanjiItem("金", "キン", "かね", "gold/money", listOf("金曜日 (Friday)", "お金 (money)")),
                StudyItem.KanjiItem("土", "ド", "つち", "earth/soil", listOf("土曜日 (Saturday)", "土地 (land)")),
                StudyItem.KanjiItem("人", "ジン", "ひと", "person", listOf("日本人 (Japanese person)", "三人 (3 people)")),
                StudyItem.KanjiItem("男", "ダン", "おとこ", "man", listOf("男性 (male)", "長男 (eldest son)")),
                StudyItem.KanjiItem("女", "ジョ", "おんな", "woman", listOf("女性 (female)", "長女 (eldest daughter)")),
                StudyItem.KanjiItem("子", "シ", "こ", "child", listOf("子供 (child)", "男の子 (boy)")),
                StudyItem.KanjiItem("父", "フ", "ちち", "father", listOf("お父さん (father)", "父母 (parents)")),
                StudyItem.KanjiItem("母", "ボ", "はは", "mother", listOf("お母さん (mother)", "母国 (mother country)")),
                StudyItem.KanjiItem("友", "ユウ", "とも", "friend", listOf("友達 (friend)", "友人 (friend)")),
                StudyItem.KanjiItem("上", "ジョウ", "うえ", "above/up", listOf("上着 (jacket)", "上手 (skilled)")),
                StudyItem.KanjiItem("下", "カ", "した", "below/down", listOf("下着 (underwear)", "下手 (unskilled)")),
                StudyItem.KanjiItem("中", "チュウ", "なか", "inside/middle", listOf("中国 (China)", "中学校 (middle school)")),
                StudyItem.KanjiItem("大", "ダイ", "おお", "big/large", listOf("大学 (university)", "大切 (important)")),
                StudyItem.KanjiItem("小", "ショウ", "ちい", "small", listOf("小学校 (elementary school)", "小さい (small)")),
                StudyItem.KanjiItem("山", "サン", "やま", "mountain", listOf("山田 (Yamada)", "富士山 (Mt. Fuji)"))
            )
        ),
        JLPTStudySection(
            id = "n5_grammar",
            title = "Basic Grammar",
            description = "Essential N5 grammar patterns",
            icon = "📐",
            color = Lavender,
            items = listOf(
                StudyItem.GrammarItem(
                    "は (wa)",
                    "Topic particle - marks the topic of a sentence",
                    "Noun + は",
                    listOf("私は学生です。 (I am a student.)", "これは本です。 (This is a book.)")
                ),
                StudyItem.GrammarItem(
                    "が (ga)",
                    "Subject particle - marks the subject of a sentence",
                    "Noun + が",
                    listOf("猫がいます。 (There is a cat.)", "雨が降っています。 (It is raining.)")
                ),
                StudyItem.GrammarItem(
                    "を (wo)",
                    "Object particle - marks the direct object",
                    "Noun + を + Verb",
                    listOf("本を読みます。 (I read a book.)", "ご飯を食べます。 (I eat rice.)")
                ),
                StudyItem.GrammarItem(
                    "に (ni)",
                    "Time/Location particle - for time and destination",
                    "Time + に / Place + に",
                    listOf("７時に起きます。 (I wake up at 7.)", "学校に行きます。 (I go to school.)")
                ),
                StudyItem.GrammarItem(
                    "で (de)",
                    "Location/Means particle - for location of action or means",
                    "Place + で / Vehicle/Tool + で",
                    listOf("公園で遊びます。 (I play at the park.)", "バスで行きます。 (I go by bus.)")
                ),
                StudyItem.GrammarItem(
                    "へ (e)",
                    "Direction particle - indicates direction",
                    "Place + へ",
                    listOf("日本へ行きます。 (I go to Japan.)", "右へ曲がってください。 (Please turn right.)")
                ),
                StudyItem.GrammarItem(
                    "の (no)",
                    "Possessive particle - shows possession or connection",
                    "Noun + の + Noun",
                    listOf("私の本 (my book)", "日本の文化 (Japanese culture)")
                ),
                StudyItem.GrammarItem(
                    "と (to)",
                    "With/And particle - indicates accompaniment or listing",
                    "Noun + と",
                    listOf("友達と行きます。 (I go with a friend.)", "りんごとみかん (apples and oranges)")
                ),
                StudyItem.GrammarItem(
                    "です (desu)",
                    "Copula - 'is/am/are' (polite form)",
                    "Noun/Adjective + です",
                    listOf("学生です。 (I am a student.)", "きれいです。 (It is beautiful.)")
                ),
                StudyItem.GrammarItem(
                    "ます (masu)",
                    "Polite verb ending",
                    "Verb stem + ます",
                    listOf("行きます。 (I go.)", "食べます。 (I eat.)", "見ます。 (I watch.)")
                )
            )
        ),
        JLPTStudySection(
            id = "n5_vocabulary",
            title = "Core Vocabulary",
            description = "600+ essential N5 words",
            icon = "📚",
            color = Mint,
            items = listOf(
                StudyItem.VocabularyItem("私", "わたし", "I/me", "私は学生です。"),
                StudyItem.VocabularyItem("あなた", "あなた", "you", "あなたは日本人ですか。"),
                StudyItem.VocabularyItem("人", "ひと", "person", "あの人は誰ですか。"),
                StudyItem.VocabularyItem("本", "ほん", "book", "本を読みます。"),
                StudyItem.VocabularyItem("水", "みず", "water", "水を飲みます。"),
                StudyItem.VocabularyItem("食べる", "たべる", "to eat", "ご飯を食べます。"),
                StudyItem.VocabularyItem("飲む", "のむ", "to drink", "お茶を飲みます。"),
                StudyItem.VocabularyItem("見る", "みる", "to see/watch", "映画を見ます。"),
                StudyItem.VocabularyItem("行く", "いく", "to go", "学校へ行きます。"),
                StudyItem.VocabularyItem("来る", "くる", "to come", "友達が来ます。"),
                StudyItem.VocabularyItem("帰る", "かえる", "to return home", "家へ帰ります。"),
                StudyItem.VocabularyItem("話す", "はなす", "to speak", "日本語を話します。"),
                StudyItem.VocabularyItem("読む", "よむ", "to read", "新聞を読みます。"),
                StudyItem.VocabularyItem("書く", "かく", "to write", "手紙を書きます。"),
                StudyItem.VocabularyItem("聞く", "きく", "to hear/listen", "音楽を聞きます。"),
                StudyItem.VocabularyItem("買う", "かう", "to buy", "本を買います。"),
                StudyItem.VocabularyItem("待つ", "まつ", "to wait", "バスを待ちます。"),
                StudyItem.VocabularyItem("死ぬ", "しぬ", "to die", "「死」は漢字で「しぬ」と読みます。"),
                StudyItem.VocabularyItem("歩く", "あるく", "to walk", "駅まで歩きます。"),
                StudyItem.VocabularyItem("走る", "はしる", "to run", "公園を走ります。")
            )
        ),
        JLPTStudySection(
            id = "n5_reading",
            title = "Reading Practice",
            description = "Short passages for N5 level",
            icon = "📖",
            color = StarYellow,
            items = listOf(
                StudyItem.ReadingItem(
                    "田中さんは大学生です。毎日学校へ行きます。学校で日本語を勉強します。田中さんは日本語が好きです。",
                    listOf(
                        Pair("田中さんは何ですか。", "大学生です。"),
                        Pair("田中さんは何を勉強しますか。", "日本語を勉強します。")
                    )
                ),
                StudyItem.ReadingItem(
                    "私は毎朝７時に起きます。朝ご飯を食べて、８時に家を出ます。電車で会社へ行きます。会社は９時からです。",
                    listOf(
                        Pair("何時に起きますか。", "７時に起きます。"),
                        Pair("何で会社へ行きますか。", "電車で行きます。")
                    )
                ),
                StudyItem.ReadingItem(
                    "昨日は日曜日でした。家で休みました。本を読みました。夜、友達と電話で話しました。楽しい一日でした。",
                    listOf(
                        Pair("昨日は何曜日でしたか。", "日曜日でした。"),
                        Pair("昨日は何をしましたか。", "家で休みました。本を読みました。")
                    )
                )
            )
        )
    )
}

fun getN4StudySections(): List<JLPTStudySection> {
    return listOf(
        JLPTStudySection(
            id = "n4_kanji",
            title = "N4 Kanji",
            description = "Master 150 additional kanji",
            icon = "🈳",
            color = SakuraPink,
            items = listOf(
                StudyItem.KanjiItem("計", "ケイ", "はか", "plan/measure", listOf("計画 (plan)", "時計 (clock)")),
                StudyItem.KanjiItem("画", "ガ", "え", "picture", listOf("計画 (plan)", "映画 (movie)")),
                StudyItem.KanjiItem("不", "フ", "ふ", "not/negative", listOf("不便 (inconvenient)", "不安 (anxiety)")),
                StudyItem.KanjiItem("由", "ユ", "よし", "reason", listOf("理由 (reason)", "自由 (freedom)")),
                StudyItem.KanjiItem("理", "リ", "ことわり", "reason/logic", listOf("理由 (reason)", "理科 (science)")),
                StudyItem.KanjiItem("特", "トク", "とく", "special", listOf("特別 (special)", "特色 (characteristic)")),
                StudyItem.KanjiItem("別", "ベツ", "わか", "separate", listOf("特別 (special)", "別れる (to separate)")),
                StudyItem.KanjiItem("安", "アン", "やす", "cheap/safe", listOf("安い (cheap)", "安全 (safety)")),
                StudyItem.KanjiItem("心", "シン", "こころ", "heart/mind", listOf("安心 (relief)", "心配 (worry)")),
                StudyItem.KanjiItem("配", "ハイ", "くば", "distribute", listOf("心配 (worry)", "配る (to distribute)")),
                StudyItem.KanjiItem("注", "チュウ", "そそ", "pour/attention", listOf("注意 (attention)", "注文 (order)")),
                StudyItem.KanjiItem("意", "イ", "", "mind", listOf("注意 (attention)", "意味 (meaning)")),
                StudyItem.KanjiItem("味", "ミ", "あじ", "taste", listOf("意味 (meaning)", "趣味 (hobby)")),
                StudyItem.KanjiItem("文", "ブン", "ふみ", "sentence/literature", listOf("文化 (culture)", "文学 (literature)")),
                StudyItem.KanjiItem("化", "カ", "ば", "change", listOf("文化 (culture)", "変化 (change)")),
                StudyItem.KanjiItem("変", "ヘン", "か", "strange/change", listOf("変化 (change)", "変える (to change)")),
                StudyItem.KanjiItem("代", "ダイ", "か", "generation/fee", listOf("時代 (era)", "代わる (to substitute)")),
                StudyItem.KanjiItem("時", "ジ", "とき", "time", listOf("時代 (era)", "時間 (time)")),
                StudyItem.KanjiItem("間", "カン", "あいだ", "interval", listOf("時間 (time)", "間に合う (to be in time)")),
                StudyItem.KanjiItem("正", "セイ", "ただ", "correct", listOf("正直 (honest)", "正しい (correct)"))
            )
        ),
        JLPTStudySection(
            id = "n4_grammar",
            title = "N4 Grammar",
            description = "Intermediate grammar patterns",
            icon = "📐",
            color = Lavender,
            items = listOf(
                StudyItem.GrammarItem(
                    "ても (temo)",
                    "Even if/though - shows concession",
                    "Verb-て + も / Adj-て + も",
                    listOf("雨が降っても、出かけます。 (Even if it rains, I'll go out.)", "忙しくても、手伝います。 (Even if I'm busy, I'll help.)")
                ),
                StudyItem.GrammarItem(
                    "ば (ba)",
                    "If/When - conditional form",
                    "Verb-ば / Adj-ければ",
                    listOf("早く行けば、間に合います。 (If you go early, you'll be in time.)", "安ければ、買います。 (If it's cheap, I'll buy it.)")
                ),
                StudyItem.GrammarItem(
                    "のに (noni)",
                    "Even though/Although - shows contrast",
                    "Plain form + のに",
                    listOf("雨なのに、出かけました。 (Even though it was raining, I went out.)", "安いのに、買いませんでした。 (Even though it was cheap, I didn't buy it.)")
                ),
                StudyItem.GrammarItem(
                    "ので (node)",
                    "Because/Since - gives reason (softer than から)",
                    "Plain form + ので",
                    listOf("忙しいので、行けません。 (Because I'm busy, I can't go.)", "初心者なので、難しいです。 (Since I'm a beginner, it's difficult.)")
                ),
                StudyItem.GrammarItem(
                    "と (to)",
                    "When/Whenever - natural consequence",
                    "Dictionary form + と",
                    listOf("春になると、花が咲きます。 (When spring comes, flowers bloom.)", "この道をまっすぐ行くと、駅があります。 (If you go straight this road, there's a station.)")
                ),
                StudyItem.GrammarItem(
                    "てから (tekara)",
                    "After doing - sequence of actions",
                    "Verb-て + から",
                    listOf("ご飯を食べてから、勉強します。 (After eating, I study.)", "薬を飲んでから、寝てください。 (After taking medicine, please sleep.)")
                ),
                StudyItem.GrammarItem(
                    "ている (teiru)",
                    "Ongoing action or current state",
                    "Verb-て + いる",
                    listOf("今、ご飯を食べています。 (I'm eating now.)", "東京に住んでいます。 (I live in Tokyo.)")
                ),
                StudyItem.GrammarItem(
                    "られる (rareru)",
                    "Passive form or potential form",
                    "Verb + られる",
                    listOf("日本語が話せます。 (I can speak Japanese.)", "母に褒められました。 (I was praised by my mother.)")
                ),
                StudyItem.GrammarItem(
                    "せる/させる (seru/saseru)",
                    "Causative - make/let someone do",
                    "Verb + せる/させる",
                    listOf("子供に食べさせます。 (I make the child eat.)", "先生が読ませました。 (The teacher made us read.)")
                ),
                StudyItem.GrammarItem(
                    "よう (you)",
                    "Way of doing / like",
                    "Verb-よう / Noun + のよう",
                    listOf("泳ぎ方 (way of swimming)", "君のよう (like you)")
                ),
                StudyItem.GrammarItem(
                    "そう (sou)",
                    "I heard that / It seems",
                    "Plain form + そうです",
                    listOf("雨が降るそうです。 (I heard it will rain.)", "美味しそうです。 (It looks delicious.)")
                ),
                StudyItem.GrammarItem(
                    "らしい (rashii)",
                    "Seems like / I heard / Typical of",
                    "Plain form + らしい",
                    listOf("彼は学生らしい。 (He seems to be a student.)", "春らしい天気です。 (Spring-like weather.)")
                ),
                StudyItem.GrammarItem(
                    "みたい (mitai)",
                    "Like / Similar to / Seem",
                    "Plain form + みたい",
                    listOf("夢みたいです。 (It's like a dream.)", "彼は寝ているみたいです。 (He seems to be sleeping.)")
                ),
                StudyItem.GrammarItem(
                    "過ぎる (sugiru)",
                    "Too much / Excessive",
                    "Verb/Adj stem + 過ぎる",
                    listOf("食べ過ぎました。 (I ate too much.)", "高すぎます。 (It's too expensive.)")
                ),
                StudyItem.GrammarItem(
                    "易い/にくい (yasui/nikui)",
                    "Easy to / Hard to",
                    "Verb stem + 易い/にくい",
                    listOf("分かりやすいです。 (It's easy to understand.)", "読みにくいです。 (It's hard to read.)")
                )
            )
        ),
        JLPTStudySection(
            id = "n4_vocabulary",
            title = "N4 Vocabulary",
            description = "1500+ essential N4 words",
            icon = "📚",
            color = Mint,
            items = listOf(
                StudyItem.VocabularyItem("経験", "けいけん", "experience", "海外旅行の経験があります。"),
                StudyItem.VocabularyItem("計画", "けいかく", "plan", "週末の計画を立てます。"),
                StudyItem.VocabularyItem("趣味", "しゅみ", "hobby", "私の趣味は読書です。"),
                StudyItem.VocabularyItem("関係", "かんけい", "relationship", "彼とは家族の関係です。"),
                StudyItem.VocabularyItem("場合", "ばあい", "case/situation", "雨の場合は中止です。"),
                StudyItem.VocabularyItem("予定", "よてい", "plan/schedule", "明日の予定は何ですか。"),
                StudyItem.VocabularyItem("残業", "ざんぎょう", "overtime work", "今日は残業です。"),
                StudyItem.VocabularyItem("無理", "むり", "impossible/overdo", "無理をしないでください。"),
                StudyItem.VocabularyItem("注意", "ちゅうい", "attention/caution", "足元に注意してください。"),
                StudyItem.VocabularyItem("準備", "じゅんび", "preparation", "旅行の準備をします。"),
                StudyItem.VocabularyItem("参加", "さんか", "participation", "パーティーに参加します。"),
                StudyItem.VocabularyItem("説明", "せつめい", "explanation", "先生が説明します。"),
                StudyItem.VocabularyItem("約束", "やくそく", "promise/appointment", "友達と約束があります。"),
                StudyItem.VocabularyItem("都合", "つごう", "circumstances", "都合が悪いです。"),
                StudyItem.VocabularyItem("用事", "ようじ", "business/tasks", "用事があります。"),
                StudyItem.VocabularyItem("具合", "ぐあい", "condition", "具合が悪いです。"),
                StudyItem.VocabularyItem("遅刻", "ちこく", "lateness", "遅刻しないでください。"),
                StudyItem.VocabularyItem("留守", "るす", "absence", "今、留守です。"),
                StudyItem.VocabularyItem("喧嘩", "けんか", "fight/quarrel", "弟と喧嘩しました。"),
                StudyItem.VocabularyItem("正直", "しょうじき", "honest", "正直に言います。")
            )
        ),
        JLPTStudySection(
            id = "n4_reading",
            title = "N4 Reading",
            description = "Intermediate reading passages",
            icon = "📖",
            color = StarYellow,
            items = listOf(
                StudyItem.ReadingItem(
                    "最近、日本では「フリマアプリ」が人気になっています。これは、自分で使わなくなったものを写真を撮って、インターネットで売ることができるアプリです。手軽に使えるので、若い人を中心に人気です。",
                    listOf(
                        Pair("フリマアプリで何ができますか。", "使わないものを売ることができます。"),
                        Pair("誰に人気がありますか。", "若い人に人気があります。")
                    )
                ),
                StudyItem.ReadingItem(
                    "田中さんは毎日自転車で通勤しています。駅までの距離は近いので、自転車が便利だと言っています。雨の日は電車に乗りますが、晴れた日はいつも自転車です。運動にもなるし、電車賃もかからないので、お金も節約できます。",
                    listOf(
                        Pair("田中さんは普段どうやって通勤しますか。", "晴れた日は自転車、雨の日は電車です。"),
                        Pair("自転車の利点は何ですか。", "運動になるし、お金も節約できます。")
                    )
                ),
                StudyItem.ReadingItem(
                    "先週、友達と一緒に海外旅行に行きました。飛行機で約10時間かかりました。現地では、たくさんの観光名所を回りました。おいしい料理も食べました。お土産を買うのに、予算より多くお金を使ってしまいましたが、とても楽しい旅行でした。",
                    listOf(
                        Pair("旅行にはどのくらいかかりましたか。", "約10時間かかりました。"),
                        Pair("お土産についてどうですか。", "予算より多くお金を使いました。")
                    )
                )
            )
        )
    )
}

sealed class JLPTStudySectionId {
    object N5Kanji : JLPTStudySectionId()
    object N5Grammar : JLPTStudySectionId()
    object N5Vocabulary : JLPTStudySectionId()
    object N4Kanji : JLPTStudySectionId()
    object N4Grammar : JLPTStudySectionId()
    object N4Vocabulary : JLPTStudySectionId()
}
