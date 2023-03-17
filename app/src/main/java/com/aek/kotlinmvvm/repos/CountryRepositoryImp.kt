package com.aek.kotlinmvvm.repos

import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.service.CountryService
import com.aek.kotlinmvvm.service.room.CountryDAO
import io.reactivex.Single

class CountryRepositoryImp(
    private val countryService: CountryService,
    private val countryDAO: CountryDAO
) : CountryRepository {

    override fun getCountriesFromApi(): Single<List<Country>> =
        countryService.getCountries()

    override suspend fun getAll(): List<Country> =
        countryDAO.getAll()

    override suspend fun getWithId(id: Int): Country =
        countryDAO.getWithId(id)

    override suspend fun insertAll(counties: List<Country>): List<Long> =
        countryDAO.insertAll(*counties.toTypedArray())

    override suspend fun deleteAll() =
        countryDAO.deleteAll()

    override suspend fun deleteWithId(id: Int) =
        countryDAO.deleteWithId(id)
}
