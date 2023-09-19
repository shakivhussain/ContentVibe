package com.shakiv.husain.contentvibe.di

import com.shakiv.husain.contentvibe.data.remote.imp.AccountServiceImp
import com.shakiv.husain.contentvibe.data.remote.imp.StorageServiceImp
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.domain.service.StorageService
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

}