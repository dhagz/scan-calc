package tech.dhagz.scancalc.features.list

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.dhagz.scancalc.R
import tech.dhagz.scancalc.features.scan.ScanViewModel
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult

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
    scanViewModel: ScanViewModel = viewModel(),
    scannedListViewModel: ScannedListViewModel = viewModel()
) {

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    val pagingItemsList = scannedListViewModel.getScanData().observeAsState()

    // Do the image URI processing here
    ProcessImageUri(
        scanViewModel = scanViewModel,
        imageUri = imageUri
    )

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
                // Go to Scan Screen
                launcher.launch("image/*")
            }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
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
                    bottom = 8.dp
                )
            ) {
                val count = pagingItemsList.value?.size ?: 0
                when {
                    count > 0 -> {
                        val list = pagingItemsList.value ?: emptyList()
                        // Populate the items
                        items(items = list) { data ->
                            Log.d(
                                "ScanListScreen",
                                "pagingItemsList.count=$count"
                            )
                            ScanListDataListItem(data)
                        }
                    }
//                    pagingItemsList.loadState.append.endOfPaginationReached -> {
//                        // Show the empty screen composable
//                        item {
//                            ScanListEmptyListItem()
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun ProcessImageUri(
    scanViewModel: ScanViewModel,
    imageUri: MutableState<Uri?>
) {
    val context = LocalContext.current

    LaunchedEffect(imageUri.value) {
        imageUri.value?.let { uri ->
            try {
                when (val res = scanViewModel.findEquation(context, uri)) {
                    is ScanOperationResult.Success -> {
                        Toast.makeText(
                            context,
                            "Result: ${res.result}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ScanOperationResult.Failed -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_something_went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e(
                            "ImagePickerScreen",
                            "Failed Operation",
                            res.throwable
                        )
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(
                    "ImagePickerScreen",
                    "Failed Operation",
                    ex
                )
            }
        }
    }
}