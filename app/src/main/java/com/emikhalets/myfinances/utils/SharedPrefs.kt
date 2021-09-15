package com.emikhalets.myfinances.utils

import android.content.Context

object SharedPrefs {

    private const val NAME = "MyFinances_shared_preferences"
    private const val DEFAULT_WALLET_ID = "MyFinances_shared_preferences"

    fun getCurrentWalletId(context: Context): Long {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .getLong(DEFAULT_WALLET_ID, 0)
    }

    fun setCurrentWalletId(context: Context, value: Long) {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
            .putLong(DEFAULT_WALLET_ID, value).apply()
    }
}