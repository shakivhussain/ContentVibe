package com.shakiv.husain.contentvibe.domain.model

data class ProfileUIState(
    val user: User = User(),
    val userName : String = "",
    val posts : List<Post> = emptyList()
)