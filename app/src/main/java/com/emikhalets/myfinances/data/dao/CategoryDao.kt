package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.myfinances.data.entity.Category
import com.emikhalets.myfinances.utils.enums.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllOrderByName(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE type=:type ORDER BY name ASC")
    fun getAllOrderByName(type: TransactionType): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id=:id")
    suspend fun getById(id: Long): Category

    @Query("SELECT type FROM categories WHERE id=:id")
    suspend fun getTypeById(id: Long): TransactionType

    @Query("SELECT EXISTS (SELECT * FROM categories WHERE name=:name AND type=:type)")
    suspend fun isExist(name: String, type: TransactionType): Boolean

    suspend fun insertIfNotExist(category: Category): Boolean {
        return if (!isExist(category.name, category.type)) {
            insert(category)
            true
        } else {
            false
        }
    }
}