package com.emikhalets.myfinances.presentation.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.repository.AppRepository
import com.emikhalets.myfinances.domain.entity.TransactionEntity
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
class TransactionsViewModel @Inject constructor(
    private val repo: AppRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun getIncomeTransactions() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransactionsByType(TransactionType.Income)
                .onSuccess { flow -> setIncomeTransactions(flow) }
                .onFailure { throwable -> setErrorState(throwable) }
        }
    }

    fun getExpenseTransactions() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransactionsByType(TransactionType.Expense)
                .onSuccess { flow -> setExpenseTransactions(flow) }
                .onFailure { throwable -> setErrorState(throwable) }
        }
    }

    private suspend fun setIncomeTransactions(flow: Flow<List<TransactionEntity>>) {
        flow.collect { transactions ->
            _state.update { _state.value.setIncomes(transactions) }
        }
    }

    private suspend fun setExpenseTransactions(flow: Flow<List<TransactionEntity>>) {
        flow.collect { transactions ->
            _state.update { _state.value.setExpenses(transactions) }
        }
    }

    private fun setErrorState(throwable: Throwable) {
        _state.update { _state.value.setError(throwable.message) }
    }

    data class State(
        val incomes: List<TransactionEntity> = emptyList(),
        val expenses: List<TransactionEntity> = emptyList(),
        val error: String = "",
    ) {

        fun setIncomes(incomes: List<TransactionEntity>): State {
            return this.copy(incomes = incomes)
        }

        fun setExpenses(expenses: List<TransactionEntity>): State {
            return this.copy(expenses = expenses)
        }

        fun setError(message: String?): State {
            return this.copy(error = message ?: DEFAULT_ERROR)
        }
    }
}