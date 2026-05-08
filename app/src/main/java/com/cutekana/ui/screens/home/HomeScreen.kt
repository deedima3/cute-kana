package com.cutekana.ui.screens.home

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cutekana.data.model.JlptLevel
import com.cutekana.ui.viewmodel.HomeViewModel
import com.cutekana.ui.components.*
import com.cutekana.ui.theme.*
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onNavigateToLearn: () -> Unit,
    onNavigateToWriting: () -> Unit,
    onNavigateToPlay: () -> Unit,
    onNavigateToMockTests: () -> Unit,
    onNavigateToCollection: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            HomeTopBar(
                streak = uiState.currentStreak,
                kanaOrbs = uiState.kanaOrbs,
                kanjiOrbs = uiState.kanjiOrbs
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
            // Daily Goal Card
            item {
                DailyGoalCard(
                    progress = uiState.dailyProgress,
                    goalMinutes = uiState.dailyGoalMinutes,
                    completedMinutes = uiState.completedMinutes,
                    onStartLearning = onNavigateToLearn
                )
            }
            
            // Continue Learning Section
            item {
                Text(
                    text = "Continue Learning",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            item {
                ContinueLearningCard(
                    currentLevel = uiState.currentLevel,
                    currentLesson = uiState.currentLesson,
                    progress = uiState.lessonProgress,
                    onContinue = onNavigateToLearn
                )
            }
            
            // Quick Actions Grid
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            item {
                QuickActionsGrid(
                    onWriting = onNavigateToWriting,
                    onPlay = onNavigateToPlay,
                    onMockTest = onNavigateToMockTests,
                    onCollection = onNavigateToCollection
                )
            }
            
            // Study Stats
            item {
                StudyStatsCard(
                    totalLearned = uiState.totalCharactersLearned,
                    totalKanji = uiState.totalKanjiLearned,
                    studyStreak = uiState.currentStreak,
                    totalTime = uiState.totalStudyTimeMinutes
                )
            }
            
            // JLPT Progress
            item {
                JLPTProgressCard(
                    n5Progress = uiState.n5Progress,
                    n4Progress = uiState.n4Progress,
                    onViewDetails = onNavigateToMockTests
                )
            }
            
            // Recent Achievements
            if (uiState.recentAchievements.isNotEmpty()) {
                item {
                    Text(
                        text = "Recent Achievements",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                items(uiState.recentAchievements.size) { index ->
                    val achievement = uiState.recentAchievements[index]
                    AchievementRow(
                        icon = achievement.icon,
                        title = achievement.title,
                        description = achievement.description,
                        unlockedDate = achievement.unlockedDate
                    )
                }
            }
            
            // Bottom spacing to account for bottom navigation bar
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    streak: Int,
    kanaOrbs: Int,
    kanjiOrbs: Int
) {
    TopAppBar(
        title = {
            Text(
                text = "Cute Kana",
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            StreakIndicator(streak = streak, modifier = Modifier.padding(end = 8.dp))
            OrbDisplay(
                count = kanaOrbs,
                orbType = OrbType.KANA,
                modifier = Modifier.padding(end = 8.dp)
            )
            OrbDisplay(
                count = kanjiOrbs,
                orbType = OrbType.KANJI,
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DailyGoalCard(
    progress: Float,
    goalMinutes: Int,
    completedMinutes: Int,
    onStartLearning: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onStartLearning),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Daily Goal",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "$completedMinutes / $goalMinutes min",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                if (progress >= 1f) {
                    Text(
                        text = "🌟 Complete!",
                        style = MaterialTheme.typography.labelLarge,
                        color = StarYellow
                    )
                } else {
                    CuteButton(
                        text = "Start",
                        onClick = onStartLearning,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            CuteProgressBar(
                progress = progress,
                color = if (progress >= 1f) Mint else MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ContinueLearningCard(
    currentLevel: String,
    currentLesson: String,
    progress: Float,
    onContinue: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onContinue),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Progress indicator
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(SakuraPink, Lavender)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = currentLevel,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = currentLesson,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = SakuraPink,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Continue",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun QuickActionsGrid(
    onWriting: () -> Unit,
    onPlay: () -> Unit,
    onMockTest: () -> Unit,
    onCollection: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                icon = "✍️",
                title = "Writing",
                subtitle = "Practice strokes",
                color = Mint,
                onClick = onWriting,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = "🎮",
                title = "Play",
                subtitle = "Mini games",
                color = Lavender,
                onClick = onPlay,
                modifier = Modifier.weight(1f)
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                icon = "📝",
                title = "Mock Tests",
                subtitle = "JLPT practice",
                color = StarYellow,
                onClick = onMockTest,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = "🏆",
                title = "Cards",
                subtitle = "View collection",
                color = SakuraPink,
                onClick = onCollection,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun QuickActionCard(
    icon: String,
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1.5f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = icon,
                fontSize = 28.sp
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = color
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun StudyStatsCard(
    totalLearned: Int,
    totalKanji: Int,
    studyStreak: Int,
    totalTime: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Study Stats",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    value = "$totalLearned",
                    label = "Characters",
                    icon = "🈳"
                )
                StatItem(
                    value = "$totalKanji",
                    label = "Kanji",
                    icon = "🈴"
                )
                StatItem(
                    value = "$studyStreak",
                    label = "Day Streak",
                    icon = "🔥"
                )
                StatItem(
                    value = "${totalTime / 60}h",
                    label = "Study Time",
                    icon = "⏱️"
                )
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    icon: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun JLPTProgressCard(
    n5Progress: Float,
    n4Progress: Float,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onViewDetails),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "JLPT Progress",
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "View Details",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // N5 Progress
            JLPTLevelRow(
                level = "N5",
                description = "Beginner",
                progress = n5Progress,
                color = Mint
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // N4 Progress
            JLPTLevelRow(
                level = "N4",
                description = "Elementary",
                progress = n4Progress,
                color = Lavender
            )
        }
    }
}

@Composable
fun JLPTLevelRow(
    level: String,
    description: String,
    progress: Float,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Level badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color.copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = level,
                style = MaterialTheme.typography.labelLarge,
                color = color
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = color,
                trackColor = color.copy(alpha = 0.2f)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

@Composable
fun AchievementRow(
    icon: String,
    title: String,
    description: String,
    unlockedDate: Long
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(StarYellow.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 24.sp)
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// UI State
data class HomeUiState(
    val currentStreak: Int = 0,
    val kanaOrbs: Int = 0,
    val kanjiOrbs: Int = 0,
    val dailyProgress: Float = 0f,
    val dailyGoalMinutes: Int = 15,
    val completedMinutes: Int = 0,
    val currentLevel: String = "Hiragana",
    val currentLesson: String = "Lesson 1: あ-row",
    val lessonProgress: Float = 0.35f,
    val totalCharactersLearned: Int = 0,
    val totalKanjiLearned: Int = 0,
    val totalStudyTimeMinutes: Int = 0,
    val n5Progress: Float = 0.25f,
    val n4Progress: Float = 0f,
    val recentAchievements: List<AchievementUiModel> = emptyList()
)

data class AchievementUiModel(
    val icon: String,
    val title: String,
    val description: String,
    val unlockedDate: Long
)