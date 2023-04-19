package com.emikhalets.myfinances.di

import com.emikhalets.data.repository.DatabaseRepositoryImpl
import com.emikhalets.domain.repository.DatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsDatabaseRepository(impl: DatabaseRepositoryImpl): DatabaseRepository
}