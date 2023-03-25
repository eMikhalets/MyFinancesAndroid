package com.emikhalets.presentation.screens.transaction_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.repository.AppRepository
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.domain.entity.TransactionEntity
import com.emikhalets.myfinances.utils.DEFAULT_ERROR
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionEditViewModel @Inject constructor(
    private val repo: AppRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun getTransaction(id: Long) {
        viewModelScope.launch {
            repo.getTransaction(id)
                .onSuccess { result -> _state.update { it.setTransaction(result) } }
                .onFailure { ex -> _state.update { it.setError(ex.message) } }
        }
    }

    fun getCategories(type: TransactionType?) {
        viewModelScope.launch {
            val typeNotNull = type ?: TransactionType.Expense
            repo.getCategoriesByType(typeNotNull)
                .onSuccess { setCategoriesResult(it, typeNotNull) }
                .onFailure { ex -> _state.update { _state.value.setError(ex.message) } }
        }
    }

    fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val request = if (transaction.id == 0L) {
                repo.insert(transaction)
            } else {
                repo.update(transaction)
            }
            request
                .onSuccess { _state.update { it.setSaved() } }
                .onFailure { ex -> _state.update { _state.value.setError(ex.message) } }
        }
    }

    private suspend fun setCategoriesResult(flow: Flow<List<Category>>, type: TransactionType) {
        try {
            flow.collectLatest { list ->
                val newList = list.toMutableList().apply {
                    add(0, Category.getDefaultInstance(type))
                }
                _state.update { it.setCategories(newList) }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    data class State(
        val entity: TransactionEntity? = null,
        val categories: List<Category> = emptyList(),
        val saved: Boolean = false,
        val error: String = "",
    ) {

        fun setTransaction(transaction: TransactionEntity): State {
            return this.copy(entity = transaction)
        }

        fun setCategories(categories: List<Category>): State {
            return this.copy(categories = categories)
        }

        fun setSaved(): State {
            return this.copy(saved = true)
        }

        fun setError(message: String?): State {
            return this.copy(error = message ?: DEFAULT_ERROR)
        }
    }
}