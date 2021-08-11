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

    var state by mutableStateOf<TransactionsState>(TransactionsState.Idle)
        private set

    fun getTransactions() {
        viewModelScope.launch {
            state = TransactionsState.Loading
            state = when (val result = repo.getTransactions()) {
                is Result.Error -> TransactionsState.Error(result.exception)
                is Result.Success -> {
                    if (result.data.isEmpty()) TransactionsState.EmptyTransactions
                    else TransactionsState.Transactions(result.data)
                }
            }
        }
    }
}