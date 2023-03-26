package com.emikhalets.presentation.screens.category_edit

import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity

data class CategoryEditState(
    val category: CategoryEntity? = null,
    val categoryExisted: Boolean = false,
    val categorySaved: Boolean = false,
    val categoryDeleted: Boolean = false,
    val error: UiString? = null,
) {

    fun setCategory(category: CategoryEntity): CategoryEditState {
        return this.copy(category = category)
    }

    fun setCategorySaved(saved: Boolean = true): CategoryEditState {
        return this.copy(categorySaved = saved)
    }

    fun setCategoryDeleted(deleted: Boolean = true): CategoryEditState {
        return this.copy(categoryDeleted = deleted)
    }

    fun setCategoryExisted(existed: Boolean = true): CategoryEditState {
        return this.copy(categoryExisted = existed)
    }

    fun setError(message: UiString?): CategoryEditState {
        return this.copy(error = message)
    }
}