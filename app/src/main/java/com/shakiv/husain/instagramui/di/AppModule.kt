package com.shakiv.husain.instagramui.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBuilder(app: Application) : Retrofit.Builder{
        val chucker = ChuckerInterceptor.Builder(app).build()
        val client = OkHttpClient.Builder()
            .addInterceptor(chucker)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().client(client).addConverterFactory(MoshiConverterFactory.create())
    }


    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver{
        return context.contentResolver
    }



}