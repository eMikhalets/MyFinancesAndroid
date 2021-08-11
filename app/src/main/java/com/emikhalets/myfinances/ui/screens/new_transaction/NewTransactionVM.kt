package com.emikhalets.myfinances.ui.screens.new_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.utils.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransactionVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf<NewTransactionState>(NewTransactionState.Idle)
        private set

    fun getCategories(type: TransactionType) {
        viewModelScope.launch {
            state = NewTransactionState.Loading
            state = when (val result = repo.getCategories(type.value)) {
                is Result.Error -> NewTransactionState.Error(result.exception)
                is Result.Success -> NewTransactionState.Categories(result.data)
            }
        }
    }
}