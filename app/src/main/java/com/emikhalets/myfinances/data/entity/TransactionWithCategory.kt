package com.emikhalets.myfinances.data.entity

data class TransactionWithCategory(
    val transaction: Transaction = Transaction(),
    val category: Category = Category()
)