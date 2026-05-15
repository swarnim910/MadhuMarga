package com.madhumarga.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = HoneyAmber,
    onPrimary = Color.White,
    primaryContainer = HoneyLight,
    onPrimaryContainer = CombBrown,
    secondary = CombBrown,
    onSecondary = Color.White,
    secondaryContainer = CombBrownLight.copy(alpha = 0.3f),
    onSecondaryContainer = CombBrown,
    tertiary = FloraGreen,
    onTertiary = Color.White,
    tertiaryContainer = FloraGreenPale,
    onTertiaryContainer = FloraGreen,
    error = AlertRed,
    onError = Color.White,
    errorContainer = AlertRedLight,
    onErrorContainer = AlertRed,
    background = SurfaceLight,
    onBackground = BeeBlack,
    surface = SurfaceLight,
    onSurface = BeeBlack,
    surfaceVariant = HoneyCream,
    onSurfaceVariant = CombBrown,
    outline = CombBrownLight
)

private val DarkColorScheme = darkColorScheme(
    primary = HoneyGold,
    onPrimary = CombBrown,
    primaryContainer = HoneyDark,
    onPrimaryContainer = HoneyLight,
    secondary = CombBrownLight,
    onSecondary = Color.White,
    secondaryContainer = CombBrown,
    onSecondaryContainer = HoneyLight,
    tertiary = FloraGreenLight,
    onTertiary = Color.White,
    tertiaryContainer = FloraGreen,
    onTertiaryContainer = FloraGreenPale,
    error = Color(0xFFFF6B6B),
    onError = Color.White,
    errorContainer = AlertRed,
    onErrorContainer = AlertRedLight,
    background = SurfaceDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = CardDark,
    onSurfaceVariant = HoneyLight,
    outline = CombBrownLight
)

@Composable
fun MadhuMargaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MadhuMargaTypography,
        content = content
    )
}
