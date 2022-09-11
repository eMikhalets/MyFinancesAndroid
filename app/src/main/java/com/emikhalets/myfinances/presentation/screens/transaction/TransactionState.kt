package com.emikhalets.myfinances.presentation.screens.transaction

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class TransactionState(
    val categories: List<Category> = emptyList(),
    val entity: TransactionEntity? = null,
    val transactionSaved: Boolean = false,
    val transactionDeleted: Boolean = false,
    val error: String = "",
) {

    fun setTransaction(data: TransactionEntity): TransactionState {
        return this.copy(entity = data, error = "")
    }

    fun setCategories(data: List<Category>): TransactionState {
        return this.copy(categories = data, error = "")
    }

    fun setTransactionSaved(): TransactionState {
        return this.copy(transactionSaved = true, error = "")
    }

    fun setTransactionDeleted(): TransactionState {
        return this.copy(transactionDeleted = true, error = "")
    }

    fun setError(message: String?): TransactionState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}