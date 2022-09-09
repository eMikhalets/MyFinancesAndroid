package com.emikhalets.myfinances.presentation.screens.summary

import com.emikhalets.myfinances.data.entity.Wallet

data class SummaryState(
    val wallet: Wallet? = null,
    val summaryExpenses: List<SummaryTransaction> = emptyList(),
    val summaryIncomes: List<SummaryTransaction> = emptyList(),
    val monthExpenses: Double = 0.0,
    val monthIncomes: Double = 0.0,
    val error: Exception? = null
) {

    fun setWallet(data: Wallet): SummaryState {
        return this.copy(
            wallet = data,
            error = null
        )
    }

    fun setSummary(
        summaryExpenses: List<SummaryTransaction>,
        summaryIncomes: List<SummaryTransaction>,
        expenses: Double,
        incomes: Double
    ): SummaryState {
        return this.copy(
            summaryExpenses = summaryExpenses,
            summaryIncomes = summaryIncomes,
            monthExpenses = expenses,
            monthIncomes = incomes,
            error = null
        )
    }

    fun setError(exception: Exception): SummaryState {
        return this.copy(error = exception)
    }
}