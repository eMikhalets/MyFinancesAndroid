package com.emikhalets.domain.entity

import java.util.*

data class TransactionEntity(
    val id: Long,
    val categoryId: Long,
    val walletId: Long,
    val currencyId: Long,
    val value: Double,
    val type: TransactionType,
    val note: String = "",
    val timestamp: Long = Date().time,
)