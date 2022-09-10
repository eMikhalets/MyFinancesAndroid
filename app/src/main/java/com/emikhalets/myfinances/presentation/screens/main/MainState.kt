package com.emikhalets.myfinances.presentation.screens.main

import com.emikhalets.myfinances.data.entity.TransactionEntity

data class MainState(
    val incomeList: List<TransactionEntity> = emptyList(),
    val expenseList: List<TransactionEntity> = emptyList(),
    val error: String = "",
) {

    fun setTransactions(
        incomeList: List<TransactionEntity>,
        expenseList: List<TransactionEntity>,
    ): MainState {
        return this.copy(incomeList = incomeList, expenseList = expenseList, error = "")
    }

    fun setError(message: String): MainState {
        return this.copy(error = message)
    }
}