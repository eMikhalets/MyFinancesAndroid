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
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setLoadedWallets(result.data)
            }
        }
    }

    fun getCategoriesExpense() {
        viewModelScope.launch {
            state = when (val result = repo.getCategories(TransactionType.Expense.value)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setLoadedCategoriesExpense(result.data)
            }
        }
    }

    fun getCategoriesIncome() {
        viewModelScope.launch {
            state = when (val result = repo.getCategories(TransactionType.Income.value)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setLoadedCategoriesIncome(result.data)
            }
        }
    }

    fun saveCategory(type: TransactionType, name: String, icon: Int) {
        viewModelScope.launch {
            val category = Category(name, type.value, icon)
            state = when (val result = repo.insertCategory(category)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setCategorySaved(type)
            }
        }
    }

    fun saveWallet(name: String, value: Double) {
        viewModelScope.launch {
            val wallet = Wallet(name, value)
            state = when (val result = repo.insertWallet(wallet)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setWalletSaved()
            }
        }
    }
}