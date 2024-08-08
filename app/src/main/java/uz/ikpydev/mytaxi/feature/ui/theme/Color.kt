package uz.ikpydev.mytaxi.feature.ui.theme

import androidx.compose.ui.graphics.Color

val ButtonGreen = Color(0xFF80ED99)
val ButtonRed = Color(0xFFE05656)
val BlackPrimary = Color(0xFF121212)
val Blue = Color(0xFF4A91FB)

sealed class ThemeColors(
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
    val outlineSecondary: Color
) {
    data object Dark: ThemeColors(
        backgroundPrimary = Color(0xFF121212),
        backgroundSecondary = Color(0xFF252525),
        contentPrimary = Color(0xFFFFFFFF),
        contentSecondary = Color(0xFF999999),
        contentTertiary = Color(0xFF666666),
        outlineSecondary = Color(0xFF3E3E3E)
    )

    data object Light: ThemeColors(
        backgroundPrimary = Color(0xFFFFFFFF),
        backgroundSecondary = Color(0xFFF5F6F9),
        contentPrimary = Color(0xFF121212),
        contentSecondary = Color(0xFF818AB0),
        contentTertiary = Color(0xFFBBC2D5),
        outlineSecondary = Color(0xFFE8EAF1)
    )
}