package com.emikhalets.core

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Prefs @Inject constructor(@ApplicationContext context: Context) {

    private var sp: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var isAppInitialised: Boolean
        get() = sp.getBoolean(APP_INITIALISED, false)
        set(value) = sp.edit().putBoolean(APP_INITIALISED, value).apply()

    var currentWalletId: Long
        get() = sp.getLong(CURRENT_WALLET_ID, DEFAULT_WALLET_ID)
        set(value) = sp.edit().putLong(CURRENT_WALLET_ID, value).apply()

    companion object {
        private const val NAME = "MyFinances_Preferences"
        private const val APP_INITIALISED = "APP_INITIALISED"
        private const val CURRENT_WALLET_ID = "CURRENT_WALLET_ID"
    }
}