package com.emikhalets.myfinances.ui.screens.lists

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet

data class ListsState(
    val categoriesExpense: List<Category>,
    val categoriesIncome: List<Category>,
    val wallets: List<Wallet>,
    val categoryExpenseSaved: Boolean,
    val categoryIncomeSaved: Boolean,
    val walletSaved: Boolean,
    val loading: Boolean,
    val errorCategorySaved: Boolean,
    val error: Exception?
) {

    constructor() : this(
        categoriesExpense = emptyList(),
        categoriesIncome = emptyList(),
        wallets = emptyList(),
        categoryExpenseSaved = false,
        categoryIncomeSaved = false,
        walletSaved = false,
        loading = false,
        errorCategorySaved = false,
        error = null
    )

    fun setLoadedCategoriesExpense(list: List<Category>): ListsState {
        return this.copy(
            categoriesExpense = list,
            categoryExpenseSaved = false
        )
    }

    fun setLoadedCategoriesIncome(list: List<Category>): ListsState {
        return this.copy(
            categoriesIncome = list,
            categoryIncomeSaved = false
        )
    }

    fun setLoadedWallets(list: List<Wallet>): ListsState {
        return this.copy(
            wallets = list,
            walletSaved = false
        )
    }

    fun setCategorySaved(): ListsState {
        return this.copy(categoryExpenseSaved = true)
    }

    fun setErrorCommon(exception: Exception): ListsState {
        return this.copy(error = exception)
    }

    fun setLoading(): ListsState {
        return this.copy(
            loading = true,
            errorCategorySaved = false,
            error = null
        )
    }

    fun setStopLoading(): ListsState {
        return this.copy(loading = false)
    }

    fun errorMessage(): String = error?.message.toString()
}