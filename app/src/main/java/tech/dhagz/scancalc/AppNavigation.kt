package tech.dhagz.scancalc

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tech.dhagz.scancalc.features.list.ScanListScreen
import tech.dhagz.scancalc.features.scan.CameraScanScreen

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-29 1:01 AM
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.ScanList.route
    ) {

        composable(AppScreen.ScanList.route) {
            ScanListScreen(
                navController = navController
            )
        }

        composable(AppScreen.Camera.route) {
            CameraScanScreen(
                navController = navController
            )
        }
    }

}