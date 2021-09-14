package com.emikhalets.myfinances.ui.screens.transaction_details

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.TransactionWithCategory

data class TransactionDetailsState(
    val categories: List<Category> = emptyList(),
    val transaction: TransactionWithCategory? = null,
    val savedCategory: Boolean = false,
    val savedTransaction: Boolean = false,
    val deletedTransaction: Boolean = false,
    val error: Exception? = null
) {

    fun setTransaction(data: TransactionWithCategory): TransactionDetailsState {
        return this.copy(
            transaction = data,
            savedCategory = false,
            savedTransaction = false,
            deletedTransaction = false,
            error = null
        )
    }

    fun setCategories(data: List<Category>): TransactionDetailsState {
        return this.copy(
            categories = data,
            savedCategory = false,
            savedTransaction = false,
            deletedTransaction = false,
            error = null
        )
    }

    fun setCategorySaved(): TransactionDetailsState {
        return this.copy(
            savedCategory = true,
            error = null
        )
    }

    fun setTransactionSaved(): TransactionDetailsState {
        return this.copy(
            savedTransaction = true,
            error = null
        )
    }

    fun setTransactionDeleted(): TransactionDetailsState {
        return this.copy(
            deletedTransaction = true,
            error = null
        )
    }

    fun setError(exception: Exception): TransactionDetailsState {
        return this.copy(error = exception)
    }
}