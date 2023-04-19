package com.emikhalets.domain.entity.complex

import com.emikhalets.domain.entity.CategoryEntity
import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.WalletEntity

data class ComplexTransactionEntity(
    val transaction: TransactionEntity,
    val category: CategoryEntity,
    val wallet: WalletEntity,
    val currency: CurrencyEntity,
)