package tech.dhagz.scancalc.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import tech.dhagz.scancalc.R
import tech.dhagz.scancalc.features.scan.ScanCaptureImageNotFoundException
import tech.dhagz.scancalc.features.scan.ScanExpressionNotFoundException
import tech.dhagz.scancalc.features.scan.ScanNumberNotFoundException
import tech.dhagz.scancalc.features.scan.ScanOperatorNotFoundException
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 10:54 PM
 */
@Composable
fun ResultDialog(
    scanOperationResult: MutableState<ScanOperationResult?>,
    onDismiss: (() -> Unit)? = null
) {

    if (scanOperationResult.value == null) {
        // Exit if dialog is not to be shown
        return
    }

    val cornerRadius = 16.dp
    val dialogShape = RoundedCornerShape(
        topStart = cornerRadius,
        topEnd = cornerRadius,
        bottomEnd = cornerRadius,
        bottomStart = cornerRadius
    )

    Dialog(
        onDismissRequest = {
            scanOperationResult.value = null
            onDismiss?.let { it() }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            Modifier
                .background(
                    color = colors.background,
                    shape = dialogShape
                )
                .padding(all = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (scanOperationResult.value) {
                    is ScanOperationResult.Success -> {
                        val res = scanOperationResult.value as ScanOperationResult.Success
                        // Expression
                        Text(
                            text = stringResource(
                                R.string.result_expression_text_label,
                                res.expression
                            )
                        )
                        // Result
                        Text(
                            text = stringResource(
                                R.string.result_expression_result_label,
                                res.result
                            )
                        )
                    }
                    is ScanOperationResult.Failed -> {
                        val res = scanOperationResult.value as ScanOperationResult.Failed
                        Text(text = stringResource(getMessage(res.throwable)))
                    }
                }
            }
        }
    }
}

private fun getMessage(ex: Throwable): Int {
    return when (ex) {
        is ScanOperatorNotFoundException -> R.string.error_operator_not_found
        is ScanExpressionNotFoundException -> R.string.error_expression_not_found
        is ScanNumberNotFoundException -> R.string.error_number_not_found
        is ScanCaptureImageNotFoundException -> R.string.error_image_not_found
        else -> R.string.error_something_went_wrong
    }
}