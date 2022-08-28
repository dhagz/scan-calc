package tech.dhagz.scancalc.features.scan

import tech.dhagz.scancalc.features.scan.models.ScanOperation

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 1:00 AM
 */
open class ScanException(cause: Throwable? = null) : Exception(cause)

/**
 * Thrown when the supported operation is not found.
 * See [ScanOperation]
 */
class ScanOperatorNotFoundException : ScanException()

/**
 * Thrown when the scan didn't fine anything.
 */
class ScanVisionTextNotFoundException : ScanException()

/**
 * Thrown when the scanned text is not able to parse the numbers
 */
class ScanNumberNotFoundException(cause: Throwable? = null) : ScanException(cause = cause)