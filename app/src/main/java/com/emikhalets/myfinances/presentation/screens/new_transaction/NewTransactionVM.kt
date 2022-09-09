package com.emikhalets.myfinances.presentation.screens.new_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransactionVM @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {

    var state by mutableStateOf(NewTransactionState())
        private set

    fun getCategories(type: TransactionType) {
        viewModelScope.launch {
            state = when (val result = repo.getCategories(type.value)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setCategories(result.data)
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
        category: Category?,
        note: String,
        value: Double,
        type: TransactionType
    ) {
        if (category == null) return

        viewModelScope.launch {
            val transaction = Transaction(
                categoryId = category.categoryId,
                walletId = wallet,
                value = value,
                type = type.value,
                note = note
            )
            state = when (val result = repo.insertTransaction(transaction)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setTransactionSaved()
            }
        }
    }
}