package com.emikhalets.myfinances.ui.screens.summary

import com.emikhalets.myfinances.data.entity.Transaction

data class SummaryState(
    val incomeList: List<Transaction>,
    val expenseList: List<Transaction>,
    val error: Exception?
) {

    constructor() : this(
        incomeList = emptyList(),
        expenseList = emptyList(),
        error = null
    )

    fun setLoadedIncome(list: List<Transaction>): SummaryState {
        return this.copy(incomeList = list)
    }

    fun setLoadedExpense(list: List<Transaction>): SummaryState {
        return this.copy(expenseList = list)
    }

    fun setCommonError(exception: Exception): SummaryState {
        return this.copy(error = exception)
    }
}