package com.aek.kotlinmvvm.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SingletonSharedPreferences private constructor() {

    companion object {

        @Volatile
        private var instance: SingletonSharedPreferences? = null

        private var sharedPreferences: SharedPreferences? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: makeInstance(context).also {
                instance = it
            }
        }

        private fun makeInstance(context: Context): SingletonSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SingletonSharedPreferences()
        }

        private const val PREFERENCE_TIME = "time"
    }

    fun saveTime(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(PREFERENCE_TIME, time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCE_TIME, 0) ?: 0
}
