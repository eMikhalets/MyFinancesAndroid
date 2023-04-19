package com.emikhalets.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.data.database.dao.CategoriesDao
import com.emikhalets.data.database.dao.CurrenciesDao
import com.emikhalets.data.database.dao.TransactionsDao
import com.emikhalets.data.database.dao.WalletsDao
import com.emikhalets.data.database.table.CategoryDb
import com.emikhalets.data.database.table.CurrencyDb
import com.emikhalets.data.database.table.TransactionDb
import com.emikhalets.data.database.table.WalletDb

@Database(
    entities = [
        CategoryDb::class,
        CurrencyDb::class,
        TransactionDb::class,
        WalletDb::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoriesDao: CategoriesDao
    abstract val currenciesDao: CurrenciesDao
    abstract val transactionsDao: TransactionsDao
    abstract val walletsDao: WalletsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "SimpleMoney.db").build()
    }
}