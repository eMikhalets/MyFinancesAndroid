package com.emikhalets.myfinances.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.myfinances.domain.entity.Category
import com.emikhalets.myfinances.domain.entity.Transaction

@Database(
    entities = [
        Category::class,
        Transaction::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoryDao: CategoryDao
    abstract val transactionDao: TransactionDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "MyFinances.db").build()
    }
}