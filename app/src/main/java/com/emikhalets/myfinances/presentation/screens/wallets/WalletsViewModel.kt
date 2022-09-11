package com.emikhalets.myfinances.presentation.screens.wallets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.data.entity.WalletEntity
import com.emikhalets.myfinances.utils.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class WalletsViewModel @Inject constructor(
    private val repo: AppRepository,
    val prefs: Prefs,
) : ViewModel() {

    var state by mutableStateOf(WalletsState())
        private set

    fun getWallets() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getWallets()
                .onSuccess { setWalletsState(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    private suspend fun setWalletsState(flow: Flow<List<WalletEntity>>) {
        flow.collect { wallets ->
            state = state.setWallets(wallets)
        }
    }

    fun getWallet(id: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getWallet(id)
                .onSuccess { state = state.setWallet(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun saveWallet(wallet: Wallet) {
        viewModelScope.launch(Dispatchers.Default) {
            val request = if (wallet.id == 0L) {
                repo.insertWallet(wallet)
            } else {
                repo.updateWallet(wallet)
            }
            request
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun deleteWallet(wallet: Wallet) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.deleteWallet(wallet)
                .onFailure { state = state.setError(it.message) }
        }
    }
}