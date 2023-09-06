package com.shakiv.husain.instagramui.di

import com.shakiv.husain.instagramui.data.post.FeedPostRepository
import com.shakiv.husain.instagramui.data.repository.DataStoreRepositoryImp
import com.shakiv.husain.instagramui.data.repository.PhotoSaverRepositoryImp
import com.shakiv.husain.instagramui.domain.repository.DataStoreRepository
import com.shakiv.husain.instagramui.domain.repository.PhotoSaverRepository
import com.shakiv.husain.instagramui.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindRepositoryModule(photoSaverRepositoryImp: PhotoSaverRepositoryImp):PhotoSaverRepository


    @Binds
    @Singleton
    abstract fun bindFeedRepository(postRepository: FeedPostRepository): PostRepository

    @Binds
    @Singleton
    abstract fun bindDataRepository(dataStoreRepositoryImp: DataStoreRepositoryImp) : DataStoreRepository

}