package tech.dhagz.scancalc.features.scan.models

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 12:47 AM
 */
sealed class ScanOperationResult {

    /**
     * When the scan operation is a success
     */
    class Success(
        val scanOperation: ScanOperation,
        val num1: Float,
        val num2: Float
    ) : ScanOperationResult() {
        val result = scanOperation.getResult(num1, num2)
    }

    /**
     * When the scan operation failed
     */
    class Failed(
        val throwable: Throwable
    ) : ScanOperationResult()
}