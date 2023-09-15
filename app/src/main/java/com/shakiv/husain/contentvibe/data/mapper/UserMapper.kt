package com.shakiv.husain.contentvibe.data.mapper

import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.User


fun UserEntity.toUser(): User{
    return User(
        id = userId,
        isAnonymous = isAnonymous
    )
}