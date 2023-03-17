package com.aek.kotlinmvvm.repos

import com.aek.kotlinmvvm.model.Country
import io.reactivex.Single

interface CountryRepository {

    fun getCountriesFromApi(): Single<List<Country>>

    suspend fun getAll(): List<Country>

    suspend fun getWithId(id: Int): Country

    suspend fun insertAll(counties: List<Country>): List<Long>

    suspend fun deleteAll()

    suspend fun deleteWithId(id: Int)
}
