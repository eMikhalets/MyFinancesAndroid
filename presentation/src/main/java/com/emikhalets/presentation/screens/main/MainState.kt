package com.emikhalets.presentation.screens.main

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity

data class MainState(
    val complexWallet: ComplexWalletEntity? = null,
    val lastTransactions: List<ComplexTransactionEntity> = emptyList(),
    val incomeSum: Double? = null,
    val expenseSum: Double? = null,
    val error: UiString? = null,
) {

    fun setWalletInfo(
        complexWallet: ComplexWalletEntity,
        incomeSum: Double,
        expenseSum: Double,
    ): MainState {
        return this.copy(complexWallet = complexWallet,
            incomeSum = incomeSum,
            expenseSum = expenseSum)
    }

    fun setLastTransactions(list: List<ComplexTransactionEntity>): MainState {
        return this.copy(lastTransactions = list)
    }

    fun setError(message: UiString?): MainState {
        return this.copy(error = message)
    }
}