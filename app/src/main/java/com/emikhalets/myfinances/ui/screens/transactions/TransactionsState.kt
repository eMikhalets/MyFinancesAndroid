package com.emikhalets.myfinances.ui.screens.transactions

import com.emikhalets.myfinances.data.entity.Transaction

data class TransactionsState(
    val incomeList: List<Transaction> = emptyList(),
    val expenseList: List<Transaction> = emptyList(),
    val error: Exception? = null
) {

    fun errorMessage(): String {
        return error?.message ?: ""
    }
}