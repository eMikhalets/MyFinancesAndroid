package com.emikhalets.presentation.screens.currencies

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CurrencyEntity

data class CurrenciesState(
    val currencies: List<CurrencyEntity> = emptyList(),
    val error: UiString? = null,
) {

    fun setCurrencies(currencies: List<CurrencyEntity>): CurrenciesState {
        return this.copy(currencies = currencies)
    }

    fun setError(message: UiString?): CurrenciesState {
        return this.copy(error = message)
    }
}