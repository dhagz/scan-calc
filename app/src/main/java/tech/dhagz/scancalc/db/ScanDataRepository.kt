package tech.dhagz.scancalc.db

import androidx.lifecycle.LiveData
import tech.dhagz.scancalc.db.local.ScanDataDao
import tech.dhagz.scancalc.db.local.models.ScanDataDbModel
import javax.inject.Inject

/**
 * The repository used to access the different data sources.
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 1:25 PM
 */
class ScanDataRepository @Inject constructor(
    private val scanDataDao: ScanDataDao
) {

    /**
     * Insert a new scan record.
     */
    suspend fun insert(scanDataDbModel: ScanDataDbModel) {
        scanDataDao.insert(scanDataDbModel)
    }

    /**
     * Fetch the list of scan records.
     */
    fun getAllScanData(): LiveData<List<ScanDataDbModel>> {
        return scanDataDao.fetchAll()
    }
}