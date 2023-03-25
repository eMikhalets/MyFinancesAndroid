package com.emikhalets.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.data.database.table.TransactionDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: TransactionDb): Long

    @Update
    suspend fun update(entity: TransactionDb): Int

    @Delete
    suspend fun delete(entity: TransactionDb): Int

    @Query("SELECT * FROM transactions WHERE id=:id")
    suspend fun getItemFlow(id: Long): Flow<TransactionDb>

    @Query("SELECT * FROM transactions ORDER BY timestamp DECS")
    fun getAllFlow(): Flow<List<TransactionDb>>
}