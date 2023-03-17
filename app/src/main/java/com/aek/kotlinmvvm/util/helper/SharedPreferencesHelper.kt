package com.aek.kotlinmvvm.util.helper

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val PREFERENCE_TIME = "time"

    fun saveTime(time: Long) {
        sharedPreferences.edit(commit = true) {
            putLong(PREFERENCE_TIME, time)
        }
    }

    fun getTime() = sharedPreferences.getLong(PREFERENCE_TIME, 0)
}
