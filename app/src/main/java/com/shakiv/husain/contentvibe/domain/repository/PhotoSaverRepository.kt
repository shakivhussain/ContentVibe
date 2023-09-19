package com.shakiv.husain.contentvibe.domain.repository

import android.net.Uri
import java.io.File

interface PhotoSaverRepository {


    fun getPhotos(): List<File>
    fun isEmpty(): Boolean
    fun canAddPhoto(): Boolean

    fun generatePhotoCacheFile(): File
    fun cacheCapturedPhoto(photo: File): Unit

    suspend fun cacheFromURI(uri: Uri): Unit

    suspend fun cacheFromUris(uris: List<Uri>)

    suspend fun removeFile(photo: File): Unit

    suspend fun savePhotos(): List<File>

    suspend fun clear()
}