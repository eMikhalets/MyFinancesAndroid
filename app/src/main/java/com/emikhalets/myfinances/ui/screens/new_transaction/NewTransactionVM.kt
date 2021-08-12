package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewTransactionVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(NewTransactionState())
        private set

    fun getCategories(type: TransactionType) {
        viewModelScope.launch {
            state = state.setLoading()
            state = when (val result = repo.getCategories(type.value)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedCategories(result.data)
            }
        }
    }

    fun saveTransaction(
        wallet: Long?,
        category: Long?,
        note: String,
        value: String,
        type: TransactionType
    ) {
        // Check wallet
        if (wallet == null) {
            state = state.setWalletError()
            return
        }

        // Check category
        if (category == null) {
            state = state.setCategoryError()
            return
        }

        // Check value
        val valueDouble: Double
        try {
            valueDouble = value.toDouble()
        } catch (ex: NumberFormatException) {
            ex.printStackTrace()
            state = state.copy(valueError = true)
            return
        }

        viewModelScope.launch {
            state = state.setLoading()
            val transaction = Transaction(
                categoryId = category,
                walletId = wallet,
                amount = valueDouble,
                type = type.value,
                note = note,
                timestamp = Date().time
            )
            state = when (val result = repo.insertTransaction(transaction)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setSavedTransaction()
            }
        }
    }
}