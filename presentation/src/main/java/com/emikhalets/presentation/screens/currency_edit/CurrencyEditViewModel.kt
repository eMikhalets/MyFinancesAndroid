package com.emikhalets.presentation.screens.currency_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.CODE_CURRENCY_EXISTED
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.use_case.currency.AddCurrencyUseCase
import com.emikhalets.domain.use_case.currency.DeleteCurrencyUseCase
import com.emikhalets.domain.use_case.currency.GetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyEditViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyEditState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun dropCurrencyExisted() {
        _state.update { _state.value.setCurrencyExisted(false) }
    }

    fun getCurrency(id: Long) {
        viewModelScope.launch {
            when (val result = getCurrencyUseCase.invoke(id)) {
                is ResultWrapper.Success -> setCurrencyState(result.data)
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    fun saveCurrency(entity: CurrencyEntity) {
        viewModelScope.launch {
            when (val result = addCurrencyUseCase.invoke(entity)) {
                is ResultWrapper.Success -> _state.update { _state.value.setCurrencySaved() }
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    fun deleteCurrency() {
        viewModelScope.launch {
            val entity = _state.value.currency
            if (entity != null) {
                when (val result = deleteCurrencyUseCase.invoke(entity)) {
                    is ResultWrapper.Success -> _state.update { _state.value.setCurrencyDeleted() }
                    is ResultWrapper.Error -> setErrorState(result.code, result.message)
                }
            }
        }
    }

    private suspend fun setCurrencyState(flow: Flow<CurrencyEntity>?) {
        flow ?: return
        flow.collectLatest { category ->
            _state.update { _state.value.setCurrency(category) }
        }
    }

    private fun setErrorState(code: Int, message: UiString) {
        when (code) {
            CODE_CURRENCY_EXISTED -> _state.update { _state.value.setCurrencyExisted() }
            else -> _state.update { _state.value.setError(message) }
        }

    }
}