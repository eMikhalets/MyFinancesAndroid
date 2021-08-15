package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.Wallet
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
            state = when (val result = repo.getCategories(type.value)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedCategories(result.data)
            }
        }
    }

    fun getWallets() {
        viewModelScope.launch {
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedWallets(result.data)
            }
        }
    }

    fun saveCategory(type: TransactionType, name: String) {
        viewModelScope.launch {
            val category = Category(name, type.value, 1)
            state = when (val result = repo.insertCategory(category)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setSavedCategory()
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
//            state = state.setWalletError()
            return
        }

        // Check category
        if (category == null) {
//            state = state.setCategoryError()
            return
        }

        // Check value
        val valueDouble: Double
        try {
            valueDouble = value.toDouble()
        } catch (ex: NumberFormatException) {
            ex.printStackTrace()
//            state = state.copy(valueError = true)
            return
        }

        viewModelScope.launch {
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

    fun saveWallet(name: String, value: Double) {
        viewModelScope.launch {
            val wallet = Wallet(name, value)
            state = when (val result = repo.insertWallet(wallet)) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setSavedWallet()
            }
        }
    }
}