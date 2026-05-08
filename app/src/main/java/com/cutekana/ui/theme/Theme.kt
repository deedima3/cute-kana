package com.cutekana.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Cute Kana - Kawaii Color Palette
// Inspired by anime aesthetics and Japanese pastel culture

// Light Theme Colors
val SakuraPink = Color(0xFFFF85A2)
val SakuraPinkLight = Color(0xFFFFB3C1)
val SakuraPinkDark = Color(0xFFFF6B9D)

val Lavender = Color(0xFFB8B5FF)
val LavenderLight = Color(0xFFD4D2FF)
val LavenderDark = Color(0xFF9D98FF)

val Mint = Color(0xFF95E1D3)
val MintLight = Color(0xFFB8E9DF)
val MintDark = Color(0xFF7FD4C3)

val Cream = Color(0xFFFFF5F7)
val StarYellow = Color(0xFFFFD93D)
val BabyBlue = Color(0xFFABE9FF)
val Peach = Color(0xFFFFD6C2)

// Dark Theme Colors
val DarkBackground = Color(0xFF1A1A2E)
val DarkSurface = Color(0xFF252542)
val DarkSurfaceVariant = Color(0xFF2D2D50)
val OnDarkSurface = Color(0xFFE8E8F0)
val OnDarkSurfaceVariant = Color(0xFFB8B8D0)

val SakuraPinkDarkTheme = Color(0xFFFF6B9D)
val LavenderDarkTheme = Color(0xFF9D98FF)
val MintDarkTheme = Color(0xFF7FD4C3)

private val LightColorScheme = lightColorScheme(
    primary = SakuraPink,
    onPrimary = Color.White,
    primaryContainer = SakuraPinkLight,
    onPrimaryContainer = Color(0xFF4A0018),
    
    secondary = Lavender,
    onSecondary = Color(0xFF1A1B4B),
    secondaryContainer = LavenderLight,
    onSecondaryContainer = Color(0xFF1A1B4B),
    
    tertiary = Mint,
    onTertiary = Color(0xFF003829),
    tertiaryContainer = MintLight,
    onTertiaryContainer = Color(0xFF003829),
    
    background = Cream,
    onBackground = Color(0xFF4A4A6A),
    
    surface = Color.White,
    onSurface = Color(0xFF4A4A6A),
    surfaceVariant = Color(0xFFF0F0F8),
    onSurfaceVariant = Color(0xFF6B6B8A),
    
    error = Color(0xFFFF6B6B),
    onError = Color.White,
    errorContainer = Color(0xFFFFDEDE),
    onErrorContainer = Color(0xFF410002),
    
    outline = Color(0xFFD0D0E0),
    outlineVariant = Color(0xFFE0E0F0),
    
    scrim = Color(0x99000000),
    
    inverseSurface = DarkSurface,
    inverseOnSurface = OnDarkSurface,
    inversePrimary = SakuraPinkDark
)

private val DarkColorScheme = darkColorScheme(
    primary = SakuraPinkDarkTheme,
    onPrimary = Color(0xFF4A0018),
    primaryContainer = Color(0xFF7A2E4E),
    onPrimaryContainer = Color(0xFFFFD9E2),
    
    secondary = LavenderDarkTheme,
    onSecondary = Color(0xFF1A1B4B),
    secondaryContainer = Color(0xFF2E2F5B),
    onSecondaryContainer = Color(0xFFE2E0FF),
    
    tertiary = MintDarkTheme,
    onTertiary = Color(0xFF003829),
    tertiaryContainer = Color(0xFF1A4D3D),
    onTertiaryContainer = Color(0xFFB8E9DF),
    
    background = DarkBackground,
    onBackground = OnDarkSurface,
    
    surface = DarkSurface,
    onSurface = OnDarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSurfaceVariant,
    
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    
    outline = Color(0xFF505070),
    outlineVariant = Color(0xFF404060),
    
    scrim = Color(0x99000000),
    
    inverseSurface = Color.White,
    inverseOnSurface = Color(0xFF4A4A6A),
    inversePrimary = SakuraPink
)

@Composable
fun CuteKanaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled for consistent branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CuteKanaTypography,
        content = content
    )
}