package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Category

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("SELECT EXISTS (SELECT * FROM categories WHERE name = :name)")
    suspend fun isExist(name: String): Boolean

    suspend fun insertIfNotExist(category: Category): Boolean {
        return if (!isExist(category.name)) {
            insert(category)
            true
        } else {
            false
        }
    }

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<Category>

    @Query("SELECT * FROM categories WHERE category_id = :id")
    suspend fun getCategoryById(id: Long): Category

    @Query("SELECT * FROM categories WHERE type = :type ORDER BY name ASC")
    suspend fun getAllByType(type: Int): List<Category>
}