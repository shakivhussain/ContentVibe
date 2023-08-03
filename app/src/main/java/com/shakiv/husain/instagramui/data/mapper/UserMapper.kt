package com.shakiv.husain.instagramui.data.mapper

import com.shakiv.husain.instagramui.data.remote.dto.UserDto
import com.shakiv.husain.instagramui.domain.model.User


fun UserDto.toUser(): User{
    return User(
        id = id,
        isAnonymous = isAnonymous
    )
}