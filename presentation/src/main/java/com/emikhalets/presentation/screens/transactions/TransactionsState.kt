package com.emikhalets.presentation.screens.transactions

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.utils.TransactionsListEntity

data class TransactionsState(
    val incomeSum: Double = 0.0,
    val expensesSum: Double = 0.0,
    val totalSum: Double = 0.0,
    val transactionsList: List<TransactionsListEntity> = emptyList(),
    val error: UiString? = null,
) {

    fun setTransactions(
        incomeSum: Double,
        expensesSum: Double,
        totalSum: Double,
        transactionsList: List<TransactionsListEntity>,
    ): TransactionsState {
        return this.copy(
            incomeSum = incomeSum,
            expensesSum = expensesSum,
            totalSum = totalSum,
            transactionsList = transactionsList
        )
    }

    fun setError(message: UiString?): TransactionsState {
        return this.copy(error = message)
    }
}