package com.emikhalets.presentation.screens.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.WalletEntity
import com.emikhalets.domain.use_case.wallet.GetWalletsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletsViewModel @Inject constructor(
    private val getWalletsUseCase: GetWalletsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WalletsState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getWallets() {
        viewModelScope.launch {
            when (val result = getWalletsUseCase.invoke()) {
                is ResultWrapper.Success -> setWalletsState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun setWalletsState(flow: Flow<List<WalletEntity>>?) {
        flow ?: return
        flow.collectLatest { currencies ->
            _state.update { _state.value.setWallets(currencies) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}