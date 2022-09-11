package com.emikhalets.myfinances.presentation.screens.wallets

import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.data.entity.WalletEntity
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class WalletsState(
    val wallets: List<WalletEntity> = emptyList(),
    val wallet: Wallet? = null,
    val error: String = "",
) {

    fun setWallets(wallets: List<WalletEntity>): WalletsState {
        return this.copy(wallets = wallets)
    }

    fun setWallet(wallet: Wallet): WalletsState {
        return this.copy(wallet = wallet)
    }

    fun setError(message: String?): WalletsState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}