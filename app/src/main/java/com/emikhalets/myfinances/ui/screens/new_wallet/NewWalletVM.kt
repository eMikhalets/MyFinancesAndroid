package com.emikhalets.myfinances.ui.screens.new_wallet

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
class NewWalletVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(NewWalletState())
        private set

    fun saveWallet(
        name: String,
        value: String
    ) {
        // Check name
        if (name.isEmpty()) {
            state = state.setEmptyNameError()
            return
        }

        // Check value
        val valueDouble: Double
        try {
            valueDouble = value.toDouble()
        } catch (ex: NumberFormatException) {
            ex.printStackTrace()
            state = state.setValueError()
            return
        }

        viewModelScope.launch {
            state = state.setLoading()
            val wallet = Wallet(
                name = name,
                amount = valueDouble
            )
            state = when (val result = repo.insertWallet(wallet)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setSavedWallet()
            }
        }
    }
}