package com.emikhalets.myfinances.ui.screens.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.utils.getMaxTSOfMonth
import com.emikhalets.myfinances.utils.getMinTSOfMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionsVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(TransactionsState())
        private set

    fun getTransactions(date: Long, wallet: Long) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val start = calendar.getMinTSOfMonth(calendar.timeInMillis)
            val end = calendar.getMaxTSOfMonth(calendar.timeInMillis)
            state = when (val result = repo.getTransactionsBetween(start, end, wallet)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setTransactions(result.data)
            }
        }
    }
}