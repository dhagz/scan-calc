package tech.dhagz.scancalc.features.scan.usecases

import tech.dhagz.scancalc.db.ScanDataRepository
import tech.dhagz.scancalc.db.local.models.ScanDataDbModel
import tech.dhagz.scancalc.features.scan.models.ScanOperation
import javax.inject.Inject

/**
 * Save the scanned expression to the local database.
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 1:19 PM
 */
class SaveScanDataUseCase @Inject constructor(
    private val scanDataRepository: ScanDataRepository
) {

    suspend operator fun invoke(
        expression: String,
        scanOperation: ScanOperation,
        num1: Float,
        num2: Float,
        result: Float
    ) {
        val data = ScanDataDbModel(
            expression = expression,
            num1 = num1,
            num2 = num2,
            result = result,
            operator = scanOperation.symbol
        )
        scanDataRepository.insert(data)
    }
}