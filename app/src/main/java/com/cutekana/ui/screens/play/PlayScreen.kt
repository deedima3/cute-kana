package com.cutekana.ui.screens.play

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.ui.components.*
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.PlayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    onBack: () -> Unit,
    onNavigateToRadicalBuilder: () -> Unit,
    onNavigateToKanjiStory: () -> Unit,
    onNavigateToMatchPairs: () -> Unit,
    onNavigateToSpeedQuiz: () -> Unit,
    onNavigateToKanaGuess: () -> Unit,
    onNavigateToWordGuess: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Play & Practice") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Choose a Game",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Game cards
            item {
                GameCard(
                    title = "Kana Guess",
                    description = "Guess hiragana, katakana, or both!",
                    icon = "🎌",
                    color = MaterialTheme.colorScheme.primary,
                    highScore = uiState.kanaGuessHighScore,
                    onClick = onNavigateToKanaGuess
                )
            }

            item {
                GameCard(
                    title = "Word Guess",
                    description = "Guess words in hiragana or katakana!",
                    icon = "📝",
                    color = Lavender,
                    highScore = uiState.wordGuessHighScore,
                    onClick = onNavigateToWordGuess
                )
            }

            item {
                GameCard(
                    title = "Speed Quiz",
                    description = "Answer as fast as you can!",
                    icon = "⚡",
                    color = Color(0xFFFF9800),
                    highScore = uiState.speedQuizHighScore,
                    onClick = onNavigateToSpeedQuiz
                )
            }

            item {
                GameCard(
                    title = "Match Pairs",
                    description = "Match kana, romaji, and meanings",
                    icon = "🎯",
                    color = StarYellow,
                    highScore = uiState.matchPairsHighScore,
                    onClick = onNavigateToMatchPairs
                )
            }

            item {
                GameCard(
                    title = "Radical Builder",
                    description = "Build kanji from components",
                    icon = "🧩",
                    color = Lavender,
                    highScore = uiState.radicalBuilderHighScore,
                    onClick = onNavigateToRadicalBuilder
                )
            }

            item {
                GameCard(
                    title = "Kanji Story",
                    description = "Read anime scenes with kanji",
                    icon = "📖",
                    color = Mint,
                    highScore = uiState.kanjiStoryHighScore,
                    onClick = onNavigateToKanjiStory
                )
            }

            // Bottom spacing to account for bottom navigation bar
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// UI State
data class PlayUiState(
    val radicalBuilderHighScore: Int = 0,
    val kanjiStoryHighScore: Int = 0,
    val matchPairsHighScore: Int = 0,
    val speedQuizHighScore: Int = 0,
    val kanaGuessHighScore: Int = 0,
    val wordGuessHighScore: Int = 0,
    val selectedGame: PlayGameMode? = null,
    val message: String? = null
)

enum class PlayGameMode(
    val displayName: String
) {
    RADICAL_BUILDER("Radical Builder"),
    KANJI_STORY("Kanji Story"),
    MATCH_PAIRS("Match Pairs"),
    SPEED_QUIZ("Speed Quiz"),
    KANA_GUESS("Kana Guess"),
    WORD_GUESS("Word Guess")
}

@Composable
fun GameCard(
    title: String,
    description: String,
    icon: String,
    color: androidx.compose.ui.graphics.Color,
    highScore: Int,
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
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (highScore > 0) {
                    Text(
                        text = "🏆 High Score: $highScore",
                        style = MaterialTheme.typography.labelMedium,
                        color = StarYellow
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = color
            )
        }
    }
}

