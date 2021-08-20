package com.emikhalets.myfinances.ui.screens.lists

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet

data class ListsState(
    val categoriesExpense: List<Category> = emptyList(),
    val categoriesIncome: List<Category> = emptyList(),
    val wallets: List<Wallet> = emptyList(),
    val savedCategoryExpense: Boolean = false,
    val savedCategoryIncome: Boolean = false,
    val savedWallet: Boolean = false,
    val errorCategorySaved: Boolean = false,
    val error: Exception? = null
) {

    fun errorMessage(): String {
        return error?.message ?: ""
    }
}