package tech.dhagz.scancalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import tech.dhagz.scancalc.base.ui.theme.ScanCalcTheme
import tech.dhagz.scancalc.features.list.ScanListScreen
import tech.dhagz.scancalc.features.scan.CameraScanScreen
import tech.dhagz.scancalc.features.scan.ImagePickerScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScanCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScanListScreen()
                }
            }
        }
    }
}
