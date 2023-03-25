package com.emikhalets.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.data.database.table.CurrencyDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: CurrencyDb): Long

    @Update
    suspend fun update(entity: CurrencyDb): Int

    @Delete
    suspend fun delete(entity: CurrencyDb): Int

    @Query("SELECT * FROM currencies WHERE id=:id")
    fun getItemFlow(id: Long): Flow<CurrencyDb>

    @Query("SELECT * FROM currencies")
    fun getAllFlow(): Flow<List<CurrencyDb>>
}