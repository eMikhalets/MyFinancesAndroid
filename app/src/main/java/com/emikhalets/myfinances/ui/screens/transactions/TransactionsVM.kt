package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(TransactionsState())
        private set

    fun getExpenseTransactions() {
        viewModelScope.launch {
            state = when (val result = repo.getExpenseTransactions()) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    expenseList = result.data,
                    error = null
                )
            }
        }
    }

    fun getIncomeTransactions() {
        viewModelScope.launch {
            state = when (val result = repo.getIncomeTransactions()) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    incomeList = result.data,
                    error = null
                )
            }
        }
    }
}