package com.emikhalets.presentation.screens.currency_edit

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity

data class CurrencyEditState(
    val currency: CurrencyEntity? = null,
    val existed: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) {

    fun setCurrency(currency: CurrencyEntity): CurrencyEditState {
        return this.copy(currency = currency)
    }

    fun setCurrencySaved(saved: Boolean = true): CurrencyEditState {
        return this.copy(saved = saved)
    }

    fun setCurrencyDeleted(deleted: Boolean = true): CurrencyEditState {
        return this.copy(deleted = deleted)
    }

    fun setCurrencyExisted(existed: Boolean = true): CurrencyEditState {
        return this.copy(existed = existed)
    }

    fun setError(message: UiString?): CurrencyEditState {
        return this.copy(error = message)
    }
}