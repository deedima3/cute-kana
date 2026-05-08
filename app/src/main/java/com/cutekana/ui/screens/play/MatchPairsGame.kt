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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchPairsGame(
    onBack: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    
    var gameState by remember { mutableStateOf(MatchPairsGameState()) }
    var selectedCard by remember { mutableStateOf<MatchCard?>(null) }
    var isChecking by remember { mutableStateOf(false) }
    var showGameOver by remember { mutableStateOf(false) }
    
    // Initialize game
    LaunchedEffect(Unit) {
        if (gameState.cards.isEmpty()) {
            gameState = createMatchPairsGame()
        }
    }
    
    // Check win condition
    LaunchedEffect(gameState.matchedPairs) {
        if (gameState.matchedPairs == gameState.totalPairs && gameState.totalPairs > 0) {
            delay(500)
            showGameOver = true
            viewModel.updateHighScore(PlayGameMode.MATCH_PAIRS, gameState.score)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Match Pairs") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        text = "⏱️ ${gameState.timeElapsed}s",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(end = 8.dp)
                    )
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
                gameState.cards.isEmpty() -> {
                    // Loading
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                showGameOver -> {
                    // Game over
                    MatchPairsGameOver(
                        score = gameState.score,
                        time = gameState.timeElapsed,
                        moves = gameState.moves,
                        highScore = maxOf(gameState.score, uiState.matchPairsHighScore),
                        onPlayAgain = {
                            gameState = createMatchPairsGame()
                            selectedCard = null
                            showGameOver = false
                        },
                        onBack = onBack
                    )
                }
                else -> {
                    // Game grid
                    Column {
                        // Progress
                        LinearProgressIndicator(
                            progress = gameState.matchedPairs.toFloat() / gameState.totalPairs,
                            modifier = Modifier.fillMaxWidth(),
                            color = StarYellow
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Card grid
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(gameState.cards) { card ->
                                MatchCardItem(
                                    card = card,
                                    isSelected = selectedCard?.id == card.id,
                                    onClick = {
                                        if (!isChecking && !card.isMatched && !card.isRevealed) {
                                            if (selectedCard == null) {
                                                selectedCard = card
                                                gameState = gameState.revealCard(card.id)
                                            } else if (selectedCard?.id != card.id) {
                                                isChecking = true
                                                gameState = gameState.revealCard(card.id)
                                                gameState = gameState.incrementMoves()
                                                
                                                // Check match
                                                if (selectedCard?.content == card.content) {
                                                    // Match!
                                                    gameState = gameState.matchPair(selectedCard!!.id, card.id)
                                                    gameState = gameState.addScore(50)
                                                    selectedCard = null
                                                    isChecking = false
                                                } else {
                                                    // No match - flip back after delay
                                                    coroutineScope.launch {
                                                        delay(1000)
                                                        gameState = gameState.hideCards(
                                                            selectedCard!!.id, 
                                                            card.id
                                                        )
                                                        selectedCard = null
                                                        isChecking = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                    
                    // Timer
                    LaunchedEffect(gameState.isActive) {
                        while (gameState.isActive && !showGameOver) {
                            delay(1000)
                            gameState = gameState.incrementTime()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchCardItem(
    card: MatchCard,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick, enabled = !card.isMatched && !card.isRevealed),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                card.isMatched -> Mint.copy(alpha = 0.3f)
                card.isRevealed || isSelected -> Color.White
                else -> StarYellow.copy(alpha = 0.3f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (card.isRevealed || card.isMatched) 8.dp else 2.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (card.isRevealed || card.isMatched) {
                Text(
                    text = card.content,
                    fontSize = if (card.type == CardType.KANA) 40.sp else 18.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "?",
                    fontSize = 32.sp,
                    color = StarYellow
                )
            }
        }
    }
}

@Composable
fun MatchPairsGameOver(
    score: Int,
    time: Int,
    moves: Int,
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
            text = if (score >= highScore) "🎉" else "🎯",
            fontSize = 80.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (score >= highScore) "New High Score!" else "Level Complete!",
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
                    color = StarYellow
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("⏱️ Time", "$time s")
                    StatItem("🔄 Moves", "$moves")
                    StatItem("🏆 Best", "$highScore")
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

@Composable
private fun StatItem(label: String, value: String) {
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

// Game data
data class MatchCard(
    val id: Int,
    val content: String,
    val type: CardType,
    val isRevealed: Boolean = false,
    val isMatched: Boolean = false
)

enum class CardType { KANA, ROMAJI, MEANING }

data class MatchPairsGameState(
    val cards: List<MatchCard> = emptyList(),
    val matchedPairs: Int = 0,
    val totalPairs: Int = 6,
    val score: Int = 0,
    val moves: Int = 0,
    val timeElapsed: Int = 0,
    val isActive: Boolean = true
) {
    fun revealCard(id: Int): MatchPairsGameState {
        return copy(cards = cards.map { 
            if (it.id == id) it.copy(isRevealed = true) else it 
        })
    }
    
    fun hideCards(id1: Int, id2: Int): MatchPairsGameState {
        return copy(cards = cards.map { 
            when (it.id) {
                id1, id2 -> it.copy(isRevealed = false)
                else -> it
            }
        })
    }
    
    fun matchPair(id1: Int, id2: Int): MatchPairsGameState {
        return copy(
            cards = cards.map { 
                when (it.id) {
                    id1, id2 -> it.copy(isMatched = true, isRevealed = true)
                    else -> it
                }
            },
            matchedPairs = matchedPairs + 1
        )
    }
    
    fun addScore(points: Int): MatchPairsGameState {
        return copy(score = score + points)
    }
    
    fun incrementMoves(): MatchPairsGameState {
        return copy(moves = moves + 1)
    }
    
    fun incrementTime(): MatchPairsGameState {
        return copy(timeElapsed = timeElapsed + 1)
    }
}

fun createMatchPairsGame(): MatchPairsGameState {
    val pairs = listOf(
        Triple("あ", "a", "ah"),
        Triple("い", "i", "ee"),
        Triple("う", "u", "oo"),
        Triple("え", "e", "eh"),
        Triple("か", "ka", "kah"),
        Triple("き", "ki", "kee")
    )
    
    var id = 0
    val cards = pairs.flatMap { (kana, romaji, _) ->
        listOf(
            MatchCard(id++, kana, CardType.KANA),
            MatchCard(id++, romaji, CardType.ROMAJI)
        )
    }.shuffled()
    
    return MatchPairsGameState(
        cards = cards,
        totalPairs = pairs.size,
        isActive = true
    )
}
