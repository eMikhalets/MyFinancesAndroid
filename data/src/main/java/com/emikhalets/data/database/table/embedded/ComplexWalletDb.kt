package com.emikhalets.data.database.table.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.data.database.table.CurrencyDb
import com.emikhalets.data.database.table.TransactionDb
import com.emikhalets.data.database.table.WalletDb

data class ComplexWalletDb(
    @Embedded
    val wallet: WalletDb,
    @Relation(parentColumn = "id", entityColumn = "wallet_id")
    val transactions: List<TransactionDb>,
    @Relation(parentColumn = "currency_id", entityColumn = "id")
    val currency: CurrencyDb,
)