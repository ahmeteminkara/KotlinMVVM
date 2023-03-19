package com.aek.kotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aek.kotlinmvvm.core.base.BaseViewModel
import com.aek.kotlinmvvm.model.Country
import com.aek.kotlinmvvm.repos.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
) : BaseViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getCountryData(id: Int) {
        viewModelScope.launch {
            val country = repository.getWithId(id)
            countryLiveData.value = country
        }
    }
}
