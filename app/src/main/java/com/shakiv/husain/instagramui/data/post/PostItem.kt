package com.shakiv.husain.instagramui.data.post

import com.shakiv.husain.instagramui.data.StoryItem


data class PostFeed(
    var postItemList: List<PostItem>,
    var storyList: List<StoryItem>
)

data class PostItem(
    val id: String = "",
    val post: String = "",
    val user: User,
    var isLiked: Boolean = false,
    val postActions: PostActions
)


data class User(
    var id : String="",
    var name: String = "",
    val about: String = "",
    val profile: Int = 0
)


data class PostActions(
    val isLiked: Boolean = false,
    val isDislike: Boolean = false
)