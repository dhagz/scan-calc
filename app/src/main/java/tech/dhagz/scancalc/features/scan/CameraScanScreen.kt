package tech.dhagz.scancalc.features.scan

import android.content.Context
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.dhagz.scancalc.R
import tech.dhagz.scancalc.base.ui.LoadingDialog
import tech.dhagz.scancalc.base.ui.ResultDialog
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult
import java.util.concurrent.Executor

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 11:19 AM
 */
@Suppress("OPT_IN_IS_NOT_ENABLED")
@Composable
fun CameraScanScreen(
    navController: NavController? = null,
    scanViewModel: ScanViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imageCapture = remember { mutableStateOf<ImageCapture?>(null) }
    val executor = remember(context) { ContextCompat.getMainExecutor(context) }
    val imageProxy = remember { mutableStateOf<ImageProxy?>(null) }

    val isCaptureLoading = remember { mutableStateOf(false) }
    val scanOperationResult = remember { mutableStateOf<ScanOperationResult?>(null) }

    LoadingDialog(isCaptureLoading)
    ResultDialog(scanOperationResult) {
        navController?.navigateUp()
    }

    LaunchedEffect(imageProxy.value) {
        imageProxy.value?.let { image ->
            isCaptureLoading.value = false
            scanOperationResult.value = scanViewModel.findExpression(image)
        }
    }

    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxSize()
    ) {
        MLCameraView(
            modifier = Modifier.weight(1f),
            imageCapture = imageCapture,
            executor = executor,
            context = context
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                isCaptureLoading.value = true
                imageCapture.value?.takePicture(executor,
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            imageProxy.value = image
                        }

                        override fun onError(exception: ImageCaptureException) {
                            isCaptureLoading.value = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.camera_error_failed_capture),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        ) {
            Text(text = stringResource(id = R.string.camera_capture))
        }
    }
}

/**
 * Source: https://betterprogramming.pub/text-recognition-with-jetpack-compose-and-camerax-9093735edf4f
 */
@Composable
fun MLCameraView(
    modifier: Modifier,
    imageCapture: MutableState<ImageCapture?>,
    executor: Executor,
    context: Context
) {
    val previewCameraView = remember { PreviewView(context) }
    val cameraProviderFuture =
        remember(context) { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = remember(cameraProviderFuture) { cameraProviderFuture.get() }
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = {
            cameraProviderFuture.addListener(
                {
                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    imageCapture.value = ImageCapture.Builder().build()

                    cameraProvider.unbindAll()

                    val prev = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewCameraView.surfaceProvider)
                    }

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        imageCapture.value,
                        prev
                    )
                }, executor
            )
            previewCameraView
        }
    )
}
