package tech.dhagz.scancalc.db.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The database entity containing the scan data
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-27 1:05 AM
 */
@Entity(tableName = "scan_data")
data class ScanDataDbModel(
    @PrimaryKey(autoGenerate = true)
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
    val operator: Char
)
