package de.kataia.app.ui.theme

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
    primary = PrimaryDark,
    secondary = SecondarySage,
    tertiary = PrimaryWarm,
    background = OnBackgroundDark, // Dunkler Hintergrund für Dark Mode
    surface = OnBackgroundDark,
    onPrimary = BackgroundWarm,
    onBackground = BackgroundWarm,
    onSurface = BackgroundWarm
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryWarm,
    secondary = SecondarySage,
    background = BackgroundWarm,
    surface = SurfaceWarm,
    onPrimary = OnBackgroundDark,
    onBackground = OnBackgroundDark,
    onSurface = OnBackgroundDark
)

@Composable
fun KataiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
