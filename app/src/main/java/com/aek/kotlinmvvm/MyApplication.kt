package com.aek.kotlinmvvm

import android.app.Application
import com.aek.kotlinmvvm.util.helper.SharedPreferencesHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
}
