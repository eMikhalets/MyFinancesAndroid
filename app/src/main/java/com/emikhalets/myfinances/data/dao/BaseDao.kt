package com.emikhalets.myfinances.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {

    @Insert
    suspend fun insert(item: T): Long

    @Insert
    suspend fun insertAll(items: List<T>): List<Long>

    @Update
    suspend fun update(item: T): Int

    @Delete
    suspend fun delete(item: T): Int
}