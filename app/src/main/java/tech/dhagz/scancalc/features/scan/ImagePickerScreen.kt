package tech.dhagz.scancalc.features.scan

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.dhagz.scancalc.R
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult
import java.lang.Exception


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
fun ImagePickerScreen(
    scanViewModel: ScanViewModel = viewModel()
) {

    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                imageUri.value?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }

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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    // Uri
                    launcher.launch("image/*")
                }
            ) {
                Text(text = stringResource(id = R.string.image_picker_pick_image))
            }
        }
    }
}