package com.emikhalets.data.database.table

import androidx.room.Embedded
import androidx.room.Relation

data class ComplexTransactionDb(
    @Embedded
    val transaction: TransactionDb,
    @Relation(parentColumn = "category_id", entityColumn = "id")
    val category: CategoryDb,
    @Relation(parentColumn = "currency_id", entityColumn = "id")
    val currency: CurrencyDb,
    @Relation(parentColumn = "wallet_id", entityColumn = "id")
    val wallet: WalletDb,
)