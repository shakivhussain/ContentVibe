package com.shakiv.husain.contentvibe.domain.model

import com.shakiv.husain.contentvibe.data.StoryItem

data class NavigationArgsState(
    val userId : String = "",
    val isStoryClicked : Boolean = false,
    val storyItem : StoryItem? = null
)
