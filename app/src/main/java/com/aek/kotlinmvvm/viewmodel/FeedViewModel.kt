package com.aek.kotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.repos.CountryRepository
import com.aek.kotlinmvvm.util.helper.SharedPreferencesHelper
import com.aek.kotlinmvvm.util.extentions.minuteToMillisecond
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) :
    ViewModel() {

    private val disposable = CompositeDisposable()
    private val refreshTime = 10L.minuteToMillisecond()

    val countriesLiveData = MutableLiveData<List<Country>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun refreshData() {
        sharedPreferencesHelper.saveTime(0)
        getData()
    }

    fun getData() {
        val updateTime = sharedPreferencesHelper.getTime()
        if (updateTime != 0L && (System.nanoTime() - updateTime) < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    private fun getDataFromAPI() {
        loadingLiveData.value = true
        disposable.add(
            countryRepository.getCountriesFromApi()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        saveCountriesFromRoom(t)
                    }

                    override fun onError(e: Throwable) {
                        loadingLiveData.value = false
                        errorLiveData.value = e.localizedMessage
                    }
                })
        )
    }

    private fun getDataFromSQLite() {
        loadingLiveData.value = true
        viewModelScope.launch {
            val list = countryRepository.getAll()
            showCountries(list)
        }
    }

    private fun saveCountriesFromRoom(list: List<Country>) {
        viewModelScope.launch {
            countryRepository.deleteAll()
            val addedIds = countryRepository.insertAll(list)
            for (i in addedIds.indices) {
                list[i].id = addedIds[i].toInt()
            }
            sharedPreferencesHelper.saveTime(System.nanoTime())
            showCountries(list)
        }
    }

    private fun showCountries(list: List<Country>) {
        countriesLiveData.value = list
        loadingLiveData.value = false
    }
}
