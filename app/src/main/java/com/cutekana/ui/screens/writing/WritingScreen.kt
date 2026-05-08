package com.cutekana.ui.screens.writing

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.model.PointF
import com.cutekana.data.ml.RecognitionResult
import com.cutekana.ui.components.*
import com.cutekana.ui.theme.*
import com.cutekana.ui.viewmodel.WritingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingScreen(
    onBack: () -> Unit,
    viewModel: WritingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Writing Practice") },
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
        when (uiState.mode) {
            WritingScreenMode.MENU -> WritingMenu(
                onGuidedPractice = { viewModel.setMode(WritingScreenMode.GUIDED) },
                onFreeWriting = { viewModel.setMode(WritingScreenMode.FREE) },
                onSpeedChallenge = { viewModel.setMode(WritingScreenMode.SPEED) },
                onMemoryMode = { viewModel.setMode(WritingScreenMode.MEMORY) },
                recentCharacters = uiState.recentCharacters,
                modifier = Modifier.padding(padding)
            )
            
            WritingScreenMode.GUIDED, 
            WritingScreenMode.FREE,
            WritingScreenMode.SPEED,
            WritingScreenMode.MEMORY -> WritingPractice(
                state = uiState,
                onStrokeCollected = viewModel::onStrokeCollected,
                onCheck = viewModel::checkCurrentStroke,
                onNext = viewModel::nextCharacter,
                onReset = viewModel::resetCurrent,
                onBackToMenu = { viewModel.setMode(WritingScreenMode.MENU) },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun WritingMenu(
    onGuidedPractice: () -> Unit,
    onFreeWriting: () -> Unit,
    onSpeedChallenge: () -> Unit,
    onMemoryMode: () -> Unit,
    recentCharacters: List<CharacterItem>,
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
                text = "Choose Practice Mode",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        
        item {
            PracticeModeCard(
                title = "Guided Practice",
                description = "Follow the animated stroke guide",
                icon = "✨",
                color = SakuraPink,
                onClick = onGuidedPractice
            )
        }
        
        item {
            PracticeModeCard(
                title = "Free Writing",
                description = "Write with reference outline",
                icon = "✍️",
                color = Lavender,
                onClick = onFreeWriting
            )
        }
        
        item {
            PracticeModeCard(
                title = "Speed Challenge",
                description = "10 characters, race against time!",
                icon = "⚡",
                color = StarYellow,
                onClick = onSpeedChallenge
            )
        }
        
        item {
            PracticeModeCard(
                title = "Memory Mode",
                description = "Write from memory, no guides",
                icon = "🧠",
                color = Mint,
                onClick = onMemoryMode
            )
        }
        
        if (recentCharacters.isNotEmpty()) {
            item {
                Text(
                    text = "Recent Characters",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            
            items(recentCharacters) { char ->
                RecentCharacterRow(character = char)
            }
        }
    }
}

@Composable
fun PracticeModeCard(
    title: String,
    description: String,
    icon: String,
    color: androidx.compose.ui.graphics.Color,
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
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 28.sp)
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
            }
            
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = color
            )
        }
    }
}

@Composable
fun WritingPractice(
    state: WritingUiState,
    onStrokeCollected: (List<PointF>) -> Unit,
    onCheck: () -> Unit,
    onNext: () -> Unit,
    onReset: () -> Unit,
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Track current stroke points for manual checking
    var currentStrokePoints by remember { mutableStateOf<List<PointF>>(emptyList()) }
    var hasDrawnStroke by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackToMenu) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            
            // Character info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = state.currentCharacter,
                    style = KanaDisplayStyle.copy(fontSize = 48.sp)
                )
                Text(
                    text = state.currentRomaji,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onReset) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
            }
        }
        
        // Progress
        if (state.totalCharacters > 1) {
            Text(
                text = "${state.currentIndex + 1} / ${state.totalCharacters}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Stroke order indicator
        StrokeOrderIndicator(
            totalStrokes = state.totalStrokes,
            currentStroke = state.currentStrokeIndex,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Writing canvas with manual stroke collection
        WritingCanvasManual(
            character = state.currentCharacter,
            strokes = state.currentStrokes,
            currentStrokeIndex = state.currentStrokeIndex,
            mode = when (state.mode) {
                WritingScreenMode.GUIDED -> WritingMode.GUIDED
                WritingScreenMode.FREE -> WritingMode.REFERENCE
                WritingScreenMode.MEMORY -> WritingMode.MEMORY
                else -> WritingMode.GUIDED
            },
            onStrokeDrawn = { points ->
                currentStrokePoints = points
                hasDrawnStroke = points.isNotEmpty()
                onStrokeCollected(points)
            },
            recognitionResult = state.recognitionResult,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Feedback
        AnimatedVisibility(visible = state.recognitionResult != null) {
            WritingFeedback(
                result = state.recognitionResult,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Controls
        when {
            state.recognitionResult?.isCorrect == true -> {
                CuteButton(
                    text = if (state.currentIndex < state.totalCharacters - 1) "Next Character →" else "Finish",
                    onClick = {
                        hasDrawnStroke = false
                        currentStrokePoints = emptyList()
                        onNext()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            hasDrawnStroke && state.recognitionResult == null -> {
                // Show Check button when user has drawn but not checked yet
                CuteButton(
                    text = "✓ Check",
                    onClick = onCheck,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            else -> {
                // Show hint when no stroke drawn
                CuteOutlineButton(
                    text = "Draw the stroke, then tap Check",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                )
            }
        }
    }
}

@Composable
fun RecentCharacterRow(character: CharacterItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = character.character,
                style = KanaDisplayStyle.copy(fontSize = 36.sp),
                modifier = Modifier.width(56.dp)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.romaji,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Last practiced: ${character.lastPracticed}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            CuteOutlineButton(
                text = "Practice",
                onClick = { /* Navigate to this character */ }
            )
        }
    }
}

// UI State
data class WritingUiState(
    val mode: WritingScreenMode = WritingScreenMode.MENU,
    val currentCharacter: String = "あ",
    val currentRomaji: String = "a",
    val currentStrokes: List<com.cutekana.data.model.StrokeData> = emptyList(),
    val currentStrokeIndex: Int = 0,
    val totalStrokes: Int = 3,
    val currentIndex: Int = 0,
    val totalCharacters: Int = 10,
    val recognitionResult: RecognitionResult? = null,
    val recentCharacters: List<CharacterItem> = emptyList()
)

enum class WritingScreenMode {
    MENU, GUIDED, FREE, SPEED, MEMORY
}

data class CharacterItem(
    val character: String,
    val romaji: String,
    val lastPracticed: String,
    val accuracy: Float
)