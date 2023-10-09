package com.shakiv.husain.contentvibe.presentation.story

import com.shakiv.husain.contentvibe.data.StoryItem

data class ViewStoryUIState(
    val isLoading : Boolean = true,
    val storyItem : StoryItem? = null,
)