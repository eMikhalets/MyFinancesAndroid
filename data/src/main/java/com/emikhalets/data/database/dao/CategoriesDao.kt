package com.emikhalets.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.data.database.table.CategoryDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: CategoryDb): Long

    @Update
    suspend fun update(entity: CategoryDb): Int

    @Delete
    suspend fun delete(entity: CategoryDb): Int

    @Query("SELECT * FROM categories WHERE id=:id")
    fun getItemFlow(id: Long): Flow<CategoryDb>

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllFlow(): Flow<List<CategoryDb>>

    @Query("SELECT * FROM categories WHERE type=:type ORDER BY name ASC")
    fun getAllFlow(type: String): Flow<List<CategoryDb>>

    @Query("SELECT EXISTS(SELECT * FROM categories WHERE name=:name AND type=:type)")
    suspend fun isExists(name: String, type: String): Boolean
}