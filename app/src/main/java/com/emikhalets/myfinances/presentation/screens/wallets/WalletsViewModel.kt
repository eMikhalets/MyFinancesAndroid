package com.emikhalets.myfinances.presentation.screens.wallets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Wallet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class WalletsViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(WalletsState())
        private set

    fun getWallets() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getWallets()
                .onSuccess { setWalletsState(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    private suspend fun setWalletsState(flow: Flow<List<Wallet>>) {
        flow.collect { wallets ->
            state = state.setWallets(wallets)
        }
    }

    fun addWallet(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val entity = Wallet(name)
            repo.insertWallet(entity)
                .onFailure { state = state.setError(it.message) }
        }
    }
}