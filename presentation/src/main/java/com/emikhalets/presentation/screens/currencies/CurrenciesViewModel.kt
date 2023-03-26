package com.emikhalets.presentation.screens.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.use_case.currency.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CurrenciesState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getCurrencies() {
        viewModelScope.launch {
            when (val result = getCurrenciesUseCase.invoke()) {
                is ResultWrapper.Success -> setCurrenciesState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun setCurrenciesState(flow: Flow<List<CurrencyEntity>>?) {
        flow ?: return
        flow.collectLatest { currencies ->
            _state.update { _state.value.setCurrencies(currencies) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}