package com.aek.kotlinmvvm.di.modules

import com.aek.kotlinmvvm.repos.CountryRepository
import com.aek.kotlinmvvm.repos.CountryRepositoryImp
import com.aek.kotlinmvvm.service.CountryService
import com.aek.kotlinmvvm.service.room.CountryDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesCountryRepository(
        countryService: CountryService,
        countryDAO: CountryDAO
    ): CountryRepository {
        return CountryRepositoryImp(countryService, countryDAO)
    }
}
