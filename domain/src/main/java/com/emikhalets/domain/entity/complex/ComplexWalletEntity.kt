package com.emikhalets.domain.entity.complex

import com.emikhalets.domain.entity.TransactionEntity
import com.emikhalets.domain.entity.WalletEntity

data class ComplexWalletEntity(
    val wallet: WalletEntity,
    val transactions: List<TransactionEntity>,
)