package tech.dhagz.scancalc._di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.dhagz.scancalc.db.AppDatabase
import tech.dhagz.scancalc.db.local.ScanDataDao
import javax.inject.Singleton

/**
 * ...
 *
 *
 * Copyright (c) 2022 dhagz.tech. All rights reserved.
 *
 * @author Fernando "Dhagz" Dagoc Jr.
 * @since 2022-08-27 1:17 AM
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesScanDataDao(
        appDatabase: AppDatabase
    ): ScanDataDao = appDatabase.scanDataDao()
}