package com.aek.kotlinmvvm.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.aek.kotlinmvvm.MyApplication
import com.aek.kotlinmvvm.service.room.CountryDAO
import com.aek.kotlinmvvm.service.room.CountryDatabase
import com.aek.kotlinmvvm.util.helper.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(context)
    }

    @Provides
    fun providesCountryDatabase(@ApplicationContext context: Context): CountryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "country_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesCountryDao(countryDatabase: CountryDatabase): CountryDAO {
        return countryDatabase.countryDao()
    }
}
