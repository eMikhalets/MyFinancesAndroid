package com.emikhalets.myfinances.presentation.screens.categories

import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class CategoriesState(
    val incomeList: List<Category> = emptyList(),
    val expenseList: List<Category> = emptyList(),
    val error: String = "",
) {

    fun setCategories(incomeList: List<Category>, expenseList: List<Category>): CategoriesState {
        return this.copy(incomeList = incomeList, expenseList = expenseList)
    }

    fun setError(message: String?): CategoriesState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}