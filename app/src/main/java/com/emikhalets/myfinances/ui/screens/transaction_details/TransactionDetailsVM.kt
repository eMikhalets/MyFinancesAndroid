package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Wallet
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(TransactionDetailsState())
        private set

    fun getCategories(type: Int) {
        viewModelScope.launch {
            state = when (val result = repo.getCategories(type)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    categories = result.data,
                    savedCategory = false,
                    error = null
                )
            }
        }
    }

    fun getTransaction(id: Long) {
        viewModelScope.launch {
            state = when (val result = repo.getTransaction(id)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    transaction = result.data, savedTransaction = false,
                    error = null
                )
            }
        }
    }

    fun getWallets() {
        viewModelScope.launch {
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    wallets = result.data, savedWallet = false,
                    error = null
                )
            }
        }
    }

    fun saveCategory(type: TransactionType, name: String, icon: Int) {
        viewModelScope.launch {
            val category = Category(name, type.value, icon)
            state = when (val result = repo.insertCategory(category)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    savedCategory = true,
                    error = null
                )
            }
        }
    }

    fun saveTransaction(
        wallet: Wallet,
        category: Category,
        amount: Double,
        type: Int,
        note: String,
        date: Long
    ) {
        viewModelScope.launch {
            val transaction = state.transaction?.copy(
                categoryId = category.categoryId,
                walletId = wallet.walletId,
                amount = amount,
                type = type,
                note = note,
                categoryName = category.name,
                categoryIcon = category.icon,
                timestamp = date
            )
            transaction?.let {
                state = when (val result = repo.updateTransaction(transaction)) {
                    is Result.Error -> state.copy(error = result.exception)
                    is Result.Success -> state.copy(
                        savedTransaction = true,
                        error = null
                    )
                }
            }
        }
    }

    fun saveWallet(name: String, value: Double) {
        viewModelScope.launch {
            val wallet = Wallet(name, value)
            state = when (val result = repo.insertWallet(wallet)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    savedWallet = true,
                    error = null
                )
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            state.transaction?.let {
                state = when (val result = repo.deleteTransaction(it)) {
                    is Result.Error -> state.copy(error = result.exception)
                    is Result.Success -> state.copy(
                        deletedTransaction = true,
                        error = null
                    )
                }
            }
        }
    }
}