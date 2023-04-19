package com.emikhalets.presentation.screens.wallet_edit

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.WalletEntity

data class WalletEditState(
    val wallet: WalletEntity? = null,
    val currencies: List<CurrencyEntity> = emptyList(),
    val existed: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) {

    fun setWallet(wallet: WalletEntity): WalletEditState {
        return this.copy(wallet = wallet)
    }

    fun setCurrencies(list: List<CurrencyEntity>): WalletEditState {
        return this.copy(currencies = list)
    }

    fun setSaved(saved: Boolean = true): WalletEditState {
        return this.copy(saved = saved)
    }

    fun setDeleted(deleted: Boolean = true): WalletEditState {
        return this.copy(deleted = deleted)
    }

    fun setExisted(existed: Boolean = true): WalletEditState {
        return this.copy(existed = existed)
    }

    fun setError(message: UiString?): WalletEditState {
        return this.copy(error = message)
    }
}