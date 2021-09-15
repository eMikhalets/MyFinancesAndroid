package com.emikhalets.myfinances.ui.screens.transaction_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Category
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
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setCategories(result.data)
            }
        }
    }

    fun getTransaction(id: Long) {
        viewModelScope.launch {
            state = when (val result = repo.getTransaction(id)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setTransaction(result.data)
            }
        }
    }

    fun saveCategory(type: TransactionType, name: String) {
        viewModelScope.launch {
            val category = Category(name = name, type = type.value)
            state = when (val result = repo.insertCategory(category)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setCategorySaved()
            }
        }
    }

    fun saveTransaction(
        wallet: Long,
        category: Category,
        amount: Double,
        type: Int,
        note: String
    ) {
        viewModelScope.launch {
            val transaction = state.transaction?.transaction?.copy(
                categoryId = category.categoryId,
                walletId = wallet,
                value = amount,
                type = type,
                note = note,
            )
            transaction?.let {
                state = when (val result = repo.updateTransaction(transaction)) {
                    is Result.Error -> state.setError(result.exception)
                    is Result.Success -> state.setTransactionSaved()
                }
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            state.transaction?.transaction?.let {
                state = when (val result = repo.deleteTransaction(it)) {
                    is Result.Error -> state.setError(result.exception)
                    is Result.Success -> state.setTransactionDeleted()
                }
            }
        }
    }
}