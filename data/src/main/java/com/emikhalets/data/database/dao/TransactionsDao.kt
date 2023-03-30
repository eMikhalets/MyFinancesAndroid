package com.emikhalets.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.emikhalets.data.database.table.TransactionDb
import com.emikhalets.data.database.table.embedded.ComplexTransactionDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: TransactionDb): Long

    @Update
    suspend fun update(entity: TransactionDb): Int

    @Update
    suspend fun updateAll(list: List<TransactionDb>): Int

    @Delete
    suspend fun delete(entity: TransactionDb): Int

    @Query("SELECT * FROM transactions WHERE id=:id")
    fun getItemFlow(id: Long): Flow<TransactionDb>

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<TransactionDb>>

    @Query("SELECT * FROM transactions WHERE category_id=:id")
    suspend fun getAllByCategory(id: Long): List<TransactionDb>

    @Query("SELECT * FROM transactions WHERE wallet_id=:id")
    suspend fun getAllByWallet(id: Long): List<TransactionDb>

    @Query("SELECT * FROM transactions WHERE currency_id=:id")
    suspend fun getAllByCurrency(id: Long): List<TransactionDb>

    @Transaction
    @Query("SELECT * FROM transactions WHERE wallet_id=:walletId ORDER BY timestamp DESC")
    fun getComplexTransactions(walletId: Long): Flow<List<ComplexTransactionDb>>

    @Transaction
    @Query("SELECT * FROM transactions " +
            "WHERE type=:type AND wallet_id=:walletId " +
            "ORDER BY timestamp DESC")
    fun getComplexTransactions(
        type: String,
        walletId: Long,
    ): Flow<List<ComplexTransactionDb>>

    @Query("DELETE FROM transactions WHERE id=:id")
    suspend fun deleteById(id: Long): Int
}