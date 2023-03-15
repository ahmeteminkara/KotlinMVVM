package com.aek.kotlinmvvm.service

import com.aek.kotlinmvvm.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryService {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>
}
