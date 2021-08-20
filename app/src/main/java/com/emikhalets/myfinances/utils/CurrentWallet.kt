package com.emikhalets.myfinances.utils

import android.content.Context

private const val CurrentWallet = "com.emikhalets.myfinances.current_wallet"
private const val SharedPreferences = "com.emikhalets.myfinances.shared_preferences"

fun Context.getCurrentWalletId(): Long {
    return getSharedPreferences(SharedPreferences, Context.MODE_PRIVATE)
        .getLong(CurrentWallet, 0)
}

fun Context.setCurrentWalletId(value: Long) {
    getSharedPreferences(SharedPreferences, Context.MODE_PRIVATE).edit()
        .putLong(CurrentWallet, value).apply()
}