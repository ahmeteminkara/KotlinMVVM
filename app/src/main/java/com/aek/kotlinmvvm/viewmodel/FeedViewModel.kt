package com.aek.kotlinmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.service.CountryAPI
import com.aek.kotlinmvvm.service.CountryDatabase
import com.aek.kotlinmvvm.util.SingletonSharedPreferences
import com.aek.kotlinmvvm.util.extentions.minuteToMillisecond
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val countryService = CountryAPI()
    private val disposable = CompositeDisposable()
    private lateinit var preferences: SingletonSharedPreferences
    private val refreshTime = 10L.minuteToMillisecond()

    val countriesLiveData = MutableLiveData<List<Country>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    fun refreshData(context: Context) {
        preferences.saveTime(0)
        getData(context)
    }

    fun getData(context: Context) {
        preferences = SingletonSharedPreferences(context)
        val updateTime = preferences.getTime()
        if (updateTime != 0L && (System.nanoTime() - updateTime) < refreshTime) {
            getDataFromSQLite(context)
        } else {
            getDataFromAPI(context)
        }
    }

    private fun getDataFromAPI(context: Context) {
        loadingLiveData.value = true
        disposable.add(
            countryService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        saveCountriesFromRoom(context, t)
                    }

                    override fun onError(e: Throwable) {
                        loadingLiveData.value = false
                        errorLiveData.value = e.localizedMessage
                    }
                })
        )
    }

    private fun getDataFromSQLite(context: Context) {
        loadingLiveData.value = true
        viewModelScope.launch {
            val list = CountryDatabase(context).countryDao().getAll()
            showCountries(list)
        }
    }

    private fun saveCountriesFromRoom(context: Context, list: List<Country>) {
        viewModelScope.launch {
            val dao = CountryDatabase(context).countryDao()
            dao.deleteAll()
            val addedIds = dao.insertAll(*list.toTypedArray())
            for (i in addedIds.indices) {
                list[i].id = addedIds[i].toInt()
            }
            preferences.saveTime(System.nanoTime())
            showCountries(list)
        }
    }

    private fun showCountries(list: List<Country>) {
        countriesLiveData.value = list
        loadingLiveData.value = false
    }
}
