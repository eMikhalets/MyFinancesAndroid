package com.emikhalets.myfinances.utils.di

import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.TestRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Singleton
    @Binds
    fun bindsAppRepository(impl: TestRepositoryImpl): AppRepository
}