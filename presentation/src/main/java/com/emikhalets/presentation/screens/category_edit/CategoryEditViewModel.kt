package com.emikhalets.presentation.screens.category_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.CODE_CATEGORY_EXISTED
import com.emikhalets.core.UiString
import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.use_case.category.AddCategoryUseCase
import com.emikhalets.domain.use_case.category.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryEditViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryEditState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun dropCategoryExisted() {
        _state.update { _state.value.setCategoryExisted(false) }
    }

    fun getCategory(id: Long) {
        viewModelScope.launch {
            when (val result = getCategoryUseCase.invoke(id)) {
                is ResultWrapper.Success -> setCategoryState(result.data)
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    fun saveCategory(entity: CategoryEntity) {
        viewModelScope.launch {
            when (val result = addCategoryUseCase.invoke(entity)) {
                is ResultWrapper.Success -> setCategorySavedState()
                is ResultWrapper.Error -> setErrorState(result.code, result.message)
            }
        }
    }

    private suspend fun setCategoryState(flow: Flow<CategoryEntity>?) {
        flow ?: return
        flow.collectLatest { category ->
            _state.update { _state.value.setCategory(category) }
        }
    }

    private fun setCategorySavedState() {
        _state.update { _state.value.setCategorySaved() }
    }

    private fun setErrorState(code: Int, message: UiString) {
        when (code) {
            CODE_CATEGORY_EXISTED -> _state.update { _state.value.setCategoryExisted() }
            else -> _state.update { _state.value.setError(message) }
        }

    }
}