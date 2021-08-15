package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Category
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
            state = state.setLoading()
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setLoadedWallets(result.data)
            }
            state = state.setStopLoading()
        }
    }

    fun getCategoriesExpense() {
        viewModelScope.launch {
            state = state.setLoading()
            state = when (val result = repo.getCategories(TransactionType.Expense.value)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setLoadedCategoriesExpense(result.data)
            }
            state = state.setStopLoading()
        }
    }

    fun getCategoriesIncome() {
        viewModelScope.launch {
            state = state.setLoading()
            state = when (val result = repo.getCategories(TransactionType.Income.value)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setLoadedCategoriesIncome(result.data)
            }
            state = state.setStopLoading()
        }
    }

    fun saveCategory(type: TransactionType, name: String) {
        viewModelScope.launch {
            state = state.setLoading()
            val category = Category(name, type.value, 1)
            state = when (val result = repo.insertCategory(category)) {
                is Result.Error -> state.setErrorCommon(result.exception)
                is Result.Success -> state.setCategorySaved()
            }
            state = state.setStopLoading()
        }
    }
}