package com.emikhalets.myfinances.ui.screens.transactions

import com.emikhalets.myfinances.data.entity.Transaction

data class TransactionsState(
    val incomeList: List<Transaction>,
    val expenseList: List<Transaction>,
    val loading: Boolean,
    val error: Exception?
) {

    constructor() : this(
        incomeList = emptyList(),
        expenseList = emptyList(),
        loading = false,
        error = null
    )

    fun setLoadedIncome(list: List<Transaction>): TransactionsState {
        return this.copy(
            loading = false,
            incomeList = list
        )
    }

    fun setLoadedExpense(list: List<Transaction>): TransactionsState {
        return this.copy(
            loading = false,
            expenseList = list
        )
    }

    fun setCommonError(exception: Exception): TransactionsState {
        return this.copy(
            error = exception,
            loading = false
        )
    }

    fun setLoading(): TransactionsState {
        return this.copy(
            loading = true,
            error = null
        )
    }

    fun errorMessage(): String = error?.message.toString()
}