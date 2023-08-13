package com.shakiv.husain.instagramui.presentation.write_post

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.instagramui.data.model.PostEntity
import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.model.Response
import com.shakiv.husain.instagramui.domain.repository.PhotoSaverRepository
import com.shakiv.husain.instagramui.domain.service.StorageService
import com.shakiv.husain.instagramui.utils.DateUtils
import com.shakiv.husain.instagramui.utils.randomId
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
    private val photoSaver: PhotoSaverRepository
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


    fun isValid(): Boolean {
        val uiState = writePostUiState.value
//        val isValid = uiState.let { state ->
//            !state.isSaving && !photoSaver.isEmpty()
//        }
        return true
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
            refreshSavedPhotos()
        }
    }

    fun writePost() {


        if (!isValid()) {
            return
        }

        writePostViewModelState.update {
            it.copy(isSaving = true)
        }

        viewModelScope.launch {

            val photoList = photoSaver.getPhotos()

            photoList.getOrNull(0)?.let { photo ->

                val photoResponse = storageService.addImageToFirebaseStorage(
                    photo.toUri()
                )

                when (photoResponse) {
                    is Response.Success -> {

                    }

                    is Response.Failure -> {
                        writePostViewModelState.update {
                            it.copy(errorMessage = "Error in uploading image")
                        }
                    }

                    else -> {
                        writePostViewModelState.update {
                            it.copy(errorMessage = "Error in uploading image")
                        }
                    }
                }
            }


            val user = UserEntity(
                userId = randomId(),
                isAnonymous = false,
                userName = " Shakiv Husain",
                userAbout = "Professional",
                userProfile = "IconsInstagram.ProfilePic"
            )

            val post = PostEntity(
                post = writePostUiState.value.post,
                date = DateUtils.getCurrentUTCTime(),
                user = user,
//                images = imageUrls
            )
            storageService.save(post)

            writePostViewModelState.update {
                it.copy(isSaved = true)
            }
        }
    }

    private fun uploadImages() {

        viewModelScope.launch {
            var imageUrls = mutableListOf<String>()

            photoSaver.getPhotos().forEach { photo ->
                val imageUrlResponse: Response<Uri> =
                    storageService.addImageToFirebaseStorage(photo.toUri())

                when (imageUrlResponse) {
                    is Response.Success -> {
                        imageUrls.add(imageUrlResponse.data.toString() ?: return@forEach)
                    }

                    is Response.Failure -> {
                        writePostViewModelState.update {
                            it.copy(
                                errorMessage = "Error in uploading image"
                            )
                        }
                    }

                    else -> {}
                }

            }
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

