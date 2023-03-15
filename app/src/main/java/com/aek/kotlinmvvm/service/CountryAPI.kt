package com.aek.kotlinmvvm.service

import com.aek.kotlinmvvm.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPI {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryService::class.java)

    fun getData(): Single<List<Country>> {
        return api.getCountries()
    }

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}
