package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Transaction

@Dao
interface TransactionDao : BaseDao<Transaction> {

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<Transaction>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY timestamp DESC")
    suspend fun getByType(type: Int): List<Transaction>
}