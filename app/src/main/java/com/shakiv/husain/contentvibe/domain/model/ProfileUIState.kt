package com.shakiv.husain.contentvibe.domain.model

data class ProfileUIState(
    val user: User = User(),
    val userName : String = "",
    val posts : List<Post> = emptyList(),
    val isProfileLoading : Boolean = true,
    val isPostsLoading : Boolean = true,
    val errorInPosts : String = "",
    val errorInProfileDetails : String = ""

)