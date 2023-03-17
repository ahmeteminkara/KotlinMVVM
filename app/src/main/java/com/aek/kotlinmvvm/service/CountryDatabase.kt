package com.aek.kotlinmvvm.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aek.kotlinmvvm.model.Country

@Database(
    entities = [Country::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDAO

    companion object {

        @Volatile
        private var instance: CountryDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "country_db"
        ).build()
    }
}
