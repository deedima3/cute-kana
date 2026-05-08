package com.cutekana.ui.screens.play

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.cutekana.ui.components.CuteButton
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.PlayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadicalBuilderGame(
    onBack: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var gameState by remember { mutableStateOf(RadicalBuilderState()) }
    var showHint by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showGameComplete by remember { mutableStateOf(false) }
    
    // Initialize
    LaunchedEffect(Unit) {
        if (gameState.currentKanji == null) {
            gameState = gameState.nextLevel()
        }
    }
    
    // Check win
    LaunchedEffect(gameState.currentLevel, gameState.completedLevels) {
        if (gameState.completedLevels >= 5 && !showGameComplete) {
            showGameComplete = true
            viewModel.updateHighScore(PlayGameMode.RADICAL_BUILDER, gameState.score)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Radical Builder") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Progress
                    Text(
                        text = "${gameState.completedLevels}/5",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    
                    // Score
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Lavender.copy(alpha = 0.2f)
                        )
                    ) {
                        Text(
                            text = "🏆 ${gameState.score}",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = Lavender
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
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
                showGameComplete -> {
                    RadicalBuilderComplete(
                        score = gameState.score,
                        highScore = maxOf(gameState.score, uiState.radicalBuilderHighScore),
                        onPlayAgain = {
                            showGameComplete = false
                            gameState = RadicalBuilderState().nextLevel()
                        },
                        onBack = onBack
                    )
                }
                showSuccess -> {
                    // Success animation screen
                    RadicalBuilderSuccess(
                        kanji = gameState.currentKanji?.kanji ?: "",
                        meaning = gameState.currentKanji?.meaning ?: "",
                        onContinue = {
                            showSuccess = false
                            gameState = gameState.nextLevel()
                        }
                    )
                }
                else -> {
                    // Game screen
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Instructions
                        Text(
                            text = "Tap the radicals to build the kanji!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Target kanji (faded outline)
                        Card(
                            modifier = Modifier.size(200.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                // Show the target kanji very faintly as a guide
                                gameState.currentKanji?.let { kanji ->
                                    Text(
                                        text = kanji.kanji,
                                        fontSize = 100.sp,
                                        color = if (showHint) 
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                        else 
                                            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Meaning hint
                        gameState.currentKanji?.let { kanji ->
                            Text(
                                text = "Meaning: ${kanji.meaning}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Lavender
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Reading
                            Text(
                                text = "Reading: ${kanji.reading}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Selected radicals area
                        if (gameState.selectedRadicals.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Lavender.copy(alpha = 0.1f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Your Selection:",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    Text(
                                        text = gameState.selectedRadicals.joinToString(" + ") { it.radical },
                                        fontSize = 40.sp
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    Row {
                                        OutlinedButton(
                                            onClick = { gameState = gameState.clearSelection() }
                                        ) {
                                            Text("Clear")
                                        }
                                        
                                        Spacer(modifier = Modifier.width(8.dp))
                                        
                                        CuteButton(
                                            text = "✓ Check",
                                            onClick = {
                                                if (gameState.isCorrect()) {
                                                    showSuccess = true
                                                    gameState = gameState.completeLevel()
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        
                        // Radical options
                        Text(
                            text = "Available Radicals:",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(gameState.availableRadicals) { radical ->
                                val isSelected = gameState.selectedRadicals.any { it.id == radical.id }
                                
                                Card(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clickable {
                                            gameState = gameState.toggleRadical(radical)
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) 
                                            Lavender.copy(alpha = 0.3f)
                                        else 
                                            MaterialTheme.colorScheme.surface
                                    ),
                                    border = if (isSelected) {
                                        androidx.compose.foundation.BorderStroke(
                                            2.dp, Lavender
                                        )
                                    } else null
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = radical.radical,
                                            fontSize = 28.sp
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        // Hint button
                        TextButton(
                            onClick = { showHint = !showHint },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(if (showHint) "Hide Hint" else "💡 Show Hint")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadicalBuilderSuccess(
    kanji: String,
    meaning: String,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🧩",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Excellent!",
            style = MaterialTheme.typography.headlineLarge,
            color = Mint
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.size(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Mint.copy(alpha = 0.2f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = kanji,
                    fontSize = 120.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = meaning,
            style = MaterialTheme.typography.headlineSmall,
            color = Lavender
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        CuteButton(
            text = "Continue →",
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RadicalBuilderComplete(
    score: Int,
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
            text = "🎉",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (score >= highScore) "New High Score!" else "All Levels Complete!",
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
                    text = "Final Score: $score",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Lavender
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

// Game data
data class KanjiLevel(
    val kanji: String,
    val meaning: String,
    val reading: String,
    val requiredRadicals: List<String>
)

data class Radical(
    val id: Int,
    val radical: String,
    val name: String
)

private val kanjiLevels = listOf(
    KanjiLevel("男", "man/male", "おとこ (otoko)", listOf("田", "力")),
    KanjiLevel("明", "bright", "あかるい (akarui)", listOf("日", "月")),
    KanjiLevel("林", "forest", "はやし (hayashi)", listOf("木", "木")),
    KanjiLevel("岩", "rock", "いわ (iwa)", listOf("山", "石")),
    KanjiLevel("音", "sound", "おと (oto)", listOf("立", "日"))
)

private val allRadicals = listOf(
    Radical(1, "田", "rice field"),
    Radical(2, "力", "power"),
    Radical(3, "日", "sun/day"),
    Radical(4, "月", "moon/month"),
    Radical(5, "木", "tree"),
    Radical(6, "山", "mountain"),
    Radical(7, "石", "stone"),
    Radical(8, "立", "stand"),
    Radical(9, "口", "mouth"),
    Radical(10, "人", "person"),
    Radical(11, "一", "one"),
    Radical(12, "二", "two")
)

data class RadicalBuilderState(
    val currentLevel: Int = 0,
    val completedLevels: Int = 0,
    val score: Int = 0,
    val currentKanji: KanjiLevel? = null,
    val availableRadicals: List<Radical> = emptyList(),
    val selectedRadicals: List<Radical> = emptyList()
) {
    fun nextLevel(): RadicalBuilderState {
        if (currentLevel >= kanjiLevels.size) return this
        
        val kanji = kanjiLevels[currentLevel]
        // Include required radicals plus some distractors
        val required = allRadicals.filter { it.radical in kanji.requiredRadicals }
        val distractors = allRadicals.filter { it.radical !in kanji.requiredRadicals }
            .shuffled()
            .take(4)
        
        return copy(
            currentKanji = kanji,
            availableRadicals = (required + distractors).shuffled(),
            selectedRadicals = emptyList()
        )
    }
    
    fun toggleRadical(radical: Radical): RadicalBuilderState {
        return if (selectedRadicals.any { it.id == radical.id }) {
            copy(selectedRadicals = selectedRadicals.filter { it.id != radical.id })
        } else {
            copy(selectedRadicals = selectedRadicals + radical)
        }
    }
    
    fun clearSelection(): RadicalBuilderState {
        return copy(selectedRadicals = emptyList())
    }
    
    fun isCorrect(): Boolean {
        val selected = selectedRadicals.map { it.radical }.sorted()
        val required = currentKanji?.requiredRadicals?.sorted() ?: emptyList()
        return selected == required
    }
    
    fun completeLevel(): RadicalBuilderState {
        return copy(
            currentLevel = currentLevel + 1,
            completedLevels = completedLevels + 1,
            score = score + 100
        )
    }
}
