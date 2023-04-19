package com.emikhalets.presentation.screens.transactions

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.TransactionEntity

data class TransactionsState(
    val expenseList: List<TransactionEntity> = emptyList(),
    val incomeList: List<TransactionEntity> = emptyList(),
    val error: UiString? = null,
) {

    fun setTransactions(
        expenseList: List<TransactionEntity>,
        incomeList: List<TransactionEntity>,
    ): TransactionsState {
        return this.copy(expenseList = expenseList, incomeList = incomeList)
    }

    fun setError(message: UiString?): TransactionsState {
        return this.copy(error = message)
    }
}