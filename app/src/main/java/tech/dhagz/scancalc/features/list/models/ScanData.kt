package tech.dhagz.scancalc.features.list.models

import tech.dhagz.scancalc.db.local.models.ScanDataDbModel
import tech.dhagz.scancalc.features.scan.models.ScanOperation

/**
 * The database entity containing the scan data
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-27 1:05 AM
 */
data class ScanData(
    val id: Int = 0,
    /**
     * The scanned expression text
     */
    val expression: String,
    /**
     * The first number for the operation
     */
    val num1: Float,
    /**
     * The second number for the operation
     */
    val num2: Float,
    /**
     * The recorded operation result
     */
    val result: Float,
    /**
     * The operator being used in the expression
     */
    val operation: ScanOperation
) {

    constructor(scanDataDbModel: ScanDataDbModel) : this(
        id = scanDataDbModel.id,
        expression = scanDataDbModel.expression,
        num1 = scanDataDbModel.num1,
        num2 = scanDataDbModel.num2,
        result = scanDataDbModel.result,
        operation = ScanOperation.identifyOperationBySymbol(scanDataDbModel.operator)
    )
}
