package com.shakiv.husain.instagramui.domain.model

data class Post(
    val id: String? = "",
    val post: String? = "",
    val isLiked: Boolean? = false,
    val usedId: String? = "",
    val userName: String? = "",
    val userAbout: String? = "",
    val userProfile: String? = ""
)
