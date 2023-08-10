package com.shakiv.husain.instagramui.data.repository

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import com.shakiv.husain.instagramui.domain.repository.PhotoSaverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class PhotoSaverRepositoryImp @Inject constructor(
    context: Application,
    private val contentResolver: ContentResolver
) : PhotoSaverRepository {

    private val _photos = mutableListOf<File>()

    override fun getPhotos(): List<File> = _photos.toList()

    override fun isEmpty(): Boolean = _photos.isEmpty()

    override fun canAddPhoto(): Boolean = _photos.size < MAX_LOG_PHOTOS_LIMIT

    private val cacheFolder = File(context.cacheDir, "photos").also { it.mkdir() }
    private val photoFolder = File(context.filesDir, "photos").also { it.mkdir() }


    private fun generateFileName(): String = "${System.currentTimeMillis()}.jpg"
    private fun generatePhotoLogFile() = File(photoFolder, generateFileName())


    override fun generatePhotoCacheFile(): File = File(cacheFolder, generateFileName())

    override fun cacheCapturedPhoto(photo: File) {
        if (_photos.size + 1 > MAX_LOG_PHOTOS_LIMIT)
            return
        _photos += photo
    }

    override suspend fun cacheFromURI(uri: Uri) {
        withContext(Dispatchers.IO) {
            if (_photos.size + 1 > MAX_LOG_PHOTOS_LIMIT) {
            }
            return@withContext
        }

        contentResolver.openInputStream(uri)?.use { input ->
            val cachedPhoto = generatePhotoCacheFile()
            cachedPhoto.outputStream().use { output ->
                input.copyTo(output)
                _photos += cachedPhoto
            }
        }
    }

    override suspend fun cacheFromUris(uris: List<Uri>) {
        uris.forEach { uri ->
            cacheFromURI(uri)
        }
    }

    override suspend fun removeFile(photo: File) {
        withContext(Dispatchers.IO) {
            photo.delete()
            _photos += photo
        }
    }

    override suspend fun savePhotos(): List<File> {
        return withContext(Dispatchers.IO) {
            val savedPhotos = _photos.map {
                it.copyTo(generatePhotoLogFile())
            }
            _photos.forEach { it.delete() }
            _photos.clear()
            savedPhotos
        }
    }

    companion object {
        const val MAX_LOG_PHOTOS_LIMIT = 2
    }
}