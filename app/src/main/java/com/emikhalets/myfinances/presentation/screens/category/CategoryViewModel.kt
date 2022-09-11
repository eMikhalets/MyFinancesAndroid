package com.emikhalets.myfinances.presentation.screens.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    fun getCategory(id: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getCategory(id)
                .onSuccess { state = state.setCategory(it) }
                .onFailure { state = state.setError(it.message) }
        }
    }

    fun saveCategory(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val category = state.category?.copy(name = name)
            category?.let {
                repo.updateCategory(category)
                    .onSuccess { state = state.setCategorySaved() }
                    .onFailure { state = state.setError(it.message) }
            }
        }
    }

    fun deleteCategory() {
        viewModelScope.launch(Dispatchers.Default) {
            state.category?.let { category ->
                repo.deleteCategory(category)
                    .onSuccess { state = state.setCategoryDeleted() }
                    .onFailure { state = state.setError(it.message) }
            }
        }
    }
}