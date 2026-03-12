package com.lautung.smart.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Material3 尺寸规范
 */
object MaterialDimens {
    val extraSmall: Dp = 4.dp
    val small: Dp = 8.dp
    val medium: Dp = 16.dp
    val large: Dp = 24.dp
    val extraLarge: Dp = 32.dp

    val iconSmall: Dp = 20.dp
    val iconLarge: Dp = 40.dp
}

private val DarkColorScheme = darkColorScheme(
    primary = SmartBlue,
    secondary = SmartPurple,
    tertiary = SmartGreen,
    background = SmartBlack,
    surface = SmartBlack,
    onPrimary = SmartWhite,
    onSecondary = SmartWhite,
    onTertiary = SmartWhite,
    onBackground = SmartWhite,
    onSurface = SmartWhite,
    surfaceVariant = SmartDarkPurple,
    outline = SmartGray600
)

private val LightColorScheme = lightColorScheme(
    primary = SmartNavyBlue,
    secondary = SmartDarkPurple2,
    tertiary = SmartDarkGreen,
    background = SmartWhite,
    surface = SmartWhite,
    onPrimary = SmartWhite,
    onSecondary = SmartWhite,
    onTertiary = SmartWhite,
    onBackground = SmartGray800,
    onSurface = SmartGray800,
    surfaceVariant = SmartGray200,  // 边框颜色
    outline = SmartGray200  // 边框颜色
)

@Composable
fun SmartTheme(
    themeManager: ThemeManager,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val themeMode = themeManager.themeMode.collectAsState()
    val systemDarkTheme = isSystemInDarkTheme()
    val darkTheme = when (themeMode.value) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> systemDarkTheme
    }

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