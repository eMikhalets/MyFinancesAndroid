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

    var defaultWalletId: Long
        get() = sp.getLong(DEFAULT_WALLET_ID_PREF, DEFAULT_WALLET_ID)
        set(value) = sp.edit().putLong(DEFAULT_WALLET_ID_PREF, value).apply()

    var defaultCurrencyId: Long
        get() = sp.getLong(DEFAULT_CURRENCY_ID_PREF, DEFAULT_CURRENCY_ID)
        set(value) = sp.edit().putLong(DEFAULT_CURRENCY_ID_PREF, value).apply()

    companion object {
        private const val NAME = "MyFinances_Preferences"
        private const val APP_INITIALISED = "APP_INITIALISED"
        private const val CURRENT_WALLET_ID = "CURRENT_WALLET_ID"
        private const val DEFAULT_WALLET_ID_PREF = "DEFAULT_WALLET_ID"
        private const val DEFAULT_CURRENCY_ID_PREF = "DEFAULT_CURRENCY_ID"
    }
}