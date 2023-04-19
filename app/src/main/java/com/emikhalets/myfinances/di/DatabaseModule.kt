package com.emikhalets.myfinances.di

import android.content.Context
import com.emikhalets.data.database.AppDatabase
import com.emikhalets.data.database.dao.CategoriesDao
import com.emikhalets.data.database.dao.CurrenciesDao
import com.emikhalets.data.database.dao.TransactionsDao
import com.emikhalets.data.database.dao.WalletsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.get(context)

    @Singleton
    @Provides
    fun providesCategoriesDao(database: AppDatabase): CategoriesDao = database.categoriesDao

    @Singleton
    @Provides
    fun providesTransactionsDao(database: AppDatabase): TransactionsDao = database.transactionsDao

    @Singleton
    @Provides
    fun providesCurrenciesDao(database: AppDatabase): CurrenciesDao = database.currenciesDao

    @Singleton
    @Provides
    fun providesWalletsDao(database: AppDatabase): WalletsDao = database.walletsDao
}