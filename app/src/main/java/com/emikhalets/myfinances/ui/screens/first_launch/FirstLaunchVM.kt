package com.emikhalets.myfinances.ui.screens.first_launch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstLaunchVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(FirstLaunchState())
        private set

    fun getWallets() {
        viewModelScope.launch {
            state = state.setLoading()
            delay(2000)
            state = when (val result = repo.getWallets()) {
                is Result.Error -> state.setCommonError(result.exception)
                is Result.Success -> state.setLoadedWallets(result.data)
            }
        }
    }
}