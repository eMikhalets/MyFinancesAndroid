package com.emikhalets.myfinances.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun createDefaultWallet(context: Context, name: String) {
        viewModelScope.launch {
            when (val result = repo.insertWallet(Wallet(name = name))) {
                is Result.Error -> state = state.setError(result.exception)
                is Result.Success -> SharedPrefs.setCurrentWalletId(context, 1)
            }
        }
    }
}