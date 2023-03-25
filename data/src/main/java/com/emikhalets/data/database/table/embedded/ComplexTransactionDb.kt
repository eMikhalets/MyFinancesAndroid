package com.emikhalets.data.database.table.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.data.database.table.CategoryDb
import com.emikhalets.data.database.table.CurrencyDb
import com.emikhalets.data.database.table.TransactionDb
import com.emikhalets.data.database.table.WalletDb

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