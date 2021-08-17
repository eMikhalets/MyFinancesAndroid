package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Wallet

@Dao
interface WalletDao : BaseDao<Wallet> {

    @Query("SELECT wallet_id FROM wallets WHERE name = :name")
    suspend fun isExist(name: String): Long

    suspend fun insertIfNotExist(wallet: Wallet): Boolean {
        return if (isExist(wallet.name) > 0) {
            insert(wallet)
            true
        } else {
            false
        }
    }

    @Query("SELECT * FROM wallets ORDER BY name ASC")
    suspend fun getAll(): List<Wallet>
}