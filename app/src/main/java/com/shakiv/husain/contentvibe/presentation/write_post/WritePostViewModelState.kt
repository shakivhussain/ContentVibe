package com.shakiv.husain.contentvibe.presentation.write_post

import android.net.Uri
import com.shakiv.husain.contentvibe.domain.model.Response
import java.io.File

data class WritePostViewModelState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val post: String = "",
    val hasCameraAccess: Boolean,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val savedPhotos: List<File> = emptyList(),
    val imageUrl     : String = "",
    val isImageUploading: Boolean=false,
    val addImageToStorageState :  Response<Uri> = Response.Success(null),
    val isCurrentScreenIsStoryScreen : Boolean = false
)
