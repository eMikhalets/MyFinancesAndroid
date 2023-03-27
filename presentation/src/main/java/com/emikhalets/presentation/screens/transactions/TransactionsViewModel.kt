package com.emikhalets.presentation.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.use_case.transaction.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getTransactions() {
        viewModelScope.launch {
            when (val result = getTransactionsUseCase.invoke()) {
                is ResultWrapper.Success -> setTransactionsState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun setTransactionsState(flow: Flow<List<TransactionEntity>>?) {
        flow ?: return
        flow.collect { transactions ->
            val expenses = transactions.filter { it.type == TransactionType.Expense }
            val incomes = transactions.filter { it.type == TransactionType.Income }
            _state.update { _state.value.setTransactions(expenses, incomes) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}