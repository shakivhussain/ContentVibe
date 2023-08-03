package com.shakiv.husain.instagramui.di

import com.shakiv.husain.instagramui.data.remote.imp.AccountServiceImp
import com.shakiv.husain.instagramui.domain.service.AccountService
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
    abstract fun provideAccountService(accountServiceImp: AccountServiceImp) : AccountService

}