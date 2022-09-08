package com.emikhalets.myfinances.presentation.screens.transactions

import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionWithCategory

data class TransactionsState(
    val transactions: List<TransactionWithCategory> = emptyList(),
    val incomeList: List<Transaction> = emptyList(),
    val expenseList: List<Transaction> = emptyList(),
    val error: Exception? = null
) {

    fun setTransactions(data: List<TransactionWithCategory>): TransactionsState {
        return this.copy(
            transactions = data,
            error = null
        )
    }

    fun setError(exception: Exception): TransactionsState {
        return this.copy(error = exception)
    }
}