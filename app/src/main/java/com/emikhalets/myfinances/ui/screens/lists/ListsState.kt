package com.emikhalets.myfinances.ui.screens.lists

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet

data class ListsState(
    val categoriesExpense: List<Category>,
    val categoriesIncome: List<Category>,
    val wallets: List<Wallet>,
    val loading: Boolean,
    val error: Exception?
) {

    constructor() : this(
        categoriesExpense = emptyList(),
        categoriesIncome = emptyList(),
        wallets = emptyList(),
        loading = false,
        error = null
    )

    fun setLoadedCategoriesExpense(list: List<Category>): ListsState {
        return this.copy(categoriesExpense = list)
    }

    fun setLoadedCategoriesIncome(list: List<Category>): ListsState {
        return this.copy(categoriesIncome = list)
    }

    fun setLoadedWallets(list: List<Wallet>): ListsState {
        return this.copy(wallets = list)
    }

    fun setCommonError(exception: Exception): ListsState {
        return this.copy(
            error = exception,
            loading = false
        )
    }

    fun setLoading(): ListsState {
        return this.copy(
            loading = true,
            error = null
        )
    }

    fun setStopLoading(): ListsState {
        return this.copy(loading = false)
    }

    fun errorMessage(): String = error?.message.toString()
}