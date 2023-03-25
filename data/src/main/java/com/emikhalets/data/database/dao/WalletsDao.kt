package com.emikhalets.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.data.database.table.WalletDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: WalletDb): Long

    @Update
    suspend fun update(entity: WalletDb): Int

    @Delete
    suspend fun delete(entity: WalletDb): Int

    @Query("SELECT * FROM wallets WHERE id=:id")
    suspend fun getItemFlow(id: Long): Flow<WalletDb>

    @Query("SELECT * FROM wallets ORDER BY name ASC")
    fun getAllFlow(): Flow<List<WalletDb>>

    @Query("SELECT EXISTS(SELECT * FROM wallets WHERE name=:name)")
    fun isExists(name: String): Boolean
}