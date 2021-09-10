package com.emikhalets.myfinances.data.entity

data class TransactionWithCategory(
    val transaction: Transaction,
    val category: Category
)