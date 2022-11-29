package com.emikhalets.myfinances.presentation.screens.main

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun getAllTransactions() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransactions()
                .onSuccess { flow -> setTransactionsState(flow) }
                .onFailure { throwable -> setErrorState(throwable) }
        }
    }

    private suspend fun setTransactionsState(flow: Flow<List<Transaction>>) {
        flow.collect { transactions ->
            val incomes = transactions
                .filter { it.type == TransactionType.Income }
                .sumOf { it.value }
            val expenses = transactions
                .filter { it.type == TransactionType.Expense }
                .sumOf { it.value }
            _state.update { _state.value.setTransactions(incomes, expenses) }
        }
    }

    private fun setErrorState(throwable: Throwable) {
        _state.update { _state.value.setError(throwable.message) }
    }

    data class MainState(
        val incomeValue: Double = 0.0,
        val expenseValue: Double = 0.0,
        val error: String = "",
    ) {

        fun setTransactions(incomeValue: Double, expenseValue: Double): MainState {
            return this.copy(incomeValue = incomeValue, expenseValue = expenseValue)
        }

        fun setError(message: String?): MainState {
            return this.copy(error = message ?: DEFAULT_ERROR)
        }
    }
}