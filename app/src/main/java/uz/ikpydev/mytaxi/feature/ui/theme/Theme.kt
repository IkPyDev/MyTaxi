package uz.ikpydev.mytaxi.feature.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ThemeColors.Dark.contentPrimary,
    secondary = ThemeColors.Dark.contentSecondary,
    tertiary = ThemeColors.Dark.contentTertiary,
    onPrimary = ThemeColors.Dark.contentPrimary,
    onSecondary = ThemeColors.Dark.contentSecondary,
    onTertiary = ThemeColors.Dark.contentTertiary,
    primaryContainer = ThemeColors.Dark.backgroundSecondary,
    background = ThemeColors.Dark.backgroundPrimary,
    surface = ThemeColors.Dark.backgroundSecondary,
    onSurface = ThemeColors.Dark.outlineSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = ThemeColors.Light.contentPrimary,
    secondary = ThemeColors.Light.contentSecondary,
    tertiary = ThemeColors.Light.contentTertiary,
    onPrimary = ThemeColors.Light.contentPrimary,
    onSecondary = ThemeColors.Light.contentSecondary,
    onTertiary = ThemeColors.Light.contentTertiary,
    primaryContainer = ThemeColors.Light.backgroundSecondary,
    background = ThemeColors.Light.backgroundPrimary,
    surface = ThemeColors.Light.backgroundSecondary,
    onSurface = ThemeColors.Light.outlineSecondary
)

@Composable
fun MyTaxiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}