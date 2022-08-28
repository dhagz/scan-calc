package tech.dhagz.scancalc.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 11:00 PM
 */
@Preview
@Composable
fun LoadingDialog(
    isShow: MutableState<Boolean> = remember { mutableStateOf(true) },
//    onDismiss: (() -> Unit)? = null
) {

    if (!isShow.value) {
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
            isShow.value = false
//            onDismiss?.let { it() }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = colors.primary
                )
            }
        }
    }
}