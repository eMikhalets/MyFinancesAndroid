package com.emikhalets.myfinances.presentation.screens.wallet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class WalletViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(WalletState())
        private set

    fun getWallet(id: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getWallet(id)
                .onSuccess { state = state.setWallet(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun saveWallet(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val wallet = state.wallet?.copy(name = name)
            wallet?.let {
                repo.updateWallet(wallet)
                    .onSuccess { state = state.setWalletSaved() }
                    .onFailure { state = state.setError(it.message) }
            }
        }
    }

    fun deleteWallet() {
        viewModelScope.launch(Dispatchers.Default) {
            state.wallet?.let { wallet ->
                repo.deleteWallet(wallet)
                    .onSuccess { state = state.setWalletDeleted() }
                    .onFailure { state = state.setError(it.message) }
            }
        }
    }
}