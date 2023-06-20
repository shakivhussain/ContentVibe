package com.shakiv.husain.instagramui.data.post




data class PostFeed(
    var postItem: List<PostItem>
)

data class PostItem(
    val id: String = "",
    val post: String = "",
    val user: User,
    var isLiked: Boolean = false,
    val postActions: PostActions
)


data class User(
    val name: String = "",
    val about: String = "",
    val profile: Int = 0
)


data class PostActions(
    val isLiked: Boolean = false,
    val isDislike: Boolean = false
)