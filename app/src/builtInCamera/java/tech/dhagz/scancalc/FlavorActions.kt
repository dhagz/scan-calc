package tech.dhagz.scancalc

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

/**
 * Return true if navigation is being done. Otherwise, false.
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-29 12:31 AM
 */
fun mainFabNavigation(
    navController: NavController? = null,
    context: Context,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    intentLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) -> {
            permissionGranted(
                navController = navController,
                intentLauncher = intentLauncher
            )
        }
        else -> {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

/**
 * Called when permission is granted
 */
fun permissionGranted(
    navController: NavController? = null,
    intentLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    navController?.navigate(AppScreen.Camera.route)
}

/**
 * Called when permission is denied
 */
fun permissionDenied(context: Context) {
    Toast.makeText(context, R.string.image_picker_permission_denied_msg, Toast.LENGTH_SHORT).show()
}