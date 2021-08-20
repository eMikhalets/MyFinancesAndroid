package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(ListsState())
        private set

    fun getWallets() {
        viewModelScope.launch {
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    wallets = result.data,
                    savedWallet = false,
                    error = null
                )
            }
        }
    }

    fun getCategoriesExpense() {
        viewModelScope.launch {
            state = when (val result = repo.getCategories(TransactionType.Expense.value)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    categoriesExpense = result.data,
                    savedCategoryExpense = false,
                    error = null
                )
            }
        }
    }

    fun getCategoriesIncome() {
        viewModelScope.launch {
            state = when (val result = repo.getCategories(TransactionType.Income.value)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    categoriesIncome = result.data,
                    savedCategoryIncome = false,
                    error = null
                )
            }
        }
    }

    fun saveCategory(type: TransactionType, name: String, icon: Int) {
        viewModelScope.launch {
            val category = Category(name, type.value, icon)
            state = when (val result = repo.insertCategory(category)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> {
                    when (type) {
                        TransactionType.Expense -> state.copy(
                            savedCategoryExpense = true,
                            error = null
                        )
                        TransactionType.Income -> state.copy(
                            savedCategoryIncome = true,
                            error = null
                        )
                        TransactionType.None -> state
                    }
                }
            }
        }
    }

    fun saveWallet(name: String, value: Double) {
        viewModelScope.launch {
            val wallet = Wallet(name, value)
            state = when (val result = repo.insertWallet(wallet)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    savedWallet = true,
                    error = null
                )
            }
        }
    }
}