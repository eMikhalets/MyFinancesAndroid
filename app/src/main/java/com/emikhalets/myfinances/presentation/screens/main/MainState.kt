package com.emikhalets.myfinances.presentation.screens.main

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class MainState(
    val incomeList: List<TransactionEntity> = emptyList(),
    val expenseList: List<TransactionEntity> = emptyList(),
    val categories: List<Category> = emptyList(),
    val error: String = "",
) {

    fun setTransactions(
        incomeList: List<TransactionEntity>,
        expenseList: List<TransactionEntity>,
    ): MainState {
        return this.copy(incomeList = incomeList, expenseList = expenseList)
    }

    fun setCategories(categories: List<Category>): MainState {
        return this.copy(categories = categories)
    }

    fun setError(message: String?): MainState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}