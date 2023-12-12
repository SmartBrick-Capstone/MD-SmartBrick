package com.github.emmpann.smartbrick.di

import com.github.emmpann.smartbrick.core.data.local.repository.AuthRepository
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository = AuthRepository(apiService)
}