package com.cutekana.ui.screens.learn

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.LearnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrammarStudyScreen(
    onBack: () -> Unit,
    viewModel: LearnViewModel = hiltViewModel()
) {
    var selectedTopic by remember { mutableStateOf<GrammarTopic?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Japanese Grammar") },
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
            val currentTopic = selectedTopic
            when (currentTopic) {
                null -> GrammarTopicsMenu(
                    onSelectTopic = { selectedTopic = it }
                )
                else -> GrammarTopicContent(
                    topic = currentTopic,
                    onBack = { selectedTopic = null }
                )
            }
        }
    }
}

@Composable
fun GrammarTopicsMenu(
    onSelectTopic: (GrammarTopic) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Grammar Topics",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(getGrammarTopics()) { topic ->
            GrammarTopicCard(
                topic = topic,
                onClick = { onSelectTopic(topic) }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun GrammarTopicCard(
    topic: GrammarTopic,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = topic.color.copy(alpha = 0.15f)
        )
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
                    .clip(RoundedCornerShape(12.dp))
                    .background(topic.color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = topic.icon,
                    fontSize = 24.sp
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = topic.color
                )
                Text(
                    text = topic.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${topic.points.size} points",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Open",
                tint = topic.color
            )
        }
    }
}

@Composable
fun GrammarTopicContent(
    topic: GrammarTopic,
    onBack: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val points = topic.points
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Progress
        LinearProgressIndicator(
            progress = (currentIndex + 1).toFloat() / points.size,
            modifier = Modifier.fillMaxWidth(),
            color = topic.color
        )
        
        Text(
            text = "${currentIndex + 1} / ${points.size}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Content card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = topic.color.copy(alpha = 0.1f)
            )
        ) {
            val point = points[currentIndex]
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(androidx.compose.foundation.rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Pattern/Form
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = point.pattern,
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                
                // Reading
                Text(
                    text = point.reading,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Meaning
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Meaning:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = point.meaning,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                
                // Formation
                if (point.formation.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Formation:",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = point.formation,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                
                // Examples
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Examples:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        point.examples.forEach { example ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(
                                    text = example.japanese,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = example.reading,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = example.english,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Mint
                                )
                            }
                            if (point.examples.indexOf(example) < point.examples.size - 1) {
                                Divider(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                }
                
                // Notes
                if (point.notes.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = StarYellow.copy(alpha = 0.1f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Notes:",
                                style = MaterialTheme.typography.labelMedium,
                                color = StarYellow
                            )
                            point.notes.forEach { note ->
                                Text(
                                    text = "• $note",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
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
            
            if (currentIndex < points.size - 1) {
                Button(
                    onClick = { currentIndex++ },
                    colors = ButtonDefaults.buttonColors(containerColor = topic.color)
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

// Data classes

data class GrammarExample(
    val japanese: String,
    val reading: String,
    val english: String
)

data class GrammarPoint(
    val pattern: String,
    val reading: String,
    val meaning: String,
    val formation: String = "",
    val examples: List<GrammarExample>,
    val notes: List<String> = emptyList()
)

data class GrammarTopic(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val color: androidx.compose.ui.graphics.Color,
    val points: List<GrammarPoint>
)

fun getGrammarTopics(): List<GrammarTopic> {
    return listOf(
        // Particles (助詞)
        GrammarTopic(
            id = "particles_basic",
            title = "Basic Particles",
            description = "Essential particles: は、が、を、に、で",
            icon = "🎯",
            color = SakuraPink,
            points = listOf(
                GrammarPoint(
                    "は (wa)",
                    "wa",
                    "Topic marker - introduces the topic of the sentence",
                    "Noun + は",
                    listOf(
                        GrammarExample("私は学生です。", "わたしはがくせいです。", "I am a student. (As for me, I am a student.)"),
                        GrammarExample("今日は暑いです。", "きょうはあついです。", "Today is hot. (As for today, it is hot.)")
                    ),
                    listOf("Contrasts with other things", "Replaces が when the topic is already known")
                ),
                GrammarPoint(
                    "が (ga)",
                    "ga",
                    "Subject marker - identifies what/who performs the action or has a property",
                    "Noun + が",
                    listOf(
                        GrammarExample("雨が降っています。", "あめがふっています。", "It's raining. (Rain is falling.)"),
                        GrammarExample("猫がいます。", "ねこがいます。", "There is a cat."),
                        GrammarExample("誰が来ましたか。", "だれがきましたか。", "Who came?")
                    ),
                    listOf("Used with question words", "Emphasizes the subject", "Used with いる/ある (exist)")
                ),
                GrammarPoint(
                    "を (wo/o)",
                    "wo/o",
                    "Object marker - marks the direct object of a verb",
                    "Noun + を + Verb",
                    listOf(
                        GrammarExample("本を読みます。", "ほんをよみます。", "I read a book."),
                        GrammarExample("ご飯を食べます。", "ごはんをたべます。", "I eat rice."),
                        GrammarExample("映画を見ます。", "えいがをみます。", "I watch a movie.")
                    ),
                    listOf("Always marks what receives the action", "Used with transitive verbs")
                ),
                GrammarPoint(
                    "に (ni)",
                    "ni",
                    "Location/Time/Direction marker - indicates time, destination, or indirect object",
                    "Noun + に",
                    listOf(
                        GrammarExample("７時に起きます。", "しちじにおきます。", "I wake up at 7 o'clock."),
                        GrammarExample("学校に行きます。", "がっこうにいきます。", "I go to school."),
                        GrammarExample("友達に会います。", "ともだちにあいます。", "I meet my friend."),
                        GrammarExample("母に電話します。", "ははにでんわします。", "I call my mother.")
                    ),
                    listOf("Time: Specific time points", "Location: Destination or existence", "Person: Indirect object")
                ),
                GrammarPoint(
                    "で (de)",
                    "de",
                    "Location/Means marker - indicates where an action happens or by what means",
                    "Place + で / Vehicle + で",
                    listOf(
                        GrammarExample("公園で遊びます。", "こうえんであそびます。", "I play at the park."),
                        GrammarExample("レストランで食べます。", "れすとらんでたべます。", "I eat at the restaurant."),
                        GrammarExample("電車で行きます。", "でんしゃでいきます。", "I go by train."),
                        GrammarExample("日本語で話します。", "にほんごではなします。", "I speak in Japanese.")
                    ),
                    listOf("Location of action (not existence!)", "Means/vehicle of transportation", "Language/tool used")
                )
            )
        ),
        
        // Verb Conjugations
        GrammarTopic(
            id = "verbs_conjugation",
            title = "Verb Conjugations",
            description = "Present, past, negative, and te-forms",
            icon = "📝",
            color = Lavender,
            points = listOf(
                GrammarPoint(
                    "Present/Future Affirmative (ます-form)",
                    "masu-form",
                    "Polite present/future tense - 'I do/will do'",
                    "Stem + ます",
                    listOf(
                        GrammarExample("行きます。", "いきます。", "I go / I will go."),
                        GrammarExample("食べます。", "たべます。", "I eat / I will eat."),
                        GrammarExample("見ます。", "みます。", "I watch / I will watch.")
                    ),
                    listOf("U-verbs: change final u to i + ます", "Ru-verbs: remove る + ます", "Irregular: する→します, 来る→来ます")
                ),
                GrammarPoint(
                    "Present/Future Negative (ません)",
                    "masen",
                    "Polite negative present/future - 'I don't/won't do'",
                    "Stem + ません",
                    listOf(
                        GrammarExample("行きません。", "いきません。", "I don't go / I won't go."),
                        GrammarExample("食べません。", "たべません。", "I don't eat / I won't eat."),
                        GrammarExample("見ません。", "みません。", "I don't watch / I won't watch.")
                    )
                ),
                GrammarPoint(
                    "Past Affirmative (ました)",
                    "mashita",
                    "Polite past tense - 'I did'",
                    "Stem + ました",
                    listOf(
                        GrammarExample("行きました。", "いきました。", "I went."),
                        GrammarExample("食べました。", "たべました。", "I ate."),
                        GrammarExample("見ました。", "みました。", "I watched.")
                    )
                ),
                GrammarPoint(
                    "Past Negative (ませんでした)",
                    "masen deshita",
                    "Polite negative past - 'I didn't do'",
                    "Stem + ませんでした",
                    listOf(
                        GrammarExample("行きませんでした。", "いきませんでした。", "I didn't go."),
                        GrammarExample("食べませんでした。", "たべませんでした。", "I didn't eat."),
                        GrammarExample("見ませんでした。", "みませんでした。", "I didn't watch.")
                    )
                ),
                GrammarPoint(
                    "Te-form (て-form)",
                    "te-form",
                    "Connective form used for many grammar patterns",
                    "Various rules based on verb type",
                    listOf(
                        GrammarExample("行って", "いって", "go (and...)"),
                        GrammarExample("食べて", "たべて", "eat (and...)"),
                        GrammarExample("見て", "みて", "watch (and...)")
                    ),
                    listOf("U-verbs ending in う、つ、る → って", "U-verbs ending in む、ぶ、ぬ → んで", "U-verbs ending in く → いて (ぐ→いで)", "U-verbs ending in す → して", "Ru-verbs: remove る + て", "Irregular: する→して, 来る→来て")
                ),
                GrammarPoint(
                    "ている (te iru)",
                    "te iru",
                    "Ongoing action or current state",
                    "Verb-て + いる",
                    listOf(
                        GrammarExample("食べています。", "たべています。", "I am eating. (ongoing)"),
                        GrammarExample("東京に住んでいます。", "とうきょうにすんでいます。", "I live in Tokyo. (current state)"),
                        GrammarExample("知っています。", "しっています。", "I know. (resultant state)")
                    ),
                    listOf("Ongoing action: ～ています (I am doing)", "Current state: ～ている (residence, employment)", "Resultant state: ～ている (wearing, knowledge, marriage)")
                )
            )
        ),
        
        // Adjectives
        GrammarTopic(
            id = "adjectives",
            title = "Adjectives",
            description = "い-adjectives and な-adjectives",
            icon = "🎨",
            color = Mint,
            points = listOf(
                GrammarPoint(
                    "い-adjectives (Present Affirmative)",
                    "i-adjectives",
                    "Adjectives ending in い - describe qualities",
                    "Dictionary form",
                    listOf(
                        GrammarExample("高いです。", "たかいです。", "It's tall/expensive."),
                        GrammarExample("新しいです。", "あたらしいです。", "It's new."),
                        GrammarExample("美味しいです。", "おいしいです。", "It's delicious.")
                    ),
                    listOf("Always end in い (when in dictionary form)", "Conjugate like verbs (drop い and add suffix)")
                ),
                GrammarPoint(
                    "い-adjectives (Present Negative)",
                    "i-adjectives negative",
                    "Negative form of い-adjectives",
                    "Drop い + くないです",
                    listOf(
                        GrammarExample("高くないです。", "たかくないです。", "It's not tall/expensive."),
                        GrammarExample("新しくないです。", "あたらしくないです。", "It's not new."),
                        GrammarExample("美味しくないです。", "おいしくないです。", "It's not delicious.")
                    )
                ),
                GrammarPoint(
                    "い-adjectives (Past)",
                    "i-adjectives past",
                    "Past forms of い-adjectives",
                    "Drop い + かったです / くなかったです",
                    listOf(
                        GrammarExample("高かったです。", "たかかったです。", "It was tall/expensive."),
                        GrammarExample("高くなかったです。", "たかくなかったです。", "It was not tall/expensive.")
                    )
                ),
                GrammarPoint(
                    "な-adjectives (Present Affirmative)",
                    "na-adjectives",
                    "Adjectives that need な before nouns",
                    "Dictionary form + です",
                    listOf(
                        GrammarExample("静かです。", "しずかです。", "It's quiet."),
                        GrammarExample("便利です。", "べんりです。", "It's convenient."),
                        GrammarExample("きれいです。", "きれいです。", "It's beautiful/clean."),
                        GrammarExample("好きです。", "すきです。", "I like it.")
                    ),
                    listOf("Most don't end in い (except きれい、嫌い)", "Add な before nouns: 静かな部屋")
                ),
                GrammarPoint(
                    "な-adjectives (Present Negative)",
                    "na-adjectives negative",
                    "Negative form of な-adjectives",
                    "Dictionary form + じゃないです",
                    listOf(
                        GrammarExample("静かじゃないです。", "しずかじゃないです。", "It's not quiet."),
                        GrammarExample("便利じゃないです。", "べんりじゃないです。", "It's not convenient."),
                        GrammarExample("好きじゃないです。", "すきじゃないです。", "I don't like it.")
                    )
                ),
                GrammarPoint(
                    "な-adjectives (Past)",
                    "na-adjectives past",
                    "Past forms of な-adjectives",
                    "Dictionary form + でした / じゃなかったです",
                    listOf(
                        GrammarExample("静かでした。", "しずかでした。", "It was quiet."),
                        GrammarExample("静かじゃなかったです。", "しずかじゃなかったです。", "It was not quiet.")
                    )
                )
            )
        ),
        
        // Sentence Patterns
        GrammarTopic(
            id = "sentence_patterns",
            title = "Sentence Patterns",
            description = "Common sentence structures",
            icon = "🏗️",
            color = StarYellow,
            points = listOf(
                GrammarPoint(
                    "〜たいです (tai desu)",
                    "tai desu",
                    "Want to do something",
                    "Verb stem + たいです",
                    listOf(
                        GrammarExample("日本へ行きたいです。", "にほんへいきたいです。", "I want to go to Japan."),
                        GrammarExample("寿司が食べたいです。", "すしがたべたいです。", "I want to eat sushi."),
                        GrammarExample("映画を見たくないです。", "えいがをみたくないです。", "I don't want to watch a movie.")
                    ),
                    listOf("Expresses the speaker's desire", "が can replace を with this pattern", "Conjugates like い-adjectives")
                ),
                GrammarPoint(
                    "〜ことができます (koto ga dekimasu)",
                    "koto ga dekimasu",
                    "Can do something / Ability",
                    "Dictionary form + ことができます",
                    listOf(
                        GrammarExample("日本語を話すことができます。", "にほんごをはなすことができます。", "I can speak Japanese."),
                        GrammarExample("泳ぐことができません。", "およぐことができません。", "I cannot swim."),
                        GrammarExample("漢字を読むことができますか。", "かんじをよむことができますか。", "Can you read kanji?")
                    ),
                    listOf("Formal way to express ability", "For casual: use potential form (話せる)")
                ),
                GrammarPoint(
                    "〜ましょう (mashou)",
                    "mashou",
                    "Let's do / Shall we?",
                    "Stem + ましょう",
                    listOf(
                        GrammarExample("行きましょう。", "いきましょう。", "Let's go."),
                        GrammarExample("食べましょうか。", "たべましょうか。", "Shall we eat?"),
                        GrammarExample("一緒に勉強しましょう。", "いっしょにべんきょうしましょう。", "Let's study together.")
                    ),
                    listOf("Invitation or suggestion", "Add か for 'Shall we?' question")
                ),
                GrammarPoint(
                    "〜ましょうか (mashou ka)",
                    "mashou ka",
                    "Shall I do...? (offering help)",
                    "Stem + ましょうか",
                    listOf(
                        GrammarExample("手伝いましょうか。", "てつだいましょうか。", "Shall I help you?"),
                        GrammarExample("荷物を持ちましょうか。", "にもつをもちましょうか。", "Shall I carry your luggage?"),
                        GrammarExample("窓を閉めましょうか。", "まどをしめましょうか。", "Shall I close the window?")
                    ),
                    listOf("Offering assistance", "Response: はい、お願いします or いいえ、結構です")
                ),
                GrammarPoint(
                    "〜てください (te kudasai)",
                    "te kudasai",
                    "Please do (request)",
                    "Verb-て + ください",
                    listOf(
                        GrammarExample("ここに名前を書いてください。", "ここになまえをかいてください。", "Please write your name here."),
                        GrammarExample("もう一度言ってください。", "もういちどいってください。", "Please say it again."),
                        GrammarExample("静かにしてください。", "しずかにしてください。", "Please be quiet.")
                    ),
                    listOf("Polite request", "More polite: ～てくださいませんか")
                ),
                GrammarPoint(
                    "〜てもいいです (te mo ii desu)",
                    "te mo ii desu",
                    "May do / It's okay to do (permission)",
                    "Verb-て + もいいです",
                    listOf(
                        GrammarExample("ここで写真を撮ってもいいです。", "ここでしゃしんをとってもいいです。", "You may take photos here."),
                        GrammarExample("入ってもいいですか。", "はいってもいいですか。", "May I come in?"),
                        GrammarExample("窓を開けてもいいですか。", "まどをあけてもいいですか。", "May I open the window?")
                    ),
                    listOf("Asking for permission: add か", "Giving permission: ～てもいいですよ")
                ),
                GrammarPoint(
                    "〜てはいけません (te wa ikemasen)",
                    "te wa ikemasen",
                    "Must not do (prohibition)",
                    "Verb-て + はいけません",
                    listOf(
                        GrammarExample("ここで泳いではいけません。", "ここでおよいではいけません。", "You must not swim here."),
                        GrammarExample("タバコを吸ってはいけません。", "たばこをすってはいけません。", "You must not smoke."),
                        GrammarExample("写真を撮ってはいけません。", "しゃしんをとってはいけません。", "You must not take photos.")
                    ),
                    listOf("Strong prohibition", "Often seen in signs and rules")
                ),
                GrammarPoint(
                    "〜なければなりません (nakereba narimasen)",
                    "nakereba narimasen",
                    "Must do / Have to do (obligation)",
                    "Negative stem + なければなりません",
                    listOf(
                        GrammarExample("毎日勉強しなければなりません。", "まいにちべんきょうしなければなりません。", "I must study every day."),
                        GrammarExample("薬を飲まなければなりません。", "くすりをのまなければなりません。", "I have to take medicine."),
                        GrammarExample("早く起きなければなりません。", "はやくおきなければなりません。", "I have to wake up early.")
                    ),
                    listOf("Strong obligation/social duty", "Casual: ～なきゃいけない / ～ないと")
                ),
                GrammarPoint(
                    "〜なくてもいいです (nakute mo ii desu)",
                    "nakute mo ii desu",
                    "Don't have to do (no obligation)",
                    "Negative te-form + もいいです",
                    listOf(
                        GrammarExample("明日は来なくてもいいです。", "あしたはこなくてもいいです。", "You don't have to come tomorrow."),
                        GrammarExample("心配しなくてもいいです。", "しんぱいしなくてもいいです。", "You don't have to worry."),
                        GrammarExample("全部食べなくてもいいです。", "ぜんぶたべなくてもいいです。", "You don't have to eat everything.")
                    ),
                    listOf("No obligation/necessity", "Opposite of ～なければなりません")
                )
            )
        ),
        
        // Comparison & Similarity
        GrammarTopic(
            id = "comparison",
            title = "Comparison & Similarity",
            description = "Comparing things and expressing similarity",
            icon = "⚖️",
            color = Lavender,
            points = listOf(
                GrammarPoint(
                    "〜より (yori)",
                    "yori",
                    "More than / Less than (comparison)",
                    "Compared item + より",
                    listOf(
                        GrammarExample("夏より冬の方が好きです。", "なつよりふゆのほうがすきです。", "I like winter more than summer."),
                        GrammarExample("電車よりバスの方が安いです。", "でんしゃよりばすのほうがやすいです。", "The bus is cheaper than the train."),
                        GrammarExample("去年より今年の方が暑いです。", "きょねんよりことしのほうがあついです。", "This year is hotter than last year.")
                    ),
                    listOf("AよりBの方がX = B is more X than A", "Without の方が: AよりBがX")
                ),
                GrammarPoint(
                    "〜の中で〜が一番 (no naka de ga ichiban)",
                    "no naka de ga ichiban",
                    "Among... is the most...",
                    "Group + の中で + Item + が一番 + Adjective",
                    listOf(
                        GrammarExample("果物の中で、りんごが一番好きです。", "くだもののなかで、りんごがいちばんすきです。", "Among fruits, I like apples the most."),
                        GrammarExample("日本の中で、東京が一番大きいです。", "にほんのなかで、とうきょうがいちばんおおきいです。", "In Japan, Tokyo is the biggest."),
                        GrammarExample("スポーツの中で、サッカーが一番上手です。", "すぽーつのなかで、さっかーがいちばんじょうずです。", "Among sports, I'm best at soccer.")
                    ),
                    listOf("Used for superlatives", "一番 (ichiban) = the most/number one")
                ),
                GrammarPoint(
                    "〜と同じ (to onaji)",
                    "to onaji",
                    "The same as...",
                    "Noun + と同じ",
                    listOf(
                        GrammarExample("私はあなたと同じ年です。", "わたしはあなたとおなじとしです。", "I am the same age as you."),
                        GrammarExample("これはそれと同じです。", "これはそれとおなじです。", "This is the same as that."),
                        GrammarExample("君と同じ考えです。", "きみとおなじかんがえです。", "I have the same opinion as you.")
                    )
                ),
                GrammarPoint(
                    "〜みたい (mitai)",
                    "mitai",
                    "Like / Looks like / Seems",
                    "Noun + みたい / Plain form + みたい",
                    listOf(
                        GrammarExample("あの人は日本人みたいです。", "あのひとはにほんじんみたいです。", "That person seems to be Japanese."),
                        GrammarExample("夢みたいです。", "ゆめみたいです。", "It's like a dream."),
                        GrammarExample("雨が降るみたいです。", "あめがふるみたいです。", "It looks like it's going to rain.")
                    ),
                    listOf("Colloquial form of よう", "Can be used with nouns directly")
                ),
                GrammarPoint(
                    "〜そうです (sou desu)",
                    "sou desu",
                    "I heard that / It appears that (hearsay/visual)",
                    "Plain form + そうです (hearsay)",
                    listOf(
                        GrammarExample("雨が降るそうです。", "あめがふるそうです。", "I heard that it will rain."),
                        GrammarExample("田中さんは来ないそうです。", "たなかさんはこないそうです。", "I heard that Tanaka is not coming."),
                        GrammarExample("美味しそうです。", "おいしそうです。", "It looks delicious. (visual)")
                    ),
                    listOf("Hearsay: plain form + そうです", "Visual: stem + そうです (looks like)")
                )
            )
        ),
        
        // Time Expressions
        GrammarTopic(
            id = "time_expressions",
            title = "Time Expressions",
            description = "Expressing when things happen",
            icon = "⏰",
            color = SakuraPink,
            points = listOf(
                GrammarPoint(
                    "〜時 (toki)",
                    "toki",
                    "When... / At the time of...",
                    "Plain form + 時",
                    listOf(
                        GrammarExample("日本に来た時、桜が咲いていました。", "にほんにきたとき、さくらがさいていました。", "When I came to Japan, cherry blossoms were blooming."),
                        GrammarExample("子供の時、よく公園で遊びました。", "こどものとき、よくこうえんであそびました。", "When I was a child, I often played at the park."),
                        GrammarExample("勉強する時、音楽を聞きます。", "べんきょうするとき、おんがくをききます。", "When I study, I listen to music.")
                    ),
                    listOf("Follows plain form", "Modified noun: 子供の時 (when I was a child)")
                ),
                GrammarPoint(
                    "〜前に (mae ni)",
                    "mae ni",
                    "Before doing...",
                    "Plain form + 前に",
                    listOf(
                        GrammarExample("寝る前に、本を読みます。", "ねるまえに、ほんをよみます。", "Before sleeping, I read a book."),
                        GrammarExample("ご飯を食べる前に、手を洗います。", "ごはんをたべるまえに、てをあらいます。", "Before eating, I wash my hands."),
                        GrammarExample("日本へ来る前に、日本語を勉強しました。", "にほんへくるまえに、にほんごをべんきょうしました。", "Before coming to Japan, I studied Japanese.")
                    )
                ),
                GrammarPoint(
                    "〜後で (ato de)",
                    "ato de",
                    "After doing...",
                    "Past form + 後で",
                    listOf(
                        GrammarExample("ご飯を食べた後で、勉強します。", "ごはんをたべたあとで、べんきょうします。", "After eating, I study."),
                        GrammarExample("仕事が終わった後で、飲みに行きます。", "しごとがおわったあとで、のみにいきます。", "After work, we go for drinks."),
                        GrammarExample("映画を見た後で、感想を話しました。", "えいがをみたあとで、かんそうをはなしました。", "After watching the movie, we talked about our thoughts.")
                    ),
                    listOf("Must use past tense before 後で")
                ),
                GrammarPoint(
                    "〜間に (aida ni)",
                    "aida ni",
                    "During / While / Before (time period ends)",
                    "Time period + の間に / Verb-ている + 間に",
                    listOf(
                        GrammarExample("夏休みの間に、海に行きました。", "なつやすみのあいだに、うみにいきました。", "During summer break, I went to the beach."),
                        GrammarExample("先生が話している間に、メモを取りました。", "せんせいがはなしているあいだに、めもをとりました。", "While the teacher was speaking, I took notes."),
                        GrammarExample("若い間に、いろいろなことをしたいです。", "わかいあいだに、いろいろなことをしたいです。", "Before I get old, I want to do various things.")
                    ),
                    listOf("間 (without に) = throughout the entire time", "間に (with に) = sometime during / before it ends")
                ),
                GrammarPoint(
                    "〜てから (te kara)",
                    "te kara",
                    "After doing (sequential)",
                    "Verb-て + から",
                    listOf(
                        GrammarExample("薬を飲んでから、寝てください。", "くすりをのんでから、ねてください。", "After taking medicine, please sleep."),
                        GrammarExample("ご飯を食べてから、出かけます。", "ごはんをたべてから、でかけます。", "After eating, I go out."),
                        GrammarExample("日本に来てから、三年になります。", "にほんにきてから、さんねんになります。", "It will be 3 years since I came to Japan.")
                    ),
                    listOf("Emphasizes sequence: A happens, then B", "Different from 後で: てから implies closer connection")
                ),
                GrammarPoint(
                    "〜ばかり (bakari)",
                    "bakari",
                    "Just / Only / About (time/amount)",
                    "Noun/Verb + ばかり",
                    listOf(
                        GrammarExample("日本に来たばかりです。", "にほんにきたばかりです。", "I just came to Japan."),
                        GrammarExample("食べたばかりなので、お腹が空いていません。", "たべたばかりなので、おなかがすいていません。", "I just ate, so I'm not hungry."),
                        GrammarExample("三時ばかりに来てください。", "さんじばかりにきてください。", "Please come around 3 o'clock.")
                    ),
                    listOf("Just completed: past form + ばかり", "Only: dictionary form + ばかり", "Approximately: time/number + ばかり")
                )
            )
        ),
        
        // Conditional Forms
        GrammarTopic(
            id = "conditional",
            title = "Conditional Forms",
            description = "Expressing if/when conditions",
            icon = "🔀",
            color = Mint,
            points = listOf(
                GrammarPoint(
                    "〜と (to) - Natural consequence",
                    "to",
                    "If/When... then... (inevitable result)",
                    "Plain form + と",
                    listOf(
                        GrammarExample("春になると、花が咲きます。", "はるになると、はながさきます。", "When spring comes, flowers bloom."),
                        GrammarExample("この道をまっすぐ行くと、駅があります。", "このみちをまっすぐいくと、えきがあります。", "If you go straight this road, there's a station."),
                        GrammarExample("早く起きないと、遅刻します。", "はやくおきないと、ちこくします。", "If you don't wake up early, you'll be late.")
                    ),
                    listOf("Natural/inevitable consequence", "Speaker's volition cannot be in the result clause")
                ),
                GrammarPoint(
                    "〜ば (ba)",
                    "ba",
                    "If... then... (conditional)",
                    "Verb-えば / い-adj-ければ / な-adj-なら(ば) / Noun-なら(ば)",
                    listOf(
                        GrammarExample("早く行けば、間に合います。", "はやくいけば、まにあいます。", "If we go early, we'll be in time."),
                        GrammarExample("安ければ、買います。", "やすければ、かいます。", "If it's cheap, I'll buy it."),
                        GrammarExample("いい天気なら、出かけましょう。", "いいてんきなら、でかけましょう。", "If the weather is good, let's go out.")
                    ),
                    listOf("Hypothetical condition", "Different endings for different word types")
                ),
                GrammarPoint(
                    "〜たら (tara)",
                    "tara",
                    "If/When... (casual, most versatile)",
                    "Past form + ら",
                    listOf(
                        GrammarExample("雨が降ったら、家で映画を見ます。", "あめがふったら、いえでえいがをみます。", "If it rains, I'll watch a movie at home."),
                        GrammarExample("日本に着いたら、電話してください。", "にほんについたら、でんわしてください。", "When you arrive in Japan, please call me."),
                        GrammarExample("時間があったら、一緒にご飯を食べませんか。", "じかんがあったら、いっしょにごはんをたべませんか。", "If you have time, shall we eat together?")
                    ),
                    listOf("Most common and versatile conditional", "Can be used with volition (たら〜しよう)")
                ),
                GrammarPoint(
                    "〜ても (temo) - Even if",
                    "temo",
                    "Even if/though...",
                    "Verb-て + も / Adj-くて + も / Noun-でも",
                    listOf(
                        GrammarExample("雨が降っても、出かけます。", "あめがふっても、でかけます。", "Even if it rains, I'll go out."),
                        GrammarExample("高くても、買います。", "たかくても、かいます。", "Even if it's expensive, I'll buy it."),
                        GrammarExample("明日でも、いいですか。", "あしたでも、いいですか。", "Is tomorrow okay too?"),
                        GrammarExample("子供でも、できます。", "こどもでも、できます。", "Even a child can do it.")
                    ),
                    listOf("Concession: despite the condition, result still happens", "でも with nouns means 'even noun'")
                ),
                GrammarPoint(
                    "〜と (to) - Quote/Speech",
                    "to (quote)",
                    "Quotation marker",
                    "Quoted content + と + verb of saying/thinking",
                    listOf(
                        GrammarExample("来ると言いました。", "くるといいました。", "He said he would come."),
                        GrammarExample("美味しいと思います。", "おいしいとおもいます。", "I think it's delicious."),
                        GrammarExample("「さようなら」と言いました。", "「さようなら」といいました。", "I said 'goodbye'.")
                    ),
                    listOf("Always used with verbs of communication/thought", "と言いました = said that", "と思います = think that")
                )
            )
        ),
        
        // Advanced Particles
        GrammarTopic(
            id = "advanced_particles",
            title = "Advanced Particles",
            description = "More complex particle uses",
            icon = "🔮",
            color = StarYellow,
            points = listOf(
                GrammarPoint(
                    "〜しか〜ない (shika nai)",
                    "shika nai",
                    "Nothing but / Only (negative)",
                    "Noun + しか + Negative",
                    listOf(
                        GrammarExample("私は日本語しか話せません。", "わたしはにほんごしかはなせません。", "I can speak nothing but Japanese."),
                        GrammarExample("今日は１００円しか持っていません。", "きょうはひゃくえんしかもっていません。", "Today I only have 100 yen."),
                        GrammarExample("あの人しか知りません。", "あのひとしかしりません。", "I only know that person. / I know no one but that person.")
                    ),
                    listOf("Emphasizes limitation/insufficiency", "Always paired with negative form")
                ),
                GrammarPoint(
                    "〜まで (made)",
                    "made",
                    "Until / To / Even",
                    "Noun/Time + まで",
                    listOf(
                        GrammarExample("５時まで働きます。", "ごじまではたらきます。", "I work until 5 o'clock."),
                        GrammarExample("東京まで行きます。", "とうきょうまでいきます。", "I go to Tokyo."),
                        GrammarExample("子供まで知っています。", "こどもまでしっています。", "Even children know it.")
                    ),
                    listOf("Time: until (time)", "Place: to/until (place)", "Even: emphasizing inclusiveness")
                ),
                GrammarPoint(
                    "〜から (kara)",
                    "kara",
                    "From / Because",
                    "Noun/Time + から (from) / Plain form + から (because)",
                    listOf(
                        GrammarExample("９時から始まります。", "くじからはじまります。", "It starts from 9 o'clock."),
                        GrammarExample("大阪から来ました。", "おおさかからきました。", "I came from Osaka."),
                        GrammarExample("忙しいから、行けません。", "いそがしいから、いけません。", "Because I'm busy, I can't go.")
                    ),
                    listOf("From (time/place)", "Because (reason - more subjective than ので)")
                ),
                GrammarPoint(
                    "〜けど (kedo) / 〜が (ga)",
                    "kedo/ga",
                    "But / Although (casual connector)",
                    "Plain form + けど/が",
                    listOf(
                        GrammarExample("いいけど、高いです。", "いいけど、たかいです。", "It's good, but expensive."),
                        GrammarExample("行きたいけど、忙しいです。", "いきたいけど、いそがしいです。", "I want to go, but I'm busy."),
                        GrammarExample("雨が降っていますが、出かけます。", "あめがふっていますが、でかけます。", "Although it's raining, I'll go out.")
                    ),
                    listOf("けど = casual, が = formal", "At the end of sentence: 'but...' (hanging)")
                ),
                GrammarPoint(
                    "〜や (ya)",
                    "ya",
                    "And / Such things as (non-exhaustive listing)",
                    "Noun + や + Noun + (など)",
                    listOf(
                        GrammarExample("りんごやみかんを買いました。", "りんごやみかんをかいました。", "I bought things like apples and oranges (and other things)."),
                        GrammarExample("本や雑誌などを読みます。", "ほんやざっしなどをよみます。", "I read things like books and magazines."),
                        GrammarExample("東京や大阪へ行きました。", "とうきょうやおおさかへいきました。", "I went to places like Tokyo and Osaka.")
                    ),
                    listOf("Indicates examples, not exhaustive list", "Often with など", "Compare to と which is exhaustive")
                ),
                GrammarPoint(
                    "〜など (nado)",
                    "nado",
                    "Such things as / Etc. / Or something",
                    "Noun + など",
                    listOf(
                        GrammarExample("映画などを見ました。", "えいがなどをみました。", "I watched a movie or something."),
                        GrammarExample("車やバスなどで行きます。", "くるまやばすなどでいきます。", "I go by car, bus, or something."),
                        GrammarExample("お茶でも飲みませんか。", "おちゃでものみませんか。", "Shall we drink tea or something?")
                    ),
                    listOf("Humble/dismissive tone", "Used with や for listing examples", "でも for suggestions")
                )
            )
        )
    )
}
