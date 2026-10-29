package com.example.foodapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val FoodPrimary = Color(0xFFFF5722) // Energetic Appetizing Orange
val FoodSecondary = Color(0xFFFFC107) // Warm Golden Amber
val FoodBackground = Color(0xFFFBFBFB)
val FoodSurface = Color(0xFFFFFFFF)
val FoodDark = Color(0xFF1E1E1E)

private val DarkColorScheme = darkColorScheme(
    primary = FoodPrimary,
    secondary = FoodSecondary,
    background = FoodDark,
    surface = Color(0xFF2C2C2C)
)

private val LightColorScheme = lightColorScheme(
    primary = FoodPrimary,
    secondary = FoodSecondary,
    background = FoodBackground,
    surface = FoodSurface
)

@Composable
fun FoodAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(colorScheme = colors, content = content)
}
