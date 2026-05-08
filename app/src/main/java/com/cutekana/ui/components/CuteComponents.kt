package com.cutekana.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cutekana.ui.theme.CuteKanaTheme
import com.cutekana.ui.theme.KanaDisplayStyle
import com.cutekana.ui.theme.SakuraPink
import com.cutekana.ui.theme.Lavender
import com.cutekana.ui.theme.Mint
import com.cutekana.ui.theme.StarYellow

/**
 * Cute primary button with spring animation
 */
@Composable
fun CuteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "button_scale"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 2.dp else 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = containerColor.copy(alpha = 0.5f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(containerColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled && !isLoading,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
            )
            .padding(horizontal = 32.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 3.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = if (enabled) contentColor else contentColor.copy(alpha = 0.5f)
            )
        }
    }
}

/**
 * Secondary button with outline style
 */
@Composable
fun CuteOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "outline_button_scale"
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            )
            .padding(horizontal = 32.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Character card for displaying kana/kanji with anime association
 */
@Composable
fun CharacterCard(
    character: String,
    romaji: String,
    associatedName: String?,
    rarity: com.cutekana.data.model.Rarity,
    isUnlocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "card_scale"
    )

    val rarityColor = when (rarity) {
        com.cutekana.data.model.Rarity.N -> Color.Gray
        com.cutekana.data.model.Rarity.R -> Color(0xFF4CAF50)
        com.cutekana.data.model.Rarity.SR -> Lavender
        com.cutekana.data.model.Rarity.SSR -> SakuraPink
        com.cutekana.data.model.Rarity.UR -> StarYellow
    }

    // Detect if this is a combined/yoon character for font sizing
    val isCombinedCharacter = character.length > 1 ||
        character.contains(Regex("[ゃゅょャュョ]"))
    // Adjust font size for combined characters to ensure proper display
    val characterFontSize = when {
        !isUnlocked -> 32.sp
        isCombinedCharacter -> 40.sp // Slightly smaller for combined chars
        else -> 48.sp
    }
    // Adjust line height for combined characters to prevent overlap
    val characterLineHeight = when {
        isCombinedCharacter -> characterFontSize * 1.2f
        else -> characterFontSize * 1.1f
    }

    Card(
        modifier = modifier
            .scale(scale)
            .aspectRatio(0.75f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isPressed) 2.dp else 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Rarity badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(rarityColor.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = rarity.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = rarityColor
                )
            }

            // Character with fixed height container to ensure consistent spacing
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = character,
                    style = KanaDisplayStyle.copy(
                        fontSize = characterFontSize,
                        lineHeight = characterLineHeight
                    ),
                    color = if (isUnlocked)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    maxLines = 1
                )
            }

            // Romaji
            Text(
                text = romaji,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )

            // Associated character name
            if (isUnlocked && associatedName != null) {
                Text(
                    text = "★ $associatedName",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1
                )
            } else {
                // Spacer to maintain consistent layout
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Progress indicator with cute styling
 */
@Composable
fun CuteProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "progress_animation"
    )
    
    Box(
        modifier = modifier
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(color, color.copy(alpha = 0.8f))
                    )
                )
        )
    }
}

/**
 * Streak indicator with flame animation
 */
@Composable
fun StreakIndicator(
    streak: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "flame")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flame_scale"
    )
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "🔥",
            modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale),
            fontSize = 20.sp
        )
        Text(
            text = "$streak",
            style = MaterialTheme.typography.titleMedium,
            color = StarYellow
        )
    }
}

/**
 * Orb/currency display
 */
@Composable
fun OrbDisplay(
    count: Int,
    orbType: OrbType,
    modifier: Modifier = Modifier
) {
    val (icon, color) = when (orbType) {
        OrbType.KANA -> "🌸" to SakuraPink
        OrbType.KANJI -> "📜" to Lavender
        OrbType.PREMIUM -> "💎" to Color(0xFF00BCD4)
    }
    
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = icon, fontSize = 16.sp)
        Text(
            text = "× $count",
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

enum class OrbType {
    KANA, KANJI, PREMIUM
}

/**
 * Data class representing a cell in the gojuon table
 */
data class GojuonCell(
    val character: String,
    val romaji: String,
    val isEmpty: Boolean = false
)

/**
 * Data class representing a row in the gojuon table
 */
data class GojuonRow(
    val rowLabel: String, // e.g., "K", "S", "T", "あ行"
    val cells: List<GojuonCell> // 5 cells for a, i, u, e, o
)

/**
 * Traditional Gojuon table layout for hiragana/katakana
 * Displays characters in the standard 5-column (a-i-u-e-o) format
 * with horizontal scrolling support
 */
@Composable
fun GojuonTable(
    rows: List<GojuonRow>,
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
            // Vowel column headers (a, i, u, e, o) - now scrolls with content
            Row(
                modifier = Modifier.padding(start = headerWidth + spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                val vowels = listOf("a", "i", "u", "e", "o")
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
                    
                    // Cells for this row (a, i, u, e, o)
                    row.cells.forEach { cell ->
                        if (cell.isEmpty) {
                            // Empty cell placeholder
                            Box(
                                modifier = Modifier.size(cellSizeDp)
                            )
                        } else {
                            GojuonCellCard(
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
 * Individual cell card for the gojuon table
 */
@Composable
private fun GojuonCellCard(
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
        label = "cell_scale"
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
                style = KanaDisplayStyle.copy(fontSize = 28.sp),
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

/**
 * Achievement badge
 */
@Composable
fun AchievementBadge(
    icon: String,
    title: String,
    isUnlocked: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    if (isUnlocked) StarYellow.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.surfaceVariant
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 32.sp,
                color = if (isUnlocked) StarYellow
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isUnlocked) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}