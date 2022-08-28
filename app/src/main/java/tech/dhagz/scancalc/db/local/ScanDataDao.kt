package tech.dhagz.scancalc.db.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tech.dhagz.scancalc.db.local.models.ScanDataDbModel

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-27 1:16 AM
 */
@Dao
abstract class ScanDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(scanDataDbModel: ScanDataDbModel)

    @Query("SELECT * FROM scan_data ORDER BY id DESC")
    abstract fun fetchAll(): LiveData<List<ScanDataDbModel>>
}