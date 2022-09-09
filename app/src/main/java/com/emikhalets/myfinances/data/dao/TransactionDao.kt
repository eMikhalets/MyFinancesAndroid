package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao : BaseDao<Transaction> {

    @Query("SELECT * FROM transactions WHERE wallet_id=:walletId ORDER BY timestamp DESC")
    suspend fun getAllOrderByTime(walletId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getById(id: Long): Flow<Transaction>
}