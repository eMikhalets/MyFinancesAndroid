package com.emikhalets.presentation.screens.wallets

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.WalletEntity

data class WalletsState(
    val wallets: List<WalletEntity> = emptyList(),
    val error: UiString? = null,
) {

    fun setWallets(wallets: List<WalletEntity>): WalletsState {
        return this.copy(wallets = wallets)
    }

    fun setError(message: UiString?): WalletsState {
        return this.copy(error = message)
    }
}