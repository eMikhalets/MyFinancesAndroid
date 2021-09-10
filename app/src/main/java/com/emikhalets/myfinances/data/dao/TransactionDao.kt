package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Transaction

@Dao
interface TransactionDao : BaseDao<Transaction> {

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<Transaction>

    @Query("SELECT * FROM transactions WHERE timestamp >= :start AND timestamp <= :end AND wallet_id = :wallet ORDER BY timestamp DESC")
    suspend fun getAllBetween(start: Long, end: Long, wallet: Long): List<Transaction>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY timestamp DESC")
    suspend fun getByType(type: Int): List<Transaction>

    @Query("SELECT * FROM transactions WHERE transaction_id = :id")
    suspend fun getItemById(id: Long): Transaction
}