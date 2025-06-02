package com.example.literalkids.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5AD8FF),
    onPrimary = Color.White,
    secondary = Color(0xFFDE99FF),
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color(0xFF0A617A),
    surface = Color.White,
    onSurface = Color(0xFF0A617A),
    error = Color(0xFFE53935),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5AD8FF),
    onPrimary = Color.Black,
    secondary = Color(0xFFDE99FF),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    error = Color(0xFFE53935),
    onError = Color.Black
)

@Composable
fun LiteralkidsTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}