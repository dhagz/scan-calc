package tech.dhagz.scancalc.features.scan.usecases

import tech.dhagz.scancalc.features.scan.ScanNumberNotFoundException
import tech.dhagz.scancalc.features.scan.ScanOperatorNotFoundException
import tech.dhagz.scancalc.features.scan.models.ScanOperation
import tech.dhagz.scancalc.features.scan.models.ScanOperationResult
import java.lang.NumberFormatException

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 12:41 AM
 */
class CalculateUseCase {

    operator fun invoke(expression: String): ScanOperationResult {
        return ScanOperation.supportedOperations.firstOrNull {
            expression.contains(char = it.symbol)
        }?.let {
            return@let try {
                // Get the first number
                val num1 = expression.substring(
                    startIndex = 0,
                    endIndex = expression.indexOf(
                        char = it.symbol,
                        startIndex = 0
                    )
                ).toFloat()

                // Get the second number
                val num2 = expression.substring(
                    startIndex = expression.indexOf(
                        char = it.symbol,
                        startIndex = 0
                    ) + 1,
                    endIndex = expression.length
                ).toFloat()

                // Return the success result
                ScanOperationResult.Success(
                    expression = expression,
                    scanOperation = it,
                    num1 = num1,
                    num2 = num2
                )
            } catch (ex: NumberFormatException) {
                ScanOperationResult.Failed(
                    throwable = ScanNumberNotFoundException(ex)
                )
            } catch (ex: Exception) {
                ScanOperationResult.Failed(
                    throwable = ex
                )
            }
        } ?: ScanOperationResult.Failed(
            throwable = ScanOperatorNotFoundException()
        )
    }
}