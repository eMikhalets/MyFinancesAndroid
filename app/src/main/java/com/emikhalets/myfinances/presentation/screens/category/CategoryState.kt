package com.emikhalets.myfinances.presentation.screens.category

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.utils.DEFAULT_ERROR

data class CategoryState(
    val category: Category? = null,
    val categorySaved: Boolean = false,
    val categoryDeleted: Boolean = false,
    val error: String = "",
) {

    fun setCategory(category: Category): CategoryState {
        return this.copy(category = category, error = "")
    }

    fun setCategorySaved(): CategoryState {
        return this.copy(categorySaved = true, error = "")
    }

    fun setCategoryDeleted(): CategoryState {
        return this.copy(categoryDeleted = true, error = "")
    }

    fun setError(message: String?): CategoryState {
        return this.copy(error = message ?: DEFAULT_ERROR)
    }
}