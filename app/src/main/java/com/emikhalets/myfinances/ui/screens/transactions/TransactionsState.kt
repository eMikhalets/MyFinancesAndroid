package com.emikhalets.myfinances.ui.screens.transactions

import com.emikhalets.myfinances.data.entity.Transaction

data class TransactionsState(
    val incomeList: List<Transaction>,
    val expenseList: List<Transaction>,
    val error: Exception?
) {

    constructor() : this(
        incomeList = emptyList(),
        expenseList = emptyList(),
        error = null
    )

    fun setLoadedIncome(list: List<Transaction>): TransactionsState {
        return this.copy(incomeList = list)
    }

    fun setLoadedExpense(list: List<Transaction>): TransactionsState {
        return this.copy(expenseList = list)
    }

    fun setCommonError(exception: Exception): TransactionsState {
        return this.copy(error = exception)
    }
}