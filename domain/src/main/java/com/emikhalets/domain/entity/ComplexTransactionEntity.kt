package com.emikhalets.domain.entity

data class ComplexTransactionEntity(
    val transaction: TransactionEntity,
    val category: CategoryEntity,
    val wallet: WalletEntity,
    val currency: CurrencyEntity,
)