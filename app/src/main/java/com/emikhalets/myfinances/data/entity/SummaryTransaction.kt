package com.emikhalets.myfinances.data.entity

data class SummaryTransaction(
    val value: Double = 0.0,
    val category: Category = Category()
)