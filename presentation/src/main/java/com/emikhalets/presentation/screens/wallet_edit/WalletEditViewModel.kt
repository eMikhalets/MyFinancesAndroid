package com.emikhalets.presentation.screens.wallet_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.CODE_WALLET_EXISTED
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.use_case.currency.GetCurrenciesUseCase
import com.emikhalets.domain.use_case.wallet.AddWalletUseCase
import com.emikhalets.domain.use_case.wallet.DeleteWalletUseCase
import com.emikhalets.domain.use_case.wallet.GetWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletEditViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val addWalletUseCase: AddWalletUseCase,
    private val deleteWalletUseCase: DeleteWalletUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WalletEditState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun dropExisted() {
        _state.update { _state.value.setExisted(false) }
    }

    fun getWallet(id: Long) {
        viewModelScope.launch {
            when (val result = getWalletUseCase.invoke(id)) {
                is ResultWrapper.Success -> setWalletState(result.data)
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    fun getCurrencies() {
        viewModelScope.launch {
            when (val result = getCurrenciesUseCase.invoke()) {
                is ResultWrapper.Success -> setCurrenciesState(result.data)
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    fun saveEntity(entity: WalletEntity) {
        viewModelScope.launch {
            when (val result = addWalletUseCase.invoke(entity)) {
                is ResultWrapper.Success -> _state.update { _state.value.setSaved() }
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    fun deleteEntity() {
        viewModelScope.launch {
            val entity = _state.value.wallet
            if (entity != null) {
                when (val result = deleteWalletUseCase.invoke(entity)) {
                    is ResultWrapper.Success -> _state.update { _state.value.setDeleted() }
                    is ResultWrapper.Error -> setErrorState(result.code, result.message)
                }
            }
        }
    }

    private suspend fun setWalletState(flow: Flow<WalletEntity>?) {
        flow ?: return
        flow.collectLatest { category ->
            _state.update { _state.value.setWallet(category) }
        }
    }

    private suspend fun setCurrenciesState(flow: Flow<List<CurrencyEntity>>?) {
        flow ?: return
        flow.collectLatest { list ->
            _state.update { _state.value.setCurrencies(list) }
        }
    }

    private fun setErrorState(code: Int, message: UiString) {
        when (code) {
            CODE_WALLET_EXISTED -> _state.update { _state.value.setExisted() }
            else -> _state.update { _state.value.setError(message) }
        }

    }
}