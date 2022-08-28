package tech.dhagz.scancalc.features.scan

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 3:45 PM
 */

/**
 * Converts the [TextRecognizer] to suspend function.
 */
suspend fun Task<Text>.suspendCoroutine(): Text {
    return kotlin.coroutines.suspendCoroutine { coroutine ->
        this.addOnSuccessListener { visionText ->
            coroutine.resume(visionText)
        }.addOnFailureListener {
            coroutine.resumeWithException(it)
        }
    }
}