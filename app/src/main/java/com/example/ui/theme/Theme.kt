package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// HighClue Grey Theme (Neutral Grey BG + Pure White Font)
val HighClueGreyColorScheme = darkColorScheme(
    primary = HighClueIndigoLight,
    onPrimary = HighClueWhite,
    primaryContainer = HighClueIndigoContainer,
    onPrimaryContainer = HighClueWhite,
    secondary = HighClueTealLight,
    onSecondary = HighClueWhite,
    secondaryContainer = HighClueTealContainer,
    onSecondaryContainer = HighClueWhite,
    tertiary = HighClueAmberLight,
    onTertiary = HighClueWhite,
    background = HighClueGreyBg,
    surface = HighClueGreySurface,
    surfaceVariant = HighClueGreySurfaceVariant,
    onBackground = HighClueWhite,
    onSurface = HighClueWhite,
    onSurfaceVariant = HighClueWhiteOff,
    outline = HighClueGreyBorder,
    outlineVariant = HighClueGreySurfaceElevated
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    // Fixed to Grey Background and White Font as requested
    MaterialTheme(
        colorScheme = HighClueGreyColorScheme,
        typography = Typography,
        content = content
    )
}

