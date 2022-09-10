package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao : BaseDao<Wallet> {

    @Query("SELECT * FROM wallets ORDER BY name ASC")
    suspend fun getAllOrderByName(): Flow<List<Wallet>>

    @Query("SELECT * FROM wallets WHERE id=:id")
    suspend fun getById(id: Long): Wallet

    @Query("SELECT EXISTS (SELECT * FROM wallets WHERE name=:name)")
    suspend fun isExist(name: String): Boolean

    suspend fun insertIfNotExist(wallet: Wallet): Boolean {
        return if (!isExist(wallet.name)) {
            insert(wallet)
            true
        } else {
            false
        }
    }
}