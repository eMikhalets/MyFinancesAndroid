package com.emikhalets.core

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Prefs @Inject constructor(@ApplicationContext context: Context) {

    private var sp: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var currentWalletId: Long
        get() = sp.getLong(CURRENT_WALLET_ID, NO_WALLET_ID)
        set(value) = sp.edit().putLong(CURRENT_WALLET_ID, value).apply()

    companion object {
        private const val NAME = "MyFinances_Preferences"
        private const val CURRENT_WALLET_ID = "CURRENT_WALLET_ID"

        const val NO_WALLET_ID = 0L
    }
}