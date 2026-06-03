package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.data.local.NeoChemSettings

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6EBE94),     // Soft Sage Green
    secondary = Color(0xFF7CB2CD),   // Muted Soft Blue
    tertiary = Color(0xFF5BA381),    // Mint Green
    background = Color(0xFF000000),  // OLED Pure Black
    surface = Color(0xFF0D1210),     // Soft Forest Obsidian / Deep blackish green slate
    onPrimary = Color(0xFF040A06),
    onSecondary = Color(0xFF081116),
    onTertiary = Color(0xFF020704),
    onBackground = Color(0xFFECF3EE),// Soft muted greenish light gray for high-class readability
    onSurface = Color(0xFFECF3EE)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2C6B4E),     // Deep academic Sage Green
    secondary = Color(0xFF427AA1),   // Trustworthy Soft Blue
    tertiary = Color(0xFF4EA175),    // Calm Mint Green
    background = Color(0xFFF4F7F5),  // Warm Alabaster White with sage hint
    surface = Color(0xFFFFFFFF),     // Clean notebook paper white
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1E2F26),// Deep Spruce / Slate for beautiful academic book contrast
    onSurface = Color(0xFF1E2F26)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Forced to false to maintain the beautiful requested classroom aesthetic
    content: @Composable () -> Unit
) {
    val themeModeSetting by NeoChemSettings.themeMode.collectAsState()
    val useDarkTheme = when (themeModeSetting) {
        0 -> false // Light
        1 -> true  // Dark
        else -> darkTheme // System setting
    }

    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
