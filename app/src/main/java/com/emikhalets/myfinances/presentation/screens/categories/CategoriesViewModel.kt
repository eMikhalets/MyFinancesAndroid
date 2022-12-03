package com.emikhalets.myfinances.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.repository.AppRepository
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.utils.DEFAULT_ERROR
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repo: AppRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun getCategories() {
        viewModelScope.launch {
            repo.getCategoriesByType(TransactionType.Income)
                .onSuccess { setIncomeCategoriesState(it) }
                .onFailure { ex -> _state.update { it.setError(ex.message) } }
            repo.getCategoriesByType(TransactionType.Expense)
                .onSuccess { setExpenseCategoriesState(it) }
                .onFailure { ex -> _state.update { _state.value.setError(ex.message) } }
        }
    }

    private suspend fun setIncomeCategoriesState(flow: Flow<List<Category>>) {
        flow.collect { categories ->
            _state.update { _state.value.setIncomeCategories(categories) }
        }
    }

    private suspend fun setExpenseCategoriesState(flow: Flow<List<Category>>) {
        flow.collect { categories ->
            _state.update { _state.value.setExpenseCategories(categories) }
        }
    }

    fun saveCategory(category: Category) {
        viewModelScope.launch {
            val request = if (category.id == 0) {
                repo.insert(category)
            } else {
                repo.update(category)
            }
            request.onFailure { ex -> _state.update { _state.value.setError(ex.message) } }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repo.delete(category)
                .onFailure { ex -> _state.update { _state.value.setError(ex.message) } }
        }
    }

    data class State(
        val incomeList: List<Category> = emptyList(),
        val expenseList: List<Category> = emptyList(),
        val error: String = "",
    ) {

        fun setIncomeCategories(incomeList: List<Category>): State {
            return this.copy(incomeList = incomeList)
        }

        fun setExpenseCategories(expenseList: List<Category>): State {
            return this.copy(expenseList = expenseList)
        }

        fun setError(message: String?): State {
            return this.copy(error = message ?: DEFAULT_ERROR)
        }
    }
}