package com.cutekana.ui.screens.learn

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.model.CharacterType
import com.cutekana.ui.viewmodel.LearnViewModel
import com.cutekana.ui.viewmodel.CharacterDetailViewModel
import com.cutekana.data.model.JlptLevel
import com.cutekana.data.model.Rarity
import com.cutekana.ui.components.*
import com.cutekana.ui.theme.KanaDisplayStyle
import com.cutekana.ui.theme.SakuraPink
import com.cutekana.ui.theme.Lavender
import com.cutekana.ui.theme.Mint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    onBack: () -> Unit,
    onNavigateToCharacter: (String) -> Unit,
    viewModel: LearnViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedStudyMode by remember { mutableStateOf<LearnStudyMode?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learn") },
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
            when (selectedStudyMode) {
                null -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Category tabs
                        ScrollableTabRow(
                            selectedTabIndex = uiState.selectedTabIndex,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            uiState.categories.forEachIndexed { index, category ->
                                Tab(
                                    selected = uiState.selectedTabIndex == index,
                                    onClick = { viewModel.selectCategory(index) },
                                    text = { Text(category.title) }
                                )
                            }
                        }
                        
                        // Content
                        when (uiState.currentCategory) {
                            is LearnCategory.Hiragana -> CharacterGrid(
                                characters = uiState.hiraganaCharacters,
                                onCharacterClick = onNavigateToCharacter
                            )
                            is LearnCategory.Katakana -> CharacterGrid(
                                characters = uiState.katakanaCharacters,
                                onCharacterClick = onNavigateToCharacter
                            )
                            is LearnCategory.KanjiN5 -> KanjiGrid(
                                characters = uiState.n5Kanji,
                                onCharacterClick = onNavigateToCharacter
                            )
                            is LearnCategory.KanjiN4 -> KanjiGrid(
                                characters = uiState.n4Kanji,
                                onCharacterClick = onNavigateToCharacter
                            )
                            is LearnCategory.Grammar -> GrammarStudyScreen(
                                onBack = { selectedStudyMode = null }
                            )
                            is LearnCategory.JLPTN5Study -> JLPTStudyScreen(
                                level = JlptLevel.N5,
                                onBack = { selectedStudyMode = null }
                            )
                            is LearnCategory.JLPTN4Study -> JLPTStudyScreen(
                                level = JlptLevel.N4,
                                onBack = { selectedStudyMode = null }
                            )
                        }
                    }
                }
                else -> {
                    // Handle study mode navigation
                    when (selectedStudyMode) {
                        is LearnStudyMode.Grammar -> GrammarStudyScreen(
                            onBack = { selectedStudyMode = null }
                        )
                        is LearnStudyMode.JLPTN5 -> JLPTStudyScreen(
                            level = JlptLevel.N5,
                            onBack = { selectedStudyMode = null }
                        )
                        is LearnStudyMode.JLPTN4 -> JLPTStudyScreen(
                            level = JlptLevel.N4,
                            onBack = { selectedStudyMode = null }
                        )
                        else -> { selectedStudyMode = null }
                    }
                }
            }
        }
    }
}

sealed class LearnStudyMode {
    object Grammar : LearnStudyMode()
    object JLPTN5 : LearnStudyMode()
    object JLPTN4 : LearnStudyMode()
}

@Composable
fun CharacterGrid(
    characters: List<LearnCharacterItem>,
    onCharacterClick: (String) -> Unit
) {
    var selectedKanaTab by remember { mutableStateOf(0) }
    val kanaTabs = listOf("Basic", "Dakuten", "Handakuten", "Yoon")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sub-tabs for kana categories
        ScrollableTabRow(
            selectedTabIndex = selectedKanaTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            kanaTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedKanaTab == index,
                    onClick = { selectedKanaTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Show appropriate table based on selected tab
        when (selectedKanaTab) {
            0 -> {
                // Basic Gojuon
                val basicRows = remember(characters) {
                    organizeCharactersToGojuonRows(characters)
                }
                GojuonTable(
                    rows = basicRows,
                    onCellClick = onCharacterClick,
                    modifier = Modifier.weight(1f),
                    cellSize = 72
                )
            }
            1 -> {
                // Dakuten
                val dakutenRows = remember(characters) {
                    organizeDakutenRows(characters)
                }
                GojuonTable(
                    rows = dakutenRows,
                    onCellClick = onCharacterClick,
                    modifier = Modifier.weight(1f),
                    cellSize = 72
                )
            }
            2 -> {
                // Handakuten
                val handakutenRows = remember(characters) {
                    organizeHandakutenRows(characters)
                }
                GojuonTable(
                    rows = handakutenRows,
                    onCellClick = onCharacterClick,
                    modifier = Modifier.weight(1f),
                    cellSize = 72
                )
            }
            3 -> {
                // Yoon
                val yoonRows = remember(characters) {
                    organizeYoonRows(characters)
                }
                YoonTable(
                    rows = yoonRows,
                    onCellClick = onCharacterClick,
                    modifier = Modifier.weight(1f),
                    cellSize = 72
                )
            }
        }
        
        // Bottom spacing for navigation bar
        Spacer(modifier = Modifier.height(80.dp))
    }
}

/**
 * Organizes a flat list of kana characters into proper gojuon table rows.
 * Groups: Basic (あ行-わ行)
 */
private fun organizeCharactersToGojuonRows(
    characters: List<LearnCharacterItem>
): List<com.cutekana.ui.components.GojuonRow> {
    val charMap = characters.associateBy { it.romaji }
    val rows = mutableListOf<com.cutekana.ui.components.GojuonRow>()
    
    // Helper to create a row from romaji keys
    fun createRow(
        label: String,
        romajiList: List<String>,
        emptyIndices: List<Int> = emptyList()
    ): com.cutekana.ui.components.GojuonRow {
        val cells = romajiList.mapIndexed { index, romaji ->
            if (index in emptyIndices || romaji.isEmpty()) {
                com.cutekana.ui.components.GojuonCell("", "", isEmpty = true)
            } else {
                val char = charMap[romaji]
                if (char != null) {
                    com.cutekana.ui.components.GojuonCell(char.character, char.romaji)
                } else {
                    com.cutekana.ui.components.GojuonCell("", "", isEmpty = true)
                }
            }
        }
        return com.cutekana.ui.components.GojuonRow(label, cells)
    }
    
    // === BASIC GOJUON (あ行 - わ行) ===
    // Vowel row (あ行)
    rows.add(createRow("∅", listOf("a", "i", "u", "e", "o")))
    // K-row (か行)
    rows.add(createRow("k", listOf("ka", "ki", "ku", "ke", "ko")))
    // S-row (さ行)
    rows.add(createRow("s", listOf("sa", "shi", "su", "se", "so")))
    // T-row (た行)
    rows.add(createRow("t", listOf("ta", "chi", "tsu", "te", "to")))
    // N-row (な行)
    rows.add(createRow("n", listOf("na", "ni", "nu", "ne", "no")))
    // H-row (は行)
    rows.add(createRow("h", listOf("ha", "hi", "fu", "he", "ho")))
    // M-row (ま行)
    rows.add(createRow("m", listOf("ma", "mi", "mu", "me", "mo")))
    // Y-row (や行) - yi and ye don't exist
    rows.add(createRow("y", listOf("ya", "", "yu", "", "yo"), emptyIndices = listOf(1, 3)))
    // R-row (ら行)
    rows.add(createRow("r", listOf("ra", "ri", "ru", "re", "ro")))
    // W-row (わ行) - wi and we are archaic, only wa and wo
    rows.add(createRow("w", listOf("wa", "", "", "", "wo"), emptyIndices = listOf(1, 2, 3)))
    // Special N (ん)
    rows.add(createRow("N", listOf("n", "", "", "", ""), emptyIndices = listOf(1, 2, 3, 4)))
    
    return rows
}

/**
 * Organizes dakuten (voiced) characters into table rows.
 */
private fun organizeDakutenRows(
    characters: List<LearnCharacterItem>
): List<com.cutekana.ui.components.GojuonRow> {
    val charMap = characters.associateBy { it.romaji }
    val rows = mutableListOf<com.cutekana.ui.components.GojuonRow>()
    
    fun createRow(
        label: String,
        romajiList: List<String>
    ): com.cutekana.ui.components.GojuonRow {
        val cells = romajiList.map { romaji ->
            val char = charMap[romaji]
            if (char != null) {
                com.cutekana.ui.components.GojuonCell(char.character, char.romaji)
            } else {
                com.cutekana.ui.components.GojuonCell("", "", isEmpty = true)
            }
        }
        return com.cutekana.ui.components.GojuonRow(label, cells)
    }
    
    // === DAKUTEN (voiced sounds) ===
    // G-row (が行) - from K-row
    rows.add(createRow("g", listOf("ga", "gi", "gu", "ge", "go")))
    // Z-row (ざ行) - from S-row
    rows.add(createRow("z", listOf("za", "ji", "zu", "ze", "zo")))
    // D-row (だ行) - from T-row
    rows.add(createRow("d", listOf("da", "ji", "zu", "de", "do")))
    // B-row (ば行) - from H-row
    rows.add(createRow("b", listOf("ba", "bi", "bu", "be", "bo")))
    
    return rows
}

/**
 * Organizes handakuten (semi-voiced) characters into table rows.
 */
private fun organizeHandakutenRows(
    characters: List<LearnCharacterItem>
): List<com.cutekana.ui.components.GojuonRow> {
    val charMap = characters.associateBy { it.romaji }
    val rows = mutableListOf<com.cutekana.ui.components.GojuonRow>()
    
    fun createRow(
        label: String,
        romajiList: List<String>
    ): com.cutekana.ui.components.GojuonRow {
        val cells = romajiList.map { romaji ->
            val char = charMap[romaji]
            if (char != null) {
                com.cutekana.ui.components.GojuonCell(char.character, char.romaji)
            } else {
                com.cutekana.ui.components.GojuonCell("", "", isEmpty = true)
            }
        }
        return com.cutekana.ui.components.GojuonRow(label, cells)
    }
    
    // === HANDAKUTEN (semi-voiced sounds) ===
    // P-row (ぱ行) - from H-row
    rows.add(createRow("p", listOf("pa", "pi", "pu", "pe", "po")))
    
    return rows
}

/**
 * Data class representing a yoon row (3 columns: ya, yu, yo)
 */
data class YoonRow(
    val rowLabel: String,
    val cells: List<com.cutekana.ui.components.GojuonCell>
)

/**
 * Organizes yoon (combination) characters into 3-column table rows.
 */
private fun organizeYoonRows(
    characters: List<LearnCharacterItem>
): List<YoonRow> {
    val charMap = characters.associateBy { it.romaji }
    val rows = mutableListOf<YoonRow>()
    
    fun createRow(
        label: String,
        romajiList: List<String>
    ): YoonRow {
        val cells = romajiList.map { romaji ->
            val char = charMap[romaji]
            if (char != null) {
                com.cutekana.ui.components.GojuonCell(char.character, char.romaji)
            } else {
                com.cutekana.ui.components.GojuonCell("", "", isEmpty = true)
            }
        }
        return YoonRow(label, cells)
    }
    
    // === YOON (combination sounds with small ya/yu/yo) ===
    // Basic consonants + small ya/yu/yo
    rows.add(createRow("ky", listOf("kya", "kyu", "kyo")))
    rows.add(createRow("sh", listOf("sha", "shu", "sho")))
    rows.add(createRow("ch", listOf("cha", "chu", "cho")))
    rows.add(createRow("ny", listOf("nya", "nyu", "nyo")))
    rows.add(createRow("hy", listOf("hya", "hyu", "hyo")))
    rows.add(createRow("my", listOf("mya", "myu", "myo")))
    rows.add(createRow("ry", listOf("rya", "ryu", "ryo")))
    
    // Dakuten + yoon
    rows.add(createRow("gy", listOf("gya", "gyu", "gyo")))
    rows.add(createRow("j", listOf("ja", "ju", "jo")))
    rows.add(createRow("by", listOf("bya", "byu", "byo")))
    
    // Handakuten + yoon
    rows.add(createRow("py", listOf("pya", "pyu", "pyo")))
    
    return rows
}

/**
 * Yoon table with 3-column layout (ya, yu, yo)
 */
@Composable
fun YoonTable(
    rows: List<YoonRow>,
    onCellClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    cellSize: Int = 64
) {
    val cellSizeDp = cellSize.dp
    val headerWidth = 48.dp
    val vowelHeaderHeight = 36.dp
    val spacing = 8.dp
    
    val scrollState = rememberScrollState()
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
            .padding(vertical = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            // Vowel column headers (ya, yu, yo) - now scrolls with content
            Row(
                modifier = Modifier.padding(start = headerWidth + spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                val vowels = listOf("ya", "yu", "yo")
                vowels.forEach { vowel ->
                    Box(
                        modifier = Modifier
                            .size(cellSizeDp, vowelHeaderHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = vowel,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            // Table rows
            rows.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Row header (consonant label)
                    Box(
                        modifier = Modifier
                            .size(headerWidth, cellSizeDp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = row.rowLabel,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                    
                    // Cells for this row (ya, yu, yo)
                    row.cells.forEach { cell ->
                        if (cell.isEmpty) {
                            // Empty cell placeholder
                            Box(
                                modifier = Modifier.size(cellSizeDp)
                            )
                        } else {
                            YoonCellCard(
                                character = cell.character,
                                romaji = cell.romaji,
                                onClick = { onCellClick(cell.character) },
                                modifier = Modifier.size(cellSizeDp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Individual cell card for the yoon table
 */
@Composable
private fun YoonCellCard(
    character: String,
    romaji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "yoon_cell_scale"
    )
    
    Card(
        modifier = modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isPressed) 1.dp else 3.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = character,
                style = KanaDisplayStyle.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = romaji,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun KanjiGrid(
    characters: List<LearnCharacterItem>,
    onCharacterClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characters) { char ->
            KanjiMiniCard(
                kanji = char.character,
                meaning = char.meaning ?: "",
                isLearned = char.masteryLevel >= 4,
                onClick = { onCharacterClick(char.character) }
            )
        }

        // Bottom spacing to account for bottom navigation bar (increased to prevent content from being hidden)
        item {
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun KanjiMiniCard(
    kanji: String,
    meaning: String,
    isLearned: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLearned) 
                Mint.copy(alpha = 0.2f)
            else 
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isLearned) 4.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = kanji,
                style = KanaDisplayStyle.copy(fontSize = 32.sp),
                color = if (isLearned) 
                    MaterialTheme.colorScheme.onSurface 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (isLearned) {
                Text(
                    text = meaning,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
        }
    }
}

// Character Detail Screen
@Composable
fun CharacterDetailScreen(
    character: String,
    onBack: () -> Unit,
    onStartWriting: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isTtsReady by viewModel.isTtsReady.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    
    LaunchedEffect(character) {
        viewModel.loadCharacter(character)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Large character
            Card(
                modifier = Modifier
                    .size(200.dp),
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
                        text = uiState.character,
                        style = KanaDisplayStyle.copy(fontSize = 120.sp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Romaji and info
            Text(
                text = uiState.romaji,
                style = MaterialTheme.typography.headlineMedium
            )
            
            if (uiState.associatedName != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "★ ${uiState.associatedName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = SakuraPink
                )
            }
            
            val meanings = uiState.meanings
            if (meanings != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = meanings.joinToString(", "),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Actions
            CuteButton(
                text = "✍️ Practice Writing",
                onClick = onStartWriting,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            CuteOutlineButton(
                text = if (isSpeaking) "🔊 Speaking..." else "🔊 Listen",
                onClick = { viewModel.playAudio() },
                modifier = Modifier.fillMaxWidth(),
                enabled = isTtsReady && !isSpeaking
            )
        }
    }
}

// UI State
data class LearnUiState(
    val selectedTabIndex: Int = 0,
    val categories: List<LearnCategoryItem> = listOf(
        LearnCategoryItem("Hiragana", CharacterType.HIRAGANA),
        LearnCategoryItem("Katakana", CharacterType.KATAKANA),
        LearnCategoryItem("Kanji N5", CharacterType.KANJI, JlptLevel.N5),
        LearnCategoryItem("Kanji N4", CharacterType.KANJI, JlptLevel.N4),
        LearnCategoryItem("Grammar", CharacterType.GRAMMAR),
        LearnCategoryItem("N5 Study", CharacterType.JLPT_STUDY, JlptLevel.N5),
        LearnCategoryItem("N4 Study", CharacterType.JLPT_STUDY, JlptLevel.N4)
    ),
    val currentCategory: LearnCategory = LearnCategory.Hiragana,
    val hiraganaCharacters: List<LearnCharacterItem> = emptyList(),
    val katakanaCharacters: List<LearnCharacterItem> = emptyList(),
    val n5Kanji: List<LearnCharacterItem> = emptyList(),
    val n4Kanji: List<LearnCharacterItem> = emptyList()
)

data class LearnCategoryItem(
    val title: String,
    val type: CharacterType,
    val level: JlptLevel? = null
)

sealed class LearnCategory {
    object Hiragana : LearnCategory()
    object Katakana : LearnCategory()
    object KanjiN5 : LearnCategory()
    object KanjiN4 : LearnCategory()
    object Grammar : LearnCategory()
    object JLPTN5Study : LearnCategory()
    object JLPTN4Study : LearnCategory()
}

data class LearnCharacterItem(
    val character: String,
    val romaji: String,
    val associatedName: String?,
    val meaning: String?,
    val rarity: Rarity,
    val isUnlocked: Boolean,
    val masteryLevel: Int,
    val type: CharacterType
)

data class CharacterDetailUiState(
    val character: String = "",
    val romaji: String = "",
    val associatedName: String? = null,
    val associatedDescription: String? = null,
    val meanings: List<String>? = null,
    val onYomi: List<String>? = null,
    val kunYomi: List<String>? = null,
    val strokeCount: Int = 0,
    val isLoading: Boolean = false
)