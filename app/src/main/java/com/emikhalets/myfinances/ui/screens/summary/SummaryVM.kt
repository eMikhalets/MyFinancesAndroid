package com.emikhalets.myfinances.ui.screens.summary

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
class SummaryVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(SummaryState())
        private set

    fun getTransactions() {
        viewModelScope.launch {
            // Loading expense transactions
            state = when (val result = repo.getExpenseTransactions()) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    expenseList = result.data,
                    error = null
                )
            }
            // Loading income transactions
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