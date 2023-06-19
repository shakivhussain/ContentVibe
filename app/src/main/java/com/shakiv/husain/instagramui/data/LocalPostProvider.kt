package com.shakiv.husain.instagramui.data

import com.shakiv.husain.instagramui.utils.IconsInstagram

object LocalPostProvider {

    private val allUserPost = mutableListOf<PostItem>()

    private const val post =
        "The world has come closer by social media platforms, So close that you have to think multiple times before posting anything because what you share online directly impacts your relationships with your colleagues, neighbours and friends"
    fun allUserPost(): List<PostItem> {
        for (index in 0 until 500) {
            val postItem = PostItem(
                id = "$index",
                post = "$index $post",
                isLiked = index % 2 == 0,
                user = User(
                    "$index Shakiv Husain", "Professional", profile = IconsInstagram.ProfilePic
                )
            )
            allUserPost.add(postItem)
        }
        return allUserPost
    }

    fun allStory(): List<StoryItem> {
        val allStory = mutableListOf<StoryItem>()
        for (index in 0 until 100) {
            allStory.add(StoryItem("${index}ShakivHusain", storyImage = IconsInstagram.ProfilePic))
        }
        return allStory
    }

}