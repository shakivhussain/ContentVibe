package com.shakiv.husain.contentvibe.presentation.write_post

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.data.model.PostActions
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.Response
import com.shakiv.husain.contentvibe.domain.repository.PhotoSaverRepository
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.domain.service.StorageService
import com.shakiv.husain.contentvibe.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val storageService: StorageService,
    private val context: Application,
    private val photoSaver: PhotoSaverRepository,
    private val accountService: AccountService
) : ViewModel() {

    private val writePostViewModelState = MutableStateFlow(
        WritePostViewModelState(
            isLoading = true,
            hasCameraAccess = hasPermission(Manifest.permission.CAMERA)
        )
    )

    val writePostUiState = writePostViewModelState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        writePostViewModelState.value
    )


    val currentUserId = accountService.currentUserId

    fun isValid(): Boolean {
        val uiState = writePostUiState.value
        return !uiState.isSaving && !uiState.post.isEmpty()
    }


    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context.applicationContext,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    fun onPermissionChange(permission: String, isGranted: Boolean) {
        when (permission) {
            Manifest.permission.CAMERA -> {
                writePostViewModelState.update {
                    it.copy(hasCameraAccess = isGranted)
                }

            }

            else -> {
                Log.e("OnPermissionChange", "onPermissionChange: $permission ")
            }
        }
    }


    fun loadLocalPickerPictures() {

        viewModelScope.launch {

        }
    }


    fun isCurrentScreenIsStory(isStoryScreen: Boolean? = false) {
        viewModelScope.launch {
            writePostViewModelState.update {
                it.copy(
                    isCurrentScreenIsStoryScreen = isStoryScreen ?: false
                )
            }
        }
    }

    fun onLocalPhotoPickerSelect(photo: Uri) {
        viewModelScope.launch {
            photoSaver.cacheFromURI(photo)
            refreshSavedPhotos()
        }
    }


    fun refreshSavedPhotos() {
        writePostViewModelState.update {
            it.copy(
                savedPhotos = photoSaver.getPhotos()
            )
        }
    }

    fun onPostTextChange(post: String) {
        writePostViewModelState.update {
            it.copy(post = post)
        }
    }

    fun onPhotoPickerSelect(photos: Uri?) {
        viewModelScope.launch {
            photoSaver.cacheFromURI(photos ?: return@launch)
            uploadImagesToStorage()
            refreshSavedPhotos()
        }
    }


    fun uploadCameraImage() {
        uploadImagesToStorage()
        refreshSavedPhotos()
    }


    fun writePost() {

        if (!isValid()) {
            return
        }

        writePostViewModelState.update {
            it.copy(isSaving = true)
        }



        viewModelScope.launch {


            val user = accountService.getUserById(currentUserId) ?: UserEntity()



            if (writePostUiState.value.isCurrentScreenIsStoryScreen) {

                val expirationTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000
                val storyItem = StoryItem(
                    user = user,
                    storyImage = writePostUiState.value.imageUrl,
                    publishAt = System.currentTimeMillis(),
                    expireAt = expirationTime
                )

                storageService.saveStory(storyItem)

            } else {

                val post = PostEntity(
                    post = writePostUiState.value.post,
                    date = DateUtils.getCurrentUTCTime(),
                    user = user,
                    images = writePostUiState.value.imageUrl,
                    postActions = PostActions()
                )

                storageService.savePost(post)


            }


            writePostViewModelState.update {
                it.copy(isSaved = true)
            }
        }
    }

    private fun uploadImagesToStorage() {

        viewModelScope.launch {
            val photoList = photoSaver.getPhotos()
            if (photoList.isNotEmpty()) {
                photoList.forEach { photo ->

                    writePostViewModelState.update {
                        it.copy(
                            addImageToStorageState = Response.Loading,
                            isImageUploading = true
                        )
                    }

                    val photoResponse = storageService.addImageToFirebaseStorage(
                        photo.toUri()
                    )
                    writePostViewModelState.update {
                        it.copy(
                            addImageToStorageState = photoResponse,
                            isImageUploading = false
                        )
                    }
                }
            }
        }

    }

    fun clearImages() {
        viewModelScope.launch {
            photoSaver.clear()
        }
    }

    fun updateImageUrl(imageUrl: String) {
        writePostViewModelState.update {
            it.copy(
                imageUrl = imageUrl
            )
        }
    }

    fun canAddPhoto() = photoSaver.canAddPhoto()

    fun onPhotoRemoved(photo: File, index: Int) {

        writePostViewModelState.update {
            it.copy()
        }

        viewModelScope.launch {
            photoSaver.removeFile(photo)
            refreshSavedPhotos()
        }
    }


}

