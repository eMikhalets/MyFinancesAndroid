package com.emikhalets.myfinances.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Wallet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun createDefaultWallet(name: String) {
        viewModelScope.launch {
            state = when (val result = repo.insertWallet(Wallet(name = name))) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    defaultWalletCreated = true,
                    error = null
                )
            }
        }
    }
}