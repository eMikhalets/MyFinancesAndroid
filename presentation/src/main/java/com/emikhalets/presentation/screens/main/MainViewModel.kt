package com.emikhalets.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.Prefs
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.complex.ComplexWalletEntity
import com.emikhalets.domain.use_case.wallet.GetComplexWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getComplexWalletUseCase: GetComplexWalletUseCase,
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

    private fun setWalletInfoState(entity: ComplexWalletEntity?) {
        entity ?: return
        val walletName = entity.wallet.name
        val incomesSum = entity.transactions
            .filter { it.type == TransactionType.Income }
            .sumOf { it.value }
        val expensesSum = entity.transactions
            .filter { it.type == TransactionType.Expense }
            .sumOf { it.value }
        _state.update { _state.value.setWalletInfo(walletName, incomesSum, expensesSum) }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}