package com.emikhalets.myfinances.ui.screens.summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.myfinances.data.Result
import com.emikhalets.myfinances.data.RoomRepository
import com.emikhalets.myfinances.data.entity.SummaryTransaction
import com.emikhalets.myfinances.data.entity.TransactionWithCategory
import com.emikhalets.myfinances.utils.enums.TransactionType
import com.emikhalets.myfinances.utils.getMaxTSOfMonth
import com.emikhalets.myfinances.utils.getMinTSOfMonth
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

    fun getWallet(id: Long) {
        viewModelScope.launch {
            state = when (val result = repo.getWallet(id)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> state.setWallet(result.data)
            }
        }
    }

    fun getSummary(date: Long, wallet: Long) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val start = calendar.getMinTSOfMonth(calendar.timeInMillis)
            val end = calendar.getMaxTSOfMonth(calendar.timeInMillis)
            state = when (val result = repo.getTransactionsBetween(start, end, wallet)) {
                is Result.Error -> state.setError(result.exception)
                is Result.Success -> prepareSummary(result.data)
            }
        }
    }

    private fun prepareSummary(transactions: List<TransactionWithCategory>): SummaryState {
        val summaryExpenses = mutableListOf<SummaryTransaction>()
        val summaryIncomes = mutableListOf<SummaryTransaction>()
        var expenses = 0.0
        var incomes = 0.0

        transactions.forEach { transaction ->
            when (TransactionType.get(transaction.transaction.type)) {
                TransactionType.Expense -> {
                    val expense = summaryExpenses.find {
                        it.category.name == transaction.category.name
                    }
                    if (expense == null) {
                        summaryExpenses.add(
                            SummaryTransaction(
                                value = transaction.transaction.value,
                                category = transaction.category
                            )
                        )
                    } else {
                        expense.value += transaction.transaction.value
                    }
                    expenses += transaction.transaction.value
                }
                TransactionType.Income -> {
                    val income = summaryIncomes.find {
                        it.category.name == transaction.category.name
                    }
                    if (income == null) {
                        summaryIncomes.add(
                            SummaryTransaction(
                                value = transaction.transaction.value,
                                category = transaction.category
                            )
                        )
                    } else {
                        income.value += transaction.transaction.value
                    }
                }
                TransactionType.None -> {
                }
            }
        }

        return state.setSummary(summaryExpenses, summaryIncomes, expenses, incomes)
    }
}