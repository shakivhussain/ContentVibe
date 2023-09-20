package com.shakiv.husain.contentvibe.data.mapper

import com.shakiv.husain.contentvibe.data.model.PostActions
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.Post


fun PostEntity.toPost(): Post {
    return Post(
        id = id,
        post = post,
        date = date,
        isLiked = isLiked,
        likes = likes ?: 0,
        usedId = user?.userId ?: "",
        userName = user?.userName ?: "",
        userAbout = user?.userAbout ?: "",
        userProfile = user?.userProfile ?: "",
        imageUrl = images,
        currentUserLike = currentUserLike.toMutableList()
    )
}


fun Post.toPostEntity(): PostEntity {
    return PostEntity(
        id = id.orEmpty(),
        post = post.orEmpty(),
        isLiked = isLiked ?: false,
        likes = likes,
        images = imageUrl,
        date = date.orEmpty(),
        user = UserEntity(
            userId = usedId ?: "",
            userName = userName ?: "",
            userAbout = userAbout ?: "",
            userProfile = userProfile ?: ""
        ),
        postActions = PostActions(
            isLiked = isLiked ?: false,
            isDislike = false,
            likes = likes
        )
    )
}

