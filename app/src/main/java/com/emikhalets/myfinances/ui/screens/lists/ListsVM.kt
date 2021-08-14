package com.emikhalets.myfinances.ui.screens.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
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

    fun getData() {
        viewModelScope.launch {
            state = state.setLoading()
            // Loading expense categories
            state = when (val result = repo.getCategories(TransactionType.Expense.value)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedCategoriesExpense(result.data)
            }
            // Loading income categories
            state = when (val result = repo.getCategories(TransactionType.Income.value)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedCategoriesIncome(result.data)
            }
            // Loading wallets
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedWallets(result.data)
            }
            state = state.setStopLoading()
        }
    }
}