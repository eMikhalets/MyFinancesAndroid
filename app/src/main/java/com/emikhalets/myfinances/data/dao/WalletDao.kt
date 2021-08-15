package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Wallet

@Dao
interface WalletDao : BaseDao<Wallet> {

    @Query("SELECT * FROM wallets ORDER BY name ASC")
    suspend fun getAll(): List<Wallet>
}