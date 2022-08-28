package tech.dhagz.scancalc.features.scan

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
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
     * Process the [visionText] whether or not it contains an expression.
     * If the [visionText] contains an expression, the data will immediately
     * be saved to the local database
     *
     * @param [visionText] - The text found when scanning
     * @return [ScanOperationResult] - Containing all the data and the result of the expression.
     * @throws [ScanVisionTextNotFoundException] - When scanned text is empty
     * @throws [ScanNumberNotFoundException] - Number is not found in the scanned text
     * @throws [ScanOperatorNotFoundException] - When the scanned text does not contain any of the
     *   supported operators.
     * @throws [Exception] - Fallback exception
     */
    private suspend fun processVisionText(visionText: Text): ScanOperationResult {
        if (visionText.textBlocks.isEmpty()) {
            throw ScanVisionTextNotFoundException()
        } else {
            // Get the first text data
            val expression = visionText.textBlocks.first()
                .lines.first()
                .elements.first().text
            when (val res = calculateUseCase.invoke(expression)) {
                is ScanOperationResult.Success -> {
                    saveScanDataUseCase.invoke(
                        expression = expression,
                        scanOperation = res.scanOperation,
                        num1 = res.num1,
                        num2 = res.num2,
                        result = res.result
                    )
                    return res
                }
                is ScanOperationResult.Failed -> throw res.throwable
            }
        }
    }

    /**
     * Find the expression using the [imageUri]
     *
     * @param [context] - The context to be used for getting [InputImage]
     * @param [imageUri] - The [Uri] of the image to be scanned.
     * @return [ScanOperationResult] - Containing all the data and the result of the expression.
     * @throws [ScanVisionTextNotFoundException] - When scanned text is empty
     * @throws [ScanNumberNotFoundException] - Number is not found in the scanned text
     * @throws [ScanOperatorNotFoundException] - When the scanned text does not contain any of the
     *   supported operators.
     * @throws [Exception] - Fallback exception
     */
    suspend fun findEquation(context: Context, imageUri: Uri): ScanOperationResult {
        val image = InputImage.fromFilePath(context, imageUri)
        val visionText = recognizer.process(image).suspendCoroutine()
        return processVisionText(visionText)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun findEquation(imageProxy: ImageProxy): Task<Text>? {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            return recognizer.process(image)
        }
        return null
    }

//    suspend fun findEquation(bitmap: Bitmap, rotationDegrees: Int = 0): ScanOperationResult {
//        return suspendCoroutine { coroutine ->
//            val image = InputImage.fromBitmap(bitmap, rotationDegrees)
//            recognizer.process(image).addOnSuccessListener { visionText ->
//                try {
//                    val res = processVisionText(visionText)
//                    coroutine.resume(res)
//                } catch (ex: Exception) {
//                    coroutine.resumeWithException(ex)
//                }
//            }.addOnFailureListener { ex ->
//                coroutine.resumeWithException(ex)
//            }
//        }
//    }
}