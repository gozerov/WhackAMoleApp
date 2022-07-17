package ru.gozerov.whackamoleapp.utils

import android.content.Context
import android.content.SharedPreferences
import ru.gozerov.whackamoleapp.R


val Context?.sharedPrefs: SharedPreferences
    get() {
        return this?.applicationContext?.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            ?: throw NullPointerException("CONTEXT IS NULL")
    }