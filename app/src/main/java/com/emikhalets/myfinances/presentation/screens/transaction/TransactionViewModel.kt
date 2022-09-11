package com.emikhalets.myfinances.presentation.screens.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(TransactionState())
        private set

    fun getTransaction(id: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getTransaction(id)
                .onSuccess { state = state.setTransaction(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun getCategories(type: TransactionType) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getCategories(type)
                .onSuccess { flow -> flow.collect { state = state.setCategories(it) } }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun saveTransaction(category: Category, value: Double, type: TransactionType, note: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val transaction = state.entity?.transaction?.copy(
                categoryId = category.id,
                value = value,
                type = type,
                note = note
            )
            transaction?.let {
                repo.updateTransaction(transaction)
                    .onSuccess { state = state.setTransactionSaved() }
                    .onFailure { state = state.setError(it.message) }
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch(Dispatchers.Default) {
            state.entity?.transaction?.let { transaction ->
                repo.deleteTransaction(transaction)
                    .onSuccess { state = state.setTransactionDeleted() }
                    .onFailure { state = state.setError(it.message) }
            }
        }
    }
}