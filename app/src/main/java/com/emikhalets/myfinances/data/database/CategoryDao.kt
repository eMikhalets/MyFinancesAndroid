package com.emikhalets.myfinances.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Category): Long

    @Update
    suspend fun update(item: Category): Int

    @Delete
    suspend fun delete(item: Category): Int

    @Query("SELECT * FROM categories WHERE name=:name")
    suspend fun getItem(name: String): Category

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE type=:type ORDER BY name ASC")
    fun getAll(type: TransactionType): Flow<List<Category>>
}