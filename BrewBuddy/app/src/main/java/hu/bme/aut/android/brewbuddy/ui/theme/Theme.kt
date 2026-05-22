package hu.bme.aut.android.brewbuddy.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme


private val DarkColors = darkColorScheme(

    primary = BeerGold,

    secondary = HopGreen,

    tertiary = BreweryBrown,

    background = DarkBackground,

    surface = DarkSurface
)

private val LightColors = lightColorScheme(

    primary = BeerAmber,

    secondary = HopGreen,

    tertiary = BreweryBrown,

    background = LightBackground
)
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun BrewBuddyTheme(

    darkTheme: Boolean =
        isSystemInDarkTheme(),

    content: @Composable () -> Unit
) {

    val colors =
        if (darkTheme)
            DarkColors
        else
            LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}