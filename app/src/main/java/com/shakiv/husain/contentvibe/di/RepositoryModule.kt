package com.shakiv.husain.contentvibe.di

import com.shakiv.husain.contentvibe.data.post.FeedPostRepository
import com.shakiv.husain.contentvibe.data.repository.DataStoreRepositoryImp
import com.shakiv.husain.contentvibe.data.repository.PhotoSaverRepositoryImp
import com.shakiv.husain.contentvibe.domain.repository.DataStoreRepository
import com.shakiv.husain.contentvibe.domain.repository.PhotoSaverRepository
import com.shakiv.husain.contentvibe.domain.repository.PostRepository
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