package com.emikhalets.myfinances.presentation.screens.categories

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(CategoriesState())
        private set

    fun getCategories() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getCategories()
                .onSuccess { setCategoriesState(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    private suspend fun setCategoriesState(flow: Flow<List<Category>>) {
        flow.collect { categories ->
            val expenseList = categories.filter { it.type == TransactionType.Expense }
            val incomeList = categories.filter { it.type == TransactionType.Income }
            state = state.setCategories(incomeList, expenseList)
        }
    }

    fun addCategory(name: String, type: TransactionType) {
        viewModelScope.launch(Dispatchers.Default) {
            val entity = Category(name, type)
            repo.insertCategory(entity)
                .onFailure { state = state.setError(it.message) }
        }
    }
}