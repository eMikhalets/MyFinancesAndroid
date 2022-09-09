package com.emikhalets.myfinances.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.utils.DEFAULT_ERROR
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val transactionsFlow = flow<List<Transaction>> {
    }

    fun getTransactions() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransactions()
                .onSuccess { setTransactionsState(it) }
                .onFailure { state = state.setError(it.message ?: DEFAULT_ERROR) }
        }
    }

    private suspend fun setTransactionsState(flow: Flow<List<Transaction>>) {
        flow.collect { transactions ->
            val expenseList = transactions.filter { it.type == TransactionType.Expense }
            val incomeList = transactions.filter { it.type == TransactionType.Income }
            state = state.setTransactions(incomeList, expenseList)
        }
    }
}