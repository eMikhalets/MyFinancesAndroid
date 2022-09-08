package com.emikhalets.myfinances.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Prefs @Inject constructor(context: Context) {

    private var sp: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var currentWalletId: Int
        get() = sp.getInt(CURRENT_WALLET_ID, -1)
        set(value) = sp.edit().putInt(CURRENT_WALLET_ID, value).apply()

    companion object {
        private const val NAME = "MyFinances_Preferences"
        private const val CURRENT_WALLET_ID = "CURRENT_WALLET_ID"
    }
}