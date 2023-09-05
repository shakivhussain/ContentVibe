package com.shakiv.husain.instagramui.presentation.auth

data class LoginUiState(
    var email: String="",
    val password: String="",
    val confirmPassword : String=""
)