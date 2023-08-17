package com.shakiv.husain.instagramui.data.model

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import com.shakiv.husain.instagramui.data.StoryItem


data class PostFeed(
    var postEntityList: List<PostEntity>,
    var storyList: List<StoryItem>
)

data class PostEntity(
    @DocumentId val id: String = "",
    val post: String = "",
    val date: String = "",
    val user: UserEntity? = null,
    var isLiked: Boolean = false,
    var likes: Int=0,
    val postActions: PostActions? = null,
    val images : String = ""
)

data class PostActions(
    val isLiked: Boolean = false,
    val isDislike: Boolean = false,
    val likes: Int = 0,
)