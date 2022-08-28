package tech.dhagz.scancalc.features.scan.models

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 12:43 AM
 */
sealed class ScanOperation(
    val symbol: Char
) {

    companion object {

        /**
         * The list of supported operations
         */
        val supportedOperations = listOf(
            Add(),
            Subtract(),
            Divide(),
            Multiply()
        )

        /**
         * Identify the [ScanOperation] based on the [symbol] in the [supportedOperations].
         * Returns `null` if the symbol is not found.
         */
        fun identifyOperationBySymbol(symbol: Char): ScanOperation {
            return supportedOperations.firstOrNull {
                it.symbol == symbol
            } ?: Unknown
        }
    }

    abstract fun getResult(num1: Float, num2: Float): Float

    /**
     * Add Operation
     */
    class Add : ScanOperation(
        symbol = '+'
    ) {
        override fun getResult(num1: Float, num2: Float): Float {
            return num1 + num2
        }
    }

    /**
     * Subtract Operation
     */
    class Subtract : ScanOperation(
        symbol = '-'
    ) {
        override fun getResult(num1: Float, num2: Float): Float {
            return num1 - num2
        }
    }

    /**
     * Divide Operation
     */
    class Divide : ScanOperation(
        symbol = '/'
    ) {
        override fun getResult(num1: Float, num2: Float): Float {
            return num1 / num2
        }
    }

    /**
     * Multiply Operation
     */
    class Multiply : ScanOperation(
        symbol = '*'
    ) {
        override fun getResult(num1: Float, num2: Float): Float {
            return num1 * num2
        }
    }

    object Unknown : ScanOperation(' ') {
        override fun getResult(num1: Float, num2: Float) = -1F
    }
}