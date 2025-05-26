package com.sam.networkingwithretrofit

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//There are 2 ways to bind
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providePostRepository(impl: PostRepositoryImpl): PostRepository
}
//Method 2
//class RepositoryModule {
//    @Provides
//    @Singleton // Optional: if you want a single instance throughout the app
//    fun providePostRepository(postApi: PostApi): PostRepository {
//        return PostRepositoryImpl(postApi)
//    }
//}