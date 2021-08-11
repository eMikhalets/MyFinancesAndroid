package com.emikhalets.myfinances.ui.screens.new_transaction

import com.emikhalets.myfinances.data.entity.Category

sealed class NewTransactionState {
    object Idle : NewTransactionState()
    object Loading : NewTransactionState()
    data class Categories(val list: List<Category>) : NewTransactionState()
    data class Error(val exception: Exception, val message: String = exception.message ?: "") : NewTransactionState()
}