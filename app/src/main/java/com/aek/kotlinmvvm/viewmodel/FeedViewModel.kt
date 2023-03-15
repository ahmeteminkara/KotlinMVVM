package com.aek.kotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aek.kotlinmvvm.model.Country
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    val countriesLiveData = MutableLiveData<List<Country>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    init {
        refreshData()
    }

    fun refreshData() {
        loadingLiveData.value = true
        viewModelScope.launch {
            delay(3000)
            loadingLiveData.value = false
            val dataList = listOf(
                Country(
                    "Turkey",
                    "Ankara",
                    "Asia",
                    "TRY",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Flag_of_Turkey.svg/480px-Flag_of_Turkey.svg.png",
                    "Turkish"
                ),
                Country(
                    "Italy",
                    "Roma",
                    "Europa",
                    "EUR",
                    "https://upload.wikimedia.org/wikipedia/en/thumb/0/03/Flag_of_Italy.svg/480px-Flag_of_Italy.svg.png",
                    "French"
                ),
                Country(
                    "Germany",
                    "Berlin",
                    "Europa",
                    "EUR",
                    "https://upload.wikimedia.org/wikipedia/en/thumb/b/ba/Flag_of_Germany.svg/480px-Flag_of_Germany.svg.png",
                    "German"
                )
            )
            countriesLiveData.value = dataList
        }
    }
}
