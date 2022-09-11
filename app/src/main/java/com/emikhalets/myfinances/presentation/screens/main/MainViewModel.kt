package com.emikhalets.myfinances.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun getTransactions() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransactions()
                .onSuccess { setTransactionsState(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    private suspend fun setTransactionsState(flow: Flow<List<TransactionEntity>>) {
        flow.collect { transactions ->
            val expenseList = transactions.filter { it.transaction.type == TransactionType.Expense }
            val incomeList = transactions.filter { it.transaction.type == TransactionType.Income }
            state = state.setTransactions(incomeList, expenseList)
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getCategories()
                .onSuccess { flow -> flow.collect { state = state.setCategories(it) } }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun getTransaction(id: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransaction(id)
                .onSuccess { state = state.setTransaction(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.Default) {
            val request = if (transaction.id == 0L) {
                repo.insertTransaction(transaction)
            } else {
                repo.updateTransaction(transaction)
            }
            request.onFailure { state = state.setError(it.message) }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.deleteTransaction(transaction)
                .onFailure { state = state.setError(it.message) }
        }
    }
}