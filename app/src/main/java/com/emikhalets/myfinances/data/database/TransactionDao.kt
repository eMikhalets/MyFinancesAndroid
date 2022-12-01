package com.emikhalets.myfinances.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(item: Transaction): Long

    @Update
    suspend fun update(item: Transaction): Int

    @Update
    suspend fun updateAll(items: List<Transaction>): Int

    @Delete
    suspend fun delete(item: Transaction): Int

    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getItem(id: Long): Transaction

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE category=:category ORDER BY timestamp DESC")
    fun getAll(category: String): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE type=:type ORDER BY timestamp DESC")
    fun getAll(type: TransactionType): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE category=:category")
    suspend fun getAllForDelete(category: String): List<Transaction>
}