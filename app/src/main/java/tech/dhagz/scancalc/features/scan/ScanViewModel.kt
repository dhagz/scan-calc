package tech.dhagz.scancalc.features.scan

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult
import tech.dhagz.scancalc.features.scan.usecases.CalculateUseCase
import tech.dhagz.scancalc.features.scan.usecases.SaveScanDataUseCase
import javax.inject.Inject

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-27 9:41 PM
 */
@HiltViewModel
class ScanViewModel @Inject constructor(
    private val calculateUseCase: CalculateUseCase,
    private val saveScanDataUseCase: SaveScanDataUseCase
) : ViewModel() {

    private val recognizer: TextRecognizer =
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    /**
     * Process the [inputImage] whether or not it contains an expression.
     * If the [inputImage] contains an expression, the data will immediately
     * be saved to the local database
     *
     * @param [inputImage] - The input image from file or camera capture.
     * @return [ScanOperationResult] - Containing all the data and the result of the expression.
     */
    private suspend fun processInputImage(inputImage: InputImage): ScanOperationResult {
        val visionText = recognizer.process(inputImage).suspendCoroutine()
        if (visionText.textBlocks.isEmpty()) {
            return ScanOperationResult.Failed(ScanExpressionNotFoundException())
        } else {
            // Get the first text data
            val expression = visionText.textBlocks.first()
                .lines.first()
                .elements.first().text

            val res = calculateUseCase.invoke(expression)

            // Save the result if success
            if (res is ScanOperationResult.Success) {
                saveScanDataUseCase.invoke(
                    expression = expression,
                    scanOperation = res.scanOperation,
                    num1 = res.num1,
                    num2 = res.num2,
                    result = res.result
                )
            }
            return res
        }
    }

    /**
     * Find the expression using the [imageUri]
     *
     * @param [context] - The context to be used for getting [InputImage]
     * @param [imageUri] - The [Uri] of the image to be scanned.
     * @return [ScanOperationResult] - Containing all the data and the result of the expression.
     * @throws [ScanExpressionNotFoundException] - When scanned text is empty
     * @throws [ScanNumberNotFoundException] - Number is not found in the scanned text
     * @throws [ScanOperatorNotFoundException] - When the scanned text does not contain any of the
     *   supported operators.
     * @throws [Exception] - Fallback exception
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun findExpression(context: Context, imageUri: Uri): ScanOperationResult {
        val image = InputImage.fromFilePath(context, imageUri)
        return processInputImage(image)
    }

    /**
     * Find the expression using the [imageProxy]
     *
     * @param [imageProxy] - The image from camera capture
     * @return [ScanOperationResult] - Containing all the data and the result of the expression.
     */
    @SuppressLint("UnsafeOptInUsageError")
    suspend fun findExpression(imageProxy: ImageProxy): ScanOperationResult {
        imageProxy.image?.let {
            val image = InputImage.fromMediaImage(
                it, imageProxy.imageInfo.rotationDegrees
            )
            return processInputImage(image)
        }
        return ScanOperationResult.Failed(ScanCaptureImageNotFoundException())
    }
}