package com.emikhalets.myfinances.ui.screens.new_transaction

import com.emikhalets.myfinances.data.entity.Category

data class NewTransactionState(
    val categories: List<Category> = emptyList(),
    val savedCategory: Boolean = false,
    val savedTransaction: Boolean = false,
    val error: Exception? = null
) {

    fun setCategories(data: List<Category>): NewTransactionState {
        return this.copy(
            categories = data,
            savedCategory = false,
            error = null
        )
    }

    fun setCategorySaved(): NewTransactionState {
        return this.copy(
            savedCategory = true,
            error = null
        )
    }

    fun setTransactionSaved(): NewTransactionState {
        return this.copy(
            savedTransaction = true,
            error = null
        )
    }

    fun setError(exception: Exception): NewTransactionState {
        return this.copy(error = exception)
    }
}