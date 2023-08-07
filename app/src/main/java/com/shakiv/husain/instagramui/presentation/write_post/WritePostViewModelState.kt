package com.shakiv.husain.instagramui.presentation.write_post

data class WritePostViewModelState(
    val isLoading : Boolean = true,
    val errorMessage:String="",
    val post:String=""
)
