package com.emikhalets.myfinances.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.DEFAULT_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repo: AppRepository,
) : ViewModel() {

    var state by mutableStateOf(MainActivityState())
        private set

    fun saveWallet(wallet: Wallet) {
        viewModelScope.launch {
            repo.insertWallet(wallet)
                .onSuccess { state = state.setWalletCreated(it) }
                .onFailure { state = state.setError(it.message ?: DEFAULT_ERROR) }
        }
    }
}