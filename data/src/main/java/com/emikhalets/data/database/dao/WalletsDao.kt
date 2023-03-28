package com.emikhalets.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.emikhalets.data.database.table.WalletDb
import com.emikhalets.data.database.table.embedded.ComplexWalletDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: WalletDb): Long

    @Update
    suspend fun update(entity: WalletDb): Int

    @Update
    suspend fun updateAll(list: List<WalletDb>): Int

    @Delete
    suspend fun delete(entity: WalletDb): Int

    @Query("SELECT * FROM wallets WHERE id=:id")
    fun getItemFlow(id: Long): Flow<WalletDb>

    @Query("SELECT * FROM wallets ORDER BY name ASC")
    fun getAllFlow(): Flow<List<WalletDb>>

    @Query("SELECT * FROM wallets WHERE currency_id=:id")
    suspend fun getAllByCurrency(id: Long): List<WalletDb>

    @Query("SELECT EXISTS(SELECT * FROM wallets WHERE name=:name)")
    suspend fun isExists(name: String): Boolean

    @Transaction
    @Query("SELECT * FROM wallets WHERE id=:id")
    suspend fun getComplexWallet(id: Long): ComplexWalletDb
}