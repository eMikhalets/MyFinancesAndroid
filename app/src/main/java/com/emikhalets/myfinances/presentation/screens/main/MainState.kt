package com.emikhalets.myfinances.presentation.screens.main

import com.emikhalets.myfinances.data.entity.Transaction

data class MainState(
    val transactions: List<TransactionWithCategory> = emptyList(),
    val incomeList: List<Transaction> = emptyList(),
    val expenseList: List<Transaction> = emptyList(),
    val error: String = "",
) {

    fun setTransactions(data: List<TransactionWithCategory>): MainState {
        return this.copy(transactions = data, error = "")
    }

    fun setError(exception: Exception): MainState {
        return this.copy(error = "")
    }
}