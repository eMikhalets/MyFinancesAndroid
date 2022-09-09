package com.emikhalets.myfinances.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun createDefaultWallet(context: Context, name: String) {
        viewModelScope.launch {
            when (val result = repo.insertWallet(Wallet(name = name))) {
                is Result.Error -> state = state.setError(result.exception)
                is Result.Success -> Prefs.setCurrentWalletId(context, 1)
            }
        }
    }
}