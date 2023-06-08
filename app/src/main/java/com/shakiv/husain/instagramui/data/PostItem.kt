package com.shakiv.husain.instagramui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

data class PostItem(
    val id: String = "",
    val post: String = "",
    val user: User,
    val isLiked: Boolean = false
)


data class User(
    val name: String = "",
    val about: String = "",
    val profile: Int = 0
)