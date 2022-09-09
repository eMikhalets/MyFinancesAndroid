package com.emikhalets.myfinances.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun getTransactions(date: Long, wallet: Long) {
        viewModelScope.launch {
            state = when (val result = repo.getTransactionsBetween(start, end, wallet)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setTransactions(result.data)
            }
        }
    }
}