package tech.dhagz.scancalc.features.list

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.dhagz.scancalc.R
import tech.dhagz.scancalc.base.ui.LoadingDialog
import tech.dhagz.scancalc.base.ui.ResultDialog
import tech.dhagz.scancalc.features.scan.ScanViewModel
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult
import tech.dhagz.scancalc.mainFabNavigation
import tech.dhagz.scancalc.permissionDenied
import tech.dhagz.scancalc.permissionGranted

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-26 10:39 PM
 */
@Preview
@Composable
fun ScanListScreen(
    navController: NavController? = null,
    scanViewModel: ScanViewModel = hiltViewModel(),
    scannedListViewModel: ScannedListViewModel = hiltViewModel()
) {

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val intentLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permissionGranted(
                navController = navController,
                intentLauncher = intentLauncher
            )
        } else {
            permissionDenied(
                context = context
            )
        }
    }

    val isCaptureLoading = remember { mutableStateOf(false) }
    val scanOperationResult = remember { mutableStateOf<ScanOperationResult?>(null) }

    val pagingItemsList = scannedListViewModel.getScanData().observeAsState()

    LoadingDialog(isCaptureLoading)
    ResultDialog(scanOperationResult)

    LaunchedEffect(imageUri.value) {
        imageUri.value?.let { uri ->
            isCaptureLoading.value = false
            scanOperationResult.value = scanViewModel.findExpression(context, uri)
        }
    }

    // List UI
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mainFabNavigation(
                    navController = navController,
                    context = context,
                    permissionLauncher = permissionLauncher,
                    intentLauncher = intentLauncher
                )
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_scan_list_add_24),
                    contentDescription = stringResource(id = R.string.image_picker_pick_image)
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 8.dp,
                    bottom = 72.dp
                )
            ) {
                val count = pagingItemsList.value?.size ?: 0
                when {
                    count > 0 -> {
                        val list = pagingItemsList.value ?: emptyList()
                        // Populate the items
                        items(items = list) { data ->
                            ScanListDataListItem(data)
                        }
                    }
                    count == 0 -> {
                        item {
                            ScanListEmptyListItem()
                        }
                    }
                }
            }
        }
    }

    Button(
        onClick = {
            // Check permission
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    // Some works that require permission
                    Log.d("ExampleScreen", "Code requires permission")
                }
                else -> {
                    // Asking for permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    ) {
        Text(text = "Check and Request Permission")
    }
}
