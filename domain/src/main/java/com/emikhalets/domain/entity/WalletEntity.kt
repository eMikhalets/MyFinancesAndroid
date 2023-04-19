package com.emikhalets.domain.entity

data class WalletEntity(
    val id: Long,
    val name: String,
    val currencyId: Long,
    val initialValue: Double,
)