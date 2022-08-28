package tech.dhagz.scancalc.features.list.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import tech.dhagz.scancalc.db.ScanDataRepository
import tech.dhagz.scancalc.features.list.models.ScanData
import javax.inject.Inject

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 1:54 PM
 */
class GetScanDataListUseCase @Inject constructor(
    private val scanDataRepository: ScanDataRepository
) {

    operator fun invoke(): LiveData<List<ScanData>> {
        return scanDataRepository.getAllScanData().map {
            return@map it.map { scanDataDbModel ->
                ScanData(scanDataDbModel)
            }
        }
    }
}