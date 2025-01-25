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
    tertiary = Color(0xFF8D8D99),
    background = Color.Black,
    surface = Color(0xFF2C2C2E),
    onPrimary = Color.Black,
    onSecondary = Color(0xFFE0E0E0), // sla
    onTertiary = Color(0xFF7C7C8A),
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFF7C7C8A),
    onSurfaceVariant = Color(0xFF7C7C8A),
    outline = Color(0xFFC5CCD9),

    primaryContainer = Color(0xFF2C2C2E)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1A73E8),
    secondary = Color(0xFFFF5252),
    tertiary = Color(0xFF8D8D99),
    background = Color(0xFFF4F5F8),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color(0xFF323238),
    onBackground = Color(0xFF121212),
    onSurface = Color(0xFF323238),
    onSurfaceVariant = Color(0xFF7C7C8A),
    onPrimaryContainer = Color(0xFFBAD5F8),
    onTertiaryContainer = Color(0xFFE1E1E6),
    outline = Color(0xFFC5CCD9),
    surfaceContainerHigh = Color(0xFFD9D9D9),

    primaryContainer = Color(0xFFCEE1F2)
)

@Composable
fun HandleServiceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

