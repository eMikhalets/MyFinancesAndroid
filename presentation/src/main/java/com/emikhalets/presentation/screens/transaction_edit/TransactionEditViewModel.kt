package com.emikhalets.presentation.screens.transaction_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.use_case.category.GetCategoriesUseCase
import com.emikhalets.domain.use_case.transaction.AddTransactionUseCase
import com.emikhalets.domain.use_case.transaction.DeleteTransactionUseCase
import com.emikhalets.domain.use_case.transaction.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionEditViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionEditState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getTransaction(id: Long) {
        viewModelScope.launch {
            when (val result = getTransactionUseCase.invoke(id)) {
                is ResultWrapper.Success -> seTransactionState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    fun getCategories(type: TransactionType) {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase.invoke(type)) {
                is ResultWrapper.Success -> setCategoriesState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun seTransactionState(flow: Flow<TransactionEntity>?) {
        flow ?: return
        flow.collectLatest { transaction ->
            _state.update { _state.value.setTransaction(transaction) }
        }
    }

    private suspend fun setCategoriesState(flow: Flow<List<CategoryEntity>>?) {
        flow ?: return
        flow.collectLatest { categories ->
            _state.update { it.setCategories(categories) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }

    fun saveTransaction(entity: TransactionEntity) {
        viewModelScope.launch {
            when (val result = addTransactionUseCase.invoke(entity)) {
                is ResultWrapper.Success -> _state.update { _state.value.setTransactionSaved() }
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch {
            val entity = _state.value.transaction ?: return@launch
            when (val result = deleteTransactionUseCase.invoke(entity)) {
                is ResultWrapper.Success -> _state.update { _state.value.setTransactionSaved() }
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }
}