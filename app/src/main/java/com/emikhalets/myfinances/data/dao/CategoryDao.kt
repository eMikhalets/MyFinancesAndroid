package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Category

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("SELECT category_id FROM categories WHERE name = :name")
    suspend fun isExist(name: String): Long

    suspend fun insertIfNotExist(category: Category): Boolean {
        return if (isExist(category.name) > 0) {
            insert(category)
            true
        } else {
            false
        }
    }

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<Category>

    @Query("SELECT * FROM categories WHERE type = :type ORDER BY name ASC")
    suspend fun getAllByType(type: Int): List<Category>
}