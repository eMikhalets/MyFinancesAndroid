package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Transaction

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<Transaction>
}