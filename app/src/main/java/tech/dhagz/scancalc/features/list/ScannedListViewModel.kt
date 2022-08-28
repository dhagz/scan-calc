package tech.dhagz.scancalc.features.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.dhagz.scancalc.features.list.models.ScanData
import tech.dhagz.scancalc.features.list.usecases.GetScanDataListUseCase
import javax.inject.Inject

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-28 2:16 PM
 */
@HiltViewModel
class ScannedListViewModel @Inject constructor(
    private val getScanDataListUseCase: GetScanDataListUseCase
) : ViewModel() {

    fun getScanData(): LiveData<List<ScanData>> {
        return getScanDataListUseCase.invoke()
    }
}