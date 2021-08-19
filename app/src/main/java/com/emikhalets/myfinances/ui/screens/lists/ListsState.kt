package com.emikhalets.myfinances.ui.screens.lists

import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.enums.TransactionType

data class ListsState(
    val categoriesExpense: List<Category>,
    val categoriesIncome: List<Category>,
    val wallets: List<Wallet>,
    val savedCategoryExpense: Boolean,
    val savedCategoryIncome: Boolean,
    val savedWallet: Boolean,
    val errorCategorySaved: Boolean,
    val error: Exception?
) {

    constructor() : this(
        categoriesExpense = emptyList(),
        categoriesIncome = emptyList(),
        wallets = emptyList(),
        savedCategoryExpense = false,
        savedCategoryIncome = false,
        savedWallet = false,
        errorCategorySaved = false,
        error = null
    )

    fun setLoadedCategoriesExpense(list: List<Category>): ListsState {
        return this.copy(
            categoriesExpense = list,
            savedCategoryExpense = false
        )
    }

    fun setLoadedCategoriesIncome(list: List<Category>): ListsState {
        return this.copy(
            categoriesIncome = list,
            savedCategoryIncome = false
        )
    }

    fun setLoadedWallets(list: List<Wallet>): ListsState {
        return this.copy(
            wallets = list,
            savedWallet = false
        )
    }

    fun setWalletSaved(): ListsState {
        return this.copy(savedWallet = true)
    }

    fun setCategorySaved(type: TransactionType): ListsState {
        return when (type) {
            TransactionType.Expense -> this.copy(savedCategoryExpense = true)
            TransactionType.Income -> this.copy(savedCategoryIncome = true)
            TransactionType.None -> this
        }
    }

    fun setErrorCommon(exception: Exception): ListsState {
        return this.copy(error = exception)
    }

    fun errorMessage(): String = error?.message.toString()
}