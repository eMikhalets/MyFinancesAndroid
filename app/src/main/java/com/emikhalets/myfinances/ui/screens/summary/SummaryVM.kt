package com.emikhalets.myfinances.ui.screens.summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.data.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryVM @Inject constructor(
    private val repo: RoomRepository
) : ViewModel() {

    var state by mutableStateOf(SummaryState())
        private set

    fun getSummaryMonth(wallet: Long) {
        val start = getStartMonthTimestamp()
        val end = getEndMonthTimestamp()
        viewModelScope.launch {
            state = when (val result = repo.getTransactionsBetween(start, end, wallet)) {
                is Result.Error -> state.copy(error = result.exception)
                is Result.Success -> state.copy(
                    monthTransactions = mapTransactionsToSummary(result.data),
                    error = null
                )
            }
        }
    }

    private fun getStartMonthTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        return calendar.timeInMillis
    }

    private fun getEndMonthTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.timeInMillis
    }

    private fun mapTransactionsToSummary(list: List<Transaction>): Map<Category, Double> {
        val map = mutableMapOf<Category, Double>()
        list.forEach {
//            val category = Category(id = it.categoryId, name = it.categoryName, type = it.type)
//            val value = map[category] ?: 0.0
//            map[category] = value + it.amount
        }
        return map
    }
}