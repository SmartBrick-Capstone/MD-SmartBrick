package com.github.emmpann.smartbrick.di

import com.github.emmpann.smartbrick.core.data.local.repository.ArticleRepository
import com.github.emmpann.smartbrick.core.data.local.repository.ImageRepository
import com.github.emmpann.smartbrick.core.data.local.repository.UserRepository
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
    fun provideUserRepository(apiService: ApiService): UserRepository = UserRepository(apiService)

    @Provides
    @Singleton
    fun provideArticleRepository(apiService: ApiService): ArticleRepository = ArticleRepository(apiService)

    @Provides
    @Singleton
    fun provideImageRepository(apiService: ApiService): ImageRepository = ImageRepository(apiService)
}