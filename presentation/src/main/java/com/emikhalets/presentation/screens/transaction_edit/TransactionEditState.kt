package com.emikhalets.presentation.screens.transaction_edit

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.TransactionEntity

data class TransactionEditState(
    val transaction: TransactionEntity? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) {

    fun setTransaction(transaction: TransactionEntity): TransactionEditState {
        return this.copy(transaction = transaction)
    }

    fun setCategories(categories: List<CategoryEntity>): TransactionEditState {
        return this.copy(categories = categories)
    }

    fun setTransactionSaved(saved: Boolean = true): TransactionEditState {
        return this.copy(saved = saved)
    }

    fun setTransactionDeleted(deleted: Boolean = true): TransactionEditState {
        return this.copy(deleted = deleted)
    }

    fun setError(message: UiString?): TransactionEditState {
        return this.copy(error = message)
    }
}