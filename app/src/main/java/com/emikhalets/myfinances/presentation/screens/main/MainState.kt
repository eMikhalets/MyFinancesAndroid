package com.emikhalets.myfinances.presentation.screens.main

import com.emikhalets.myfinances.data.entity.Transaction

data class MainState(
    val incomeList: List<Transaction> = emptyList(),
    val expenseList: List<Transaction> = emptyList(),
    val error: String = "",
) {

    fun setTransactions(incomeList: List<Transaction>, expenseList: List<Transaction>): MainState {
        return this.copy(incomeList = incomeList, expenseList = expenseList, error = "")
    }

    fun setError(message: String): MainState {
        return this.copy(error = message)
    }
}