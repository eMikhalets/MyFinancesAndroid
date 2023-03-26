package com.emikhalets.presentation.screens.categories

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity

data class CategoriesState(
    val expenseList: List<CategoryEntity> = emptyList(),
    val incomeList: List<CategoryEntity> = emptyList(),
    val error: UiString? = null,
) {

    fun setCategories(
        expenseList: List<CategoryEntity>,
        incomeList: List<CategoryEntity>,
    ): CategoriesState {
        return this.copy(expenseList = expenseList, incomeList = incomeList)
    }

    fun setError(message: UiString?): CategoriesState {
        return this.copy(error = message)
    }
}