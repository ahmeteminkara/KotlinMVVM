package com.aek.kotlinmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getCountryData(context: Context, id: Int) {
        viewModelScope.launch {
            val dao = CountryDatabase(context).countryDao()
            val country = dao.getWithId(id)
            countryLiveData.value = country
        }
    }
}
