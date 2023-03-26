package com.emikhalets.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.use_case.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase.invoke()) {
                is ResultWrapper.Success -> setCategoriesState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun setCategoriesState(flow: Flow<List<CategoryEntity>>?) {
        flow ?: return
        flow.collectLatest { categories ->
            val expenses = categories.filter { it.type == TransactionType.Expense }
            val incomes = categories.filter { it.type == TransactionType.Income }
            _state.update { _state.value.setCategories(expenses, incomes) }
        }
    }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}