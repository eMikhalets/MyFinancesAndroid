package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Transaction
import com.emikhalets.myfinances.data.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao : BaseDao<Transaction> {

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE wallet_id=:walletId ORDER BY timestamp DESC")
    fun getAllOrderByTime(walletId: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE wallet_id=:walletId AND category_id=:categoryId")
    suspend fun getAll(walletId: Long, categoryId: Long): List<Transaction>

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getById(id: Long): TransactionEntity

    @Query("DELETE FROM transactions WHERE wallet_id=:walletId")
    suspend fun delete(walletId: Long)
}