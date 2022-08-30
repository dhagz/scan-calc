package tech.dhagz.scancalc.features.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import tech.dhagz.scancalc.R
import tech.dhagz.scancalc.features.list.models.ScanData
import tech.dhagz.scancalc.features.scan.models.ScanOperation

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 3:25 PM
 */

/**
 * The List Item to show when there are not items to display
 */
@Preview
@Composable
fun ScanListEmptyListItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text(text = "No records.")
    }
}

/**
 * The list item per [scanData]
 */
@Preview
@Composable
fun ScanListDataListItem(
    @PreviewParameter(ScanListDataPreviewProvider::class)
    scanData: ScanData
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(
                R.string.result_expression_text_label,
                scanData.expression
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(
                R.string.result_expression_result_label,
                scanData.result
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider()
    }
}

/**
 * Preview Provider for [ScanData]
 */
class ScanListDataPreviewProvider(
    override val values: Sequence<ScanData> = listOf(
        ScanData(
            id = 0,
            expression = "1+2",
            num1 = 1F,
            num2 = 2F,
            result = 3F,
            operation = ScanOperation.Add()
        ),
    ).asSequence()
) : PreviewParameterProvider<ScanData>