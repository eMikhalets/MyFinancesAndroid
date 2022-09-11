package com.emikhalets.myfinances.presentation.screens.wallet

import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class WalletState(
    val wallet: Wallet? = null,
    val walletSaved: Boolean = false,
    val walletDeleted: Boolean = false,
    val error: String = "",
) {

    fun setWallet(wallet: Wallet): WalletState {
        return this.copy(wallet = wallet, error = "")
    }

    fun setWalletSaved(): WalletState {
        return this.copy(walletSaved = true, error = "")
    }

    fun setWalletDeleted(): WalletState {
        return this.copy(walletDeleted = true, error = "")
    }

    fun setError(message: String?): WalletState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}