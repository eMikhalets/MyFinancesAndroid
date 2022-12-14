package com.emikhalets.myfinances.utils.di

import com.emikhalets.myfinances.data.AppRepository
import com.emikhalets.myfinances.data.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppBindsModule {

    @Singleton
    @Binds
    fun bindsAppRepository(impl: AppRepositoryImpl): AppRepository
}