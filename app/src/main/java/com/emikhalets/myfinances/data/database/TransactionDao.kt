package com.emikhalets.myfinances.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.myfinances.domain.entity.Transaction
import com.emikhalets.myfinances.domain.entity.TransactionEntity
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

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getItem(id: Long): TransactionEntity

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAll(): Flow<List<TransactionEntity>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE category_id=:categoryId ORDER BY timestamp DESC")
    fun getAll(categoryId: Int): Flow<List<TransactionEntity>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE type=:type ORDER BY timestamp DESC")
    fun getAll(type: TransactionType): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE category_id=:categoryId")
    suspend fun getAllForDelete(categoryId: Int): List<Transaction>
}