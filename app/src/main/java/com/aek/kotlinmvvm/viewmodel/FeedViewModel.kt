package com.aek.kotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.service.CountryAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    private val countryService = CountryAPI()
    private val disposable = CompositeDisposable()

    val countriesLiveData = MutableLiveData<List<Country>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    init {
        refreshData()
    }

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        loadingLiveData.value = true
        disposable.add(
            countryService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        countriesLiveData.value = t
                        loadingLiveData.value = false
                    }

                    override fun onError(e: Throwable) {
                        loadingLiveData.value = false
                        errorLiveData.value = e.localizedMessage
                    }
                })
        )
    }
}
