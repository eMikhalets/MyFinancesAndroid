package com.emikhalets.myfinances.utils.di

import android.content.Context
import com.emikhalets.myfinances.data.database.AppDatabase
import com.emikhalets.myfinances.data.dao.CategoryDao
import com.emikhalets.myfinances.data.dao.TransactionDao
import com.emikhalets.myfinances.data.dao.WalletDao
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
    fun providesCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao

    @Singleton
    @Provides
    fun providesTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao

    @Singleton
    @Provides
    fun providesWalletDao(database: AppDatabase): WalletDao = database.walletDao
}