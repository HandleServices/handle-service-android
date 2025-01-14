package br.com.handleservice.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4CA3FF),
    secondary = Color(0xFFFF5252),
    tertiary = Color(0xFF7C7C8A),
    background = Color(0xFF121212),
    surface = Color(0xFF1C1C1E),
    onPrimary = Color.Black,
    onSecondary = Color(0xFFE0E0E0), // sla
    onTertiary = Color(0xFF7C7C8A),
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFF7C7C8A),
    outline = Color(0xFFC5CCD9)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1A73E8),
    secondary = Color(0xFFFF5252),
    tertiary = Color(0xFF7C7C8A),
    background = Color(0xFF121212),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color(0xFF323238),
    onBackground = Color(0xFF121212),
    onSurface = Color(0xFF323238),
    outline = Color(0xFFC5CCD9)
)

@Composable
fun HandleServiceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
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

