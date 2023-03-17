package com.aek.kotlinmvvm.di.modules

import com.aek.kotlinmvvm.service.CountryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun providesCountryService(retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }
}
