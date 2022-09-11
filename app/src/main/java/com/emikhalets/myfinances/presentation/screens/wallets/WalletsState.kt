package com.emikhalets.myfinances.presentation.screens.wallets

import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class WalletsState(
    val wallets: List<Wallet> = emptyList(),
    val error: String = "",
) {

    fun setWallets(wallets: List<Wallet>): WalletsState {
        return this.copy(wallets = wallets, error = "")
    }

    fun setError(message: String?): WalletsState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}