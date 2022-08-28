package tech.dhagz.scancalc.base.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import tech.dhagz.scancalc.R

/**
 * The app theme
 */
@Composable
fun ScanCalcTheme(content: @Composable () -> Unit) {
    val colorPalette = lightColors(
        primary = colorResource(id = R.color.primary),
        primaryVariant = colorResource(id = R.color.primaryVariant),
        secondary = colorResource(id = R.color.secondary)
    )

    MaterialTheme(
        colors = colorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}