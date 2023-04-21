package com.emikhalets.domain.entity.utils

import com.emikhalets.domain.entity.complex.ComplexTransactionEntity

data class TransactionsListEntity(
    val timestamp: Long,
    val incomeSum: Double,
    val expensesSum: Double,
    val transactions: List<ComplexTransactionEntity>,
)