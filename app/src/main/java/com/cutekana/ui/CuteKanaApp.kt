package com.cutekana.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import android.animation.Animator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cutekana.R
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
import com.cutekana.ui.theme.CuteKanaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.padding

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CuteKanaTheme {
                CuteKanaAppWithSplash()
            }
        }

        // Animate the splash screen exit
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                "translationY",
                0f,
                -splashScreenView.view.height.toFloat()
            )

            slideUp.apply {
                interpolator = AnticipateInterpolator()
                duration = 300L
                doOnEnd { splashScreenView.remove() }
                start()
            }
        }
    }
}

@Composable
fun CuteKanaAppWithSplash() {
    var showSplash by remember { mutableStateOf(true) }

    // Auto-hide splash after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false
    }

    AnimatedVisibility(
        visible = showSplash,
        enter = fadeIn(),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        SplashScreen()
    }

    AnimatedVisibility(
        visible = !showSplash,
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut()
    ) {
        CuteKanaApp()
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val packageInfo = remember {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }
    val versionName = packageInfo.versionName ?: "1.0.0"

    // Pastel gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFBF7), // soft-white
            Color(0xFFE6E6FA), // lavender
            Color(0xFFFFB7C5)  // sakura-pink
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Logo with glow effect
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFB7C5).copy(alpha = 0.6f),
                                Color(0xFFB5EAD7).copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "Cute Kana Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // App name
            Text(
                text = "Cute Kana",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9AAE), // sakura-dark
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Learn Japanese the Kawaii Way 🌸",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8B7B8B) // warm-gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Version badge
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color(0xFFFFB7C5).copy(alpha = 0.3f),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = Color(0xFFFFB7C5)
                )
            ) {
                Text(
                    text = "Version $versionName",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4A4A4A) // dark-text
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Loading indicator
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = Color(0xFFFF9AAE),
                strokeWidth = 3.dp
            )
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