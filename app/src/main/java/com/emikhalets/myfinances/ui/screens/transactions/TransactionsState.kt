package com.emikhalets.myfinances.ui.screens.transactions

import com.emikhalets.myfinances.data.entity.Transaction

sealed class TransactionsState {
    object Idle : TransactionsState()
    object Loading : TransactionsState()
    object EmptyTransactions : TransactionsState()
    data class Transactions(val list: List<Transaction>) : TransactionsState()
    data class Error(val exception: Exception, val message: String = exception.message ?: "") : TransactionsState()
}