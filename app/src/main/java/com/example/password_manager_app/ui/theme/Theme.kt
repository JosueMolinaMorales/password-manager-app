package com.example.password_manager_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Opal,
//    primaryVariant = Purple700,
//    secondary = Teal200,
    background = Charcoal,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
    onBackground = LavenderBlush
//    onSurface = Color.Black,
)


@Composable
fun PasswordmanagerappTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}