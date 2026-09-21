package org.topit.rickmorty.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB9F0B0),
    onPrimaryContainer = Color(0xFF002204),
    secondary = Color(0xFF00838F),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFF7FBF1),
    onBackground = Color(0xFF191D17),
    surface = Color(0xFFF7FBF1),
    onSurface = Color(0xFF191D17),
    surfaceVariant = Color(0xFFDEE5D8),
    onSurfaceVariant = Color(0xFF424940),
    surfaceContainer = Color(0xFFEBF0E5),
    outline = Color(0xFF72796F),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF97F28B),
    onPrimary = Color(0xFF00390A),
    primaryContainer = Color(0xFF0F5316),
    onPrimaryContainer = Color(0xFFB9F0B0),
    secondary = Color(0xFF7FD8E3),
    onSecondary = Color(0xFF00363C),
    background = Color(0xFF10140F),
    onBackground = Color(0xFFE0E4DA),
    surface = Color(0xFF10140F),
    onSurface = Color(0xFFE0E4DA),
    surfaceVariant = Color(0xFF424940),
    onSurfaceVariant = Color(0xFFC2C9BD),
    surfaceContainer = Color(0xFF1D211B),
    outline = Color(0xFF8C9388),
)

@Composable
fun AppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
