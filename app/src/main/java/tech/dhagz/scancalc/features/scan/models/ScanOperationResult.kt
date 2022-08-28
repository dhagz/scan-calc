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
        /**
         * The expression string
         */
        val expression: String,
        /**
         * The expression operation
         */
        val scanOperation: ScanOperation,
        /**
         * The first number
         */
        val num1: Float,
        /**
         * The second number
         */
        val num2: Float
    ) : ScanOperationResult() {
        /**
         * The result of the [scanOperation] of [num1] and [num2]
         */
        val result = scanOperation.getResult(num1, num2)
    }

    /**
     * When the scan operation failed
     */
    class Failed(
        /**
         * The exception thrown when failed.
         */
        val throwable: Throwable
    ) : ScanOperationResult()
}