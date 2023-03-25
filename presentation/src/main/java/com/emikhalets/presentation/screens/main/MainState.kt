package com.emikhalets.presentation.screens.main

import com.emikhalets.core.UiString

data class MainState(
    val walletName: String? = null,
    val incomeSum: Double? = null,
    val expenseSum: Double? = null,
    val error: UiString? = null,
) {

    fun setWalletInfo(walletName: String, incomeSum: Double, expenseSum: Double): MainState {
        return this.copy(walletName = walletName, incomeSum = incomeSum, expenseSum = expenseSum)
    }

    fun setError(message: UiString?): MainState {
        return this.copy(error = message)
    }
}