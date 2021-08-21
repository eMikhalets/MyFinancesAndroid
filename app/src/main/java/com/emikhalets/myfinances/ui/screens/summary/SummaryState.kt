package com.emikhalets.myfinances.ui.screens.summary

import com.emikhalets.myfinances.data.entity.Category

data class SummaryState(
    val monthTransactions: Map<Category, Double> = emptyMap(),
    val error: Exception? = null
) {

    fun errorMessage(): String {
        return error?.message ?: ""
    }
}