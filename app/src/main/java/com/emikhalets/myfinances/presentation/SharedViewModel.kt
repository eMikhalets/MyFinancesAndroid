package com.emikhalets.myfinances.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.DEFAULT_ERROR
import com.emikhalets.myfinances.utils.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repo: AppRepository,
    private val prefs: Prefs,
) : ViewModel() {

    var state by mutableStateOf(MainActivityState())
        private set

    fun createDefaultWallet(name: String) {
        viewModelScope.launch {
            repo.insertWallet(Wallet(name))
                .onSuccess {
                    if (it) prefs.currentWalletId = 1
                    state = state.setWalletCreated()
                }
                .onFailure { state = state.setError(it.message ?: DEFAULT_ERROR) }
        }
    }
}