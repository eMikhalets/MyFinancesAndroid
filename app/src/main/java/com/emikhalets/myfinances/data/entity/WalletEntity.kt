package com.emikhalets.myfinances.data.entity

data class WalletEntity(
    val wallet: Wallet,
    val value: Double,
)

fun WalletEntity?.copyWalletOrNew(name: String, initValue: Double): Wallet {
    return this?.wallet?.copy(name = name, initValue = initValue)
        ?: Wallet(name, initValue)
}