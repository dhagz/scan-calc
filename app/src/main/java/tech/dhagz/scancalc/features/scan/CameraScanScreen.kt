package tech.dhagz.scancalc.features.scan

import android.Manifest
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
fun CameraScanScreen() {
//    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
//
//    when (cameraPermissionState.status) {
//        // If the camera permission is granted, then show screen with the feature enabled
//        PermissionStatus.Granted -> {
//            Text("Camera permission Granted")
//        }
//        is PermissionStatus.Denied -> {
//            Column {
//                val textToShow =
//                    if ((cameraPermissionState.status as PermissionStatus.Denied)
//                            .shouldShowRationale
//                    ) {
//                        // If the user has denied the permission but the rationale can be shown,
//                        // then gently explain why the app requires this permission
//                        "The camera is important for this app. Please grant the permission."
//                    } else {
//                        // If it's the first time the user lands on this feature, or the user
//                        // doesn't want to be asked again for this permission, explain that the
//                        // permission is required
//                        "Camera permission required for this feature to be available. " +
//                            "Please grant the permission"
//                    }
//                Text(textToShow)
//                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
//                    Text("Request permission")
//                }
//            }
//        }
//    }
}

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
                        cameraSelector as CameraSelector,
                        imageCapture.value,
                        prev
                    )
                }, executor
            )
            previewCameraView
        }
    )
}

@Composable
private fun BindTextRecognitionOutput() {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (title, text, backToCameraButton) = createRefs()
//        val textValue by textRecognitionViewModel.getOutput().observeAsState()
//        val scrollState = rememberScrollState(0)

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    linkTo(start = parent.start, end = parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
                .then(Modifier.padding(16.dp)),
            text = "TEXT RECOGNITION RESULT",
            style = TextStyle(fontStyle = FontStyle.Italic),
            fontWeight = FontWeight.Bold
        )

        SelectionContainer(modifier = Modifier
            .constrainAs(text) {
                linkTo(top = title.bottom, bottom = backToCameraButton.top, 16.dp, 16.dp)
                linkTo(start = parent.start, end = parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .then(
                Modifier.padding(16.dp)
            )
            .then(
                Modifier.border(
                    width = 4.dp,
                    color = Color.Black,
                    shape = RectangleShape
                )
            )
        ) {
//            Text(
//                text = textValue ?: "",
//                modifier = Modifier
//                    .verticalScroll(scrollState)
//                    .then(Modifier.padding(8.dp))
//            )
        }

        Button(modifier = Modifier.constrainAs(backToCameraButton) {
            linkTo(start = parent.start, end = parent.end)
            bottom.linkTo(parent.bottom, 16.dp)
            width = Dimension.preferredWrapContent
            height = Dimension.preferredWrapContent
        }, onClick = {
//            navController.popBackStack()
        })
        {
            Text("CAMERA")
        }
    }
}
