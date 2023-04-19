package com.emikhalets.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.Prefs
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import com.emikhalets.domain.use_case.transaction.GetTransactionsUseCase
import com.emikhalets.domain.use_case.wallet.GetComplexWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getComplexWalletUseCase: GetComplexWalletUseCase,
    private val getComplexTransactionsUseCase: GetTransactionsUseCase,
    private val prefs: Prefs,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getCurrentWalletInfo() {
        viewModelScope.launch {
            when (val result = getComplexWalletUseCase.invoke(prefs.currentWalletId)) {
                is ResultWrapper.Success -> setWalletInfoState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    fun getLastTransactions() {
        viewModelScope.launch {
            when (val result = getComplexTransactionsUseCase.invoke(prefs.currentWalletId)) {
                is ResultWrapper.Success -> setLastTransactions(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun setWalletInfoState(flow: Flow<ComplexWalletEntity>?) {
        flow?.collectLatest { entity ->
            val incomesSum = entity.transactions
                .filter { it.type == TransactionType.Income }
                .sumOf { it.value }
            val expensesSum = entity.transactions
                .filter { it.type == TransactionType.Expense }
                .sumOf { it.value }
            _state.update { _state.value.setWalletInfo(entity, incomesSum, expensesSum) }
        }
    }

    private suspend fun setLastTransactions(flow: Flow<List<ComplexTransactionEntity>>?) {
        flow?.collectLatest { list ->
            val lastTransactions = list.take(20)
            _state.update { _state.value.setLastTransactions(lastTransactions) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}