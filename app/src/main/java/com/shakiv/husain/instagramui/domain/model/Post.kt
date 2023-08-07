package com.shakiv.husain.instagramui.domain.model

data class Post(
    val id: String? = "",
    val post: String? = "",
    val date: String? = "",
    val imageUrl:String="",
    val isLiked: Boolean? = false,
    val likes: Int = 0,
    val usedId: String? = "",
    val userName: String? = "",
    val userAbout: String? = "",
    val userProfile: String? = ""
)
