package com.emikhalets.myfinances.data.entity

data class SummaryTransaction(
    var value: Double = 0.0,
    var category: Category = Category()
)