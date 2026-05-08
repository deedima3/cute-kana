package com.cutekana.ui.screens.collection

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.model.Rarity
import com.cutekana.ui.viewmodel.CollectionViewModel
import com.cutekana.ui.components.*
import com.cutekana.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(
    onBack: () -> Unit,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()
    
    // Handle gacha screen
    if (uiState.showGachaScreen) {
        GachaScreen(
            availableOrbs = uiState.premiumOrbs,
            onSummon = { summonType -> viewModel.summon(summonType) },
            onClose = { viewModel.closeGacha() }
        )
    }
    
    // Handle summon result
    uiState.lastSummonedCard?.let { card ->
        SummonResultDialog(
            card = card,
            onDismiss = { viewModel.clearSummonResult() }
        )
    }
    
    // Handle card detail
    uiState.selectedCard?.let { card ->
        CardDetailDialog(
            card = card,
            onDismiss = { viewModel.closeCardDetail() }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cards") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.openGacha() }) {
                        Icon(Icons.Default.Add, contentDescription = "Summon")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Collection stats
            CollectionStatsBar(
                totalCards = uiState.totalCards,
                completedPercentage = uiState.completionPercentage,
                orbs = uiState.premiumOrbs
            )
            
            // Tab row
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                val tabs = listOf("Kana", "Kanji", "Compounds", "Scenes")
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { 
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }
            
            // Pager content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> KanaCollectionPage(
                        cards = uiState.kanaCards,
                        onCardClick = { viewModel.viewCardDetail(it) }
                    )
                    1 -> KanjiCollectionPage(
                        cards = uiState.kanjiCards,
                        onCardClick = { viewModel.viewCardDetail(it) }
                    )
                    2 -> CompoundsCollectionPage(
                        cards = uiState.compoundCards,
                        onCardClick = { viewModel.viewCardDetail(it) }
                    )
                    3 -> ScenesCollectionPage(
                        cards = uiState.sceneCards,
                        onCardClick = { viewModel.viewCardDetail(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun CollectionStatsBar(
    totalCards: Int,
    completedPercentage: Float,
    orbs: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "$totalCards Cards",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${(completedPercentage * 100).toInt()}% Complete",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            OrbDisplay(
                count = orbs,
                orbType = OrbType.PREMIUM
            )
        }
    }
}

@Composable
fun KanaCollectionPage(
    cards: List<CollectionCardItem>,
    onCardClick: (String) -> Unit
) {
    // Organize cards into traditional gojuon table structure
    val gojuonRows = remember(cards) {
        organizeCardsToGojuonRows(cards)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GojuonTable(
            rows = gojuonRows,
            onCellClick = onCardClick,
            modifier = Modifier.weight(1f),
            cellSize = 72
        )
        
        // Bottom spacing for navigation bar
        Spacer(modifier = Modifier.height(80.dp))
    }
}

/**
 * Organizes a flat list of kana cards into proper gojuon table rows.
 * Groups: Basic (あ行-わ行), Dakuten (が行-ぼ行), Handakuten (ぱ行)
 */
private fun organizeCardsToGojuonRows(
    cards: List<CollectionCardItem>
): List<com.cutekana.ui.components.GojuonRow> {
    // Create map by romaji for lookup
    val cardMap = cards.associateBy { it.romaji }
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
                val card = cardMap[romaji]
                if (card != null) {
                    com.cutekana.ui.components.GojuonCell(card.character, card.romaji)
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
    
    // === DAKUTEN (voiced sounds) ===
    // G-row (が行)
    rows.add(createRow("g", listOf("ga", "gi", "gu", "ge", "go")))
    // Z-row (ざ行) - ji is the second
    rows.add(createRow("z", listOf("za", "ji", "zu", "ze", "zo")))
    // D-row (だ行) - dji and dzu are variations
    rows.add(createRow("d", listOf("da", "ji", "zu", "de", "do")))
    // B-row (ば行)
    rows.add(createRow("b", listOf("ba", "bi", "bu", "be", "bo")))
    
    // === HANDAKUTEN (semi-voiced sounds) ===
    // P-row (ぱ行)
    rows.add(createRow("p", listOf("pa", "pi", "pu", "pe", "po")))
    
    // === YOON (small y combinations) ===
    // These are combination sounds (e.g., kya = ki + small ya)
    
    return rows
}

@Composable
fun KanjiCollectionPage(
    cards: List<CollectionCardItem>,
    onCardClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(cards) { card ->
            KanjiCollectionCard(
                kanji = card.character,
                meaning = card.meaning ?: "",
                jlptLevel = card.jlptLevel ?: "N5",
                isUnlocked = card.isUnlocked,
                onClick = { onCardClick(card.id) }
            )
        }

        // Bottom spacing to account for bottom navigation bar
        item {
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun KanjiCollectionCard(
    kanji: String,
    meaning: String,
    jlptLevel: String,
    isUnlocked: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) 
                MaterialTheme.colorScheme.surface 
            else 
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isUnlocked) 4.dp else 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // JLPT badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        when (jlptLevel) {
                            "N5" -> Mint.copy(alpha = 0.3f)
                            "N4" -> Lavender.copy(alpha = 0.3f)
                            "N3" -> SakuraPink.copy(alpha = 0.3f)
                            else -> StarYellow.copy(alpha = 0.3f)
                        }
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = jlptLevel,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            
            // Kanji
            Text(
                text = kanji,
                style = KanaDisplayStyle.copy(fontSize = 48.sp),
                color = if (isUnlocked) 
                    MaterialTheme.colorScheme.onSurface 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
            
            // Meaning
            if (isUnlocked) {
                Text(
                    text = meaning,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            } else {
                Text(
                    text = "🔒",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun CompoundsCollectionPage(
    cards: List<CollectionCardItem>,
    onCardClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(cards) { card ->
            CompoundCard(
                compound = card.character,
                reading = card.romaji,
                meaning = card.meaning ?: "",
                isUnlocked = card.isUnlocked,
                onClick = { onCardClick(card.id) }
            )
        }

        // Bottom spacing to account for bottom navigation bar
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun CompoundCard(
    compound: String,
    reading: String,
    meaning: String,
    isUnlocked: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) 
                MaterialTheme.colorScheme.surface 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = compound,
                style = KanaDisplayStyle.copy(fontSize = 36.sp),
                color = if (isUnlocked) 
                    MaterialTheme.colorScheme.onSurface 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
            
            if (isUnlocked) {
                Text(
                    text = reading,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = meaning,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ScenesCollectionPage(
    cards: List<CollectionCardItem>,
    onCardClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(cards) { card ->
            SceneCard(
                title = card.character,
                subtitle = card.associatedName ?: "",
                rarity = card.rarity,
                isUnlocked = card.isUnlocked,
                onClick = { onCardClick(card.id) }
            )
        }

        // Bottom spacing to account for bottom navigation bar
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun SceneCard(
    title: String,
    subtitle: String,
    rarity: Rarity,
    isUnlocked: Boolean,
    onClick: () -> Unit
) {
    val cardColor = when (rarity) {
        Rarity.N -> Color.Gray
        Rarity.R -> Color(0xFF4CAF50)
        Rarity.SR -> Lavender
        Rarity.SSR -> SakuraPink
        Rarity.UR -> StarYellow
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) 
                cardColor.copy(alpha = 0.15f)
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Lock indicator only (removed rarity badge)
            if (!isUnlocked) {
                Text(text = "🔒")
            }
            
            Column {
                Text(
                    text = if (isUnlocked) title else "???",
                    style = MaterialTheme.typography.titleSmall
                )
                if (isUnlocked) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Card Detail Dialog
@Composable
fun CardDetailDialog(
    card: CollectionCardItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Character display
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            when {
                                !card.isUnlocked -> MaterialTheme.colorScheme.surfaceVariant
                                card.jlptLevel == "N5" -> Mint.copy(alpha = 0.2f)
                                card.jlptLevel == "N4" -> Lavender.copy(alpha = 0.2f)
                                else -> SakuraPink.copy(alpha = 0.2f)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.character,
                        fontSize = 100.sp,
                        color = if (card.isUnlocked) 
                            MaterialTheme.colorScheme.onSurface 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Character info
                Text(
                    text = card.character,
                    style = MaterialTheme.typography.headlineLarge
                )
                
                if (card.isUnlocked) {
                    Text(
                        text = card.romaji,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    card.meaning?.let { meaning ->
                        Text(
                            text = meaning,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    card.jlptLevel?.let { level ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when (level) {
                                        "N5" -> Mint.copy(alpha = 0.3f)
                                        "N4" -> Lavender.copy(alpha = 0.3f)
                                        "N3" -> SakuraPink.copy(alpha = 0.3f)
                                        else -> StarYellow.copy(alpha = 0.3f)
                                    }
                                )
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "JLPT $level",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                    
                    card.associatedName?.let { name ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Associated: $name",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Text(
                        text = "???",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "🔒 Locked - Summon to unlock!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                CuteButton(
                    text = "Close",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Summon Result Dialog
@Composable
fun SummonResultDialog(
    card: CollectionCardItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = StarYellow.copy(alpha = 0.1f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✨ Summon Success! ✨",
                    style = MaterialTheme.typography.headlineMedium,
                    color = StarYellow
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // New card display
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            when (card.jlptLevel) {
                                "N5" -> Mint.copy(alpha = 0.3f)
                                "N4" -> Lavender.copy(alpha = 0.3f)
                                else -> SakuraPink.copy(alpha = 0.3f)
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.character,
                        fontSize = 80.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = card.character,
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Text(
                    text = card.romaji,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                card.meaning?.let { meaning ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = meaning,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "New card added to your collection!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                CuteButton(
                    text = "Awesome! 🎉",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Gacha/Summon Screen
@Composable
fun GachaScreen(
    availableOrbs: Int,
    onSummon: (Int) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎲 Summon Cards",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Use orbs to summon new character cards!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        // Show available orbs
        Card(
            modifier = Modifier.padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = StarYellow.copy(alpha = 0.2f)
            )
        ) {
            Text(
                text = "💎 $availableOrbs Orbs",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                color = StarYellow
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Summon options
        SummonOption(
            title = "Bronze Summon",
            description = "Kana cards only",
            cost = 100,
            costType = OrbType.KANA,
            available = availableOrbs >= 100,
            onSummon = { onSummon(1) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SummonOption(
            title = "Silver Summon",
            description = "N5 Kanji + Kana",
            cost = 250,
            costType = OrbType.KANJI,
            available = availableOrbs >= 250,
            onSummon = { onSummon(2) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SummonOption(
            title = "Gold Summon",
            description = "N4 Kanji + Compounds",
            cost = 500,
            costType = OrbType.PREMIUM,
            available = availableOrbs >= 500,
            onSummon = { onSummon(3) }
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        CuteOutlineButton(
            text = "Close",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SummonOption(
    title: String,
    description: String,
    cost: Int,
    costType: OrbType,
    available: Boolean,
    onSummon: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            OrbDisplay(count = cost, orbType = costType)
            
            Spacer(modifier = Modifier.width(12.dp))
            
            CuteButton(
                text = "Summon",
                onClick = onSummon,
                enabled = available
            )
        }
    }
}

// UI State
data class CollectionUiState(
    val totalCards: Int = 0,
    val completionPercentage: Float = 0f,
    val premiumOrbs: Int = 0,
    val kanaCards: List<CollectionCardItem> = emptyList(),
    val kanjiCards: List<CollectionCardItem> = emptyList(),
    val compoundCards: List<CollectionCardItem> = emptyList(),
    val sceneCards: List<CollectionCardItem> = emptyList(),
    val showGachaScreen: Boolean = false,
    val selectedCard: CollectionCardItem? = null,
    val lastSummonedCard: CollectionCardItem? = null
)

data class CollectionCardItem(
    val id: String,
    val character: String,
    val romaji: String,
    val meaning: String?,
    val associatedName: String?,
    val rarity: Rarity,
    val isUnlocked: Boolean,
    val jlptLevel: String?
)