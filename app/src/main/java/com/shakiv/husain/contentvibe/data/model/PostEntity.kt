package com.shakiv.husain.contentvibe.data.model

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.shakiv.husain.contentvibe.data.StoryItem

@Keep
data class PostFeed(
    var postEntityList: List<PostEntity>,
    var storyList: List<StoryItem>
)

@Keep
data class PostEntity(
    @DocumentId val id: String = "",
    val post: String = "",
    val date: String = "",
    val user: UserEntity? = null,
    var isLiked: Boolean = false,
    var likes: Int=0,
    val postActions: PostActions? = null,
    val images : String = "",
    val currentUserLike : List<String> = emptyList()
)

@Keep
data class PostActions(
    val isLiked: Boolean = false,
    val isDislike: Boolean = false,
    val likes: Int = 0,
    val postId : String = "",
)