package com.shakiv.husain.contentvibe.domain.model

data class Post(
    val id: String? = "",
    val post: String? = "",
    val date: String? = "",
    val imageUrl: String = "",
    var isLiked: Boolean? = false,
    var likes: Int = 0,
    val usedId: String? = "",
    val userName: String? = "",
    val userAbout: String? = "",
    val userProfile: String? = ""
)
