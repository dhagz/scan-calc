package tech.dhagz.scancalc.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.dhagz.scancalc.db.local.models.ScanDataDbModel
import tech.dhagz.scancalc.db.local.ScanDataDao

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-27 1:00 AM
 */
@Database(entities = [ScanDataDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scanDataDao(): ScanDataDao
}