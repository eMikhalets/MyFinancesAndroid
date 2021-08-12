package com.emikhalets.myfinances.ui.screens

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
class SharedVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(AppScreenState())
        private set

    fun getWallets() {
        viewModelScope.launch {
            state = state.setLoading()
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedWallets(result.data)
            }
        }
    }
}