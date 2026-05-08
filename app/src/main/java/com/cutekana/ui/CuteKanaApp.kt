package com.cutekana.ui
import com.cutekana.R

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cutekana.ui.screens.collection.CollectionScreen
import com.cutekana.ui.screens.home.HomeScreen
import com.cutekana.ui.screens.learn.CharacterDetailScreen
import com.cutekana.ui.screens.learn.LearnScreen
import com.cutekana.ui.screens.mocktest.MockTestScreen
import com.cutekana.ui.screens.play.PlayScreen
import com.cutekana.ui.screens.play.MatchPairsGame
import com.cutekana.ui.screens.play.SpeedQuizGame
import com.cutekana.ui.screens.play.RadicalBuilderGame
import com.cutekana.ui.screens.play.KanjiStoryGame
import com.cutekana.ui.screens.play.KanaGuessGame
import com.cutekana.ui.screens.play.WordGuessGame
import com.cutekana.ui.screens.writing.WritingScreen
import com.cutekana.ui.theme.CuteKanaTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Switch from splash theme to main app theme
        setTheme(R.style.Theme_CuteKana)
        
        enableEdgeToEdge()
        setContent {
            CuteKanaTheme {
                CuteKanaApp()
            }
        }
    }
}

@Composable
fun CuteKanaApp() {
    val navController = rememberNavController()
    
    // Screens that show bottom bar
    val bottomNavScreens = listOf(
        Screen.Home,
        Screen.Learn,
        Screen.Play,
        Screen.MockTests,
        Screen.Collection
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Check if current route should show bottom bar
    val showBottomBar = currentDestination?.route in bottomNavScreens.map { it.route }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavScreens.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { 
                            it.route == screen.route 
                        } == true
                        
                        NavigationBarItem(
                            icon = { 
                                Icon(
                                    if (selected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title) },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToLearn = { navController.navigate(Screen.Learn.route) },
                    onNavigateToWriting = { navController.navigate("writing") },
                    onNavigateToPlay = { navController.navigate(Screen.Play.route) },
                    onNavigateToMockTests = { navController.navigate(Screen.MockTests.route) },
                    onNavigateToCollection = { navController.navigate(Screen.Collection.route) }
                )
            }
            
            composable(Screen.Learn.route) {
                LearnScreen(
                    onBack = { navController.navigateUp() },
                    onNavigateToCharacter = { char ->
                        navController.navigate("character/$char")
                    }
                )
            }
            
            composable("character/{char}") { backStackEntry ->
                val char = backStackEntry.arguments?.getString("char") ?: "あ"
                CharacterDetailScreen(
                    character = char,
                    onBack = { navController.navigateUp() },
                    onStartWriting = { navController.navigate("writing") }
                )
            }
            
            composable("writing") {
                WritingScreen(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable(Screen.Play.route) {
                PlayScreen(
                    onBack = { navController.navigateUp() },
                    onNavigateToRadicalBuilder = { navController.navigate("game/radical_builder") },
                    onNavigateToKanjiStory = { navController.navigate("game/kanji_story") },
                    onNavigateToMatchPairs = { navController.navigate("game/match_pairs") },
                    onNavigateToSpeedQuiz = { navController.navigate("game/speed_quiz") },
                    onNavigateToKanaGuess = { navController.navigate("game/kana_guess") },
                    onNavigateToWordGuess = { navController.navigate("game/word_guess") }
                )
            }
            
            composable("game/word_guess") {
                WordGuessGame(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable("game/radical_builder") {
                RadicalBuilderGame(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable("game/kanji_story") {
                KanjiStoryGame(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable("game/match_pairs") {
                MatchPairsGame(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable("game/speed_quiz") {
                SpeedQuizGame(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable("game/kana_guess") {
                KanaGuessGame(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable(Screen.MockTests.route) {
                MockTestScreen(
                    onBack = { navController.navigateUp() }
                )
            }
            
            composable(Screen.Collection.route) {
                CollectionScreen(
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Home : Screen(
        "home",
        "Home",
        Icons.Default.Home,
        Icons.Default.Home
    )

    object Learn : Screen(
        "learn",
        "Learn",
        Icons.Default.Menu,
        Icons.Default.Menu
    )

    object Play : Screen(
        "play",
        "Play",
        Icons.Default.PlayArrow,
        Icons.Default.PlayArrow
    )

    object MockTests : Screen(
        "mock_tests",
        "Tests",
        Icons.Default.Edit,
        Icons.Default.Edit
    )

    object Collection : Screen(
        "collection",
        "Cards",
        Icons.Default.Star,
        Icons.Default.Star
    )
}