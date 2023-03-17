package com.aek.kotlinmvvm.service.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aek.kotlinmvvm.model.Country

@Database(
    entities = [Country::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDAO
}
