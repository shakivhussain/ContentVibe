package com.shakiv.husain.instagramui.data.mapper

import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.model.User


fun UserEntity.toUser(): User{
    return User(
        id = userId,
        isAnonymous = isAnonymous
    )
}