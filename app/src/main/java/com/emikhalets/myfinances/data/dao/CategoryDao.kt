package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("SELECT * FROM categories WHERE type=:type ORDER BY name ASC")
    suspend fun getAllOrderByName(type: TransactionType): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id=:id")
    suspend fun getById(id: Long): Category

    @Query("SELECT EXISTS (SELECT * FROM categories WHERE name=:name)")
    suspend fun isExist(name: String): Boolean

    suspend fun insertIfNotExist(category: Category): Boolean {
        return if (!isExist(category.name)) {
            insert(category)
            true
        } else {
            false
        }
    }
}