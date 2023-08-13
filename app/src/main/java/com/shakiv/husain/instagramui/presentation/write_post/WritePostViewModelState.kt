package com.shakiv.husain.instagramui.presentation.write_post

import android.net.Uri
import java.io.File

data class WritePostViewModelState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val post: String = "",
    val hasCameraAccess: Boolean,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val savedPhotos: List<File> = emptyList(),
    val imageUrls : MutableList<String> = mutableListOf()
)
