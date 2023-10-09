package com.shakiv.husain.contentvibe.data

import com.google.firebase.firestore.DocumentId
import com.shakiv.husain.contentvibe.data.model.UserEntity

data class StoryItem(
    @DocumentId val id: String = "",
    val user: UserEntity? = null,
    val storyImage: String = "",
    val publishAt: Long = 0,
    val expireAt: Long = 0,
    val viewedUsers: MutableList<UserEntity> = mutableListOf(),
    var isViewed : Boolean = false
)
