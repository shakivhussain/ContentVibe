package com.shakiv.husain.instagramui.di

import com.shakiv.husain.instagramui.data.post.FeedPostRepository
import com.shakiv.husain.instagramui.data.post.PostRepository
import com.shakiv.husain.instagramui.data.remote.imp.AccountServiceImp
import com.shakiv.husain.instagramui.data.remote.imp.StorageServiceImp
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.StorageService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindAccountService(accountServiceImp: AccountServiceImp) : AccountService

    @Binds
    @Singleton
    abstract fun  bindStorageService(storageServiceImp: StorageServiceImp) : StorageService

    @Binds
    @Singleton
    abstract fun bindFeedRepository(postRepository: FeedPostRepository): PostRepository


}