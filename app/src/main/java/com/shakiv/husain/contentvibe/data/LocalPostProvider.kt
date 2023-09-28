package com.shakiv.husain.contentvibe.data

import androidx.compose.runtime.mutableStateListOf
import com.shakiv.husain.contentvibe.data.model.PostActions
import com.shakiv.husain.contentvibe.data.model.PostFeed
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.utils.IconsContentVibe

object LocalPostProvider {

    private val allUserPost = mutableStateListOf<PostEntity>()

    private const val post =
        "The world has come closer by social media platforms, So close that you have to think multiple times before posting anything because what you share online directly impacts your relationships with your colleagues, neighbours and friends"



    fun getFeed(): PostFeed {
        allUserPost()

        return PostFeed(
            postEntityList = allUserPost,
            storyList = allStory()
        )

    }

    fun allUserPost(): List<PostEntity> {
        for (index in 0 until 500) {
            val user = UserEntity("$index Shakiv Husain", isAnonymous = false, description = "Professional", profileUrl = "contentvibe.ProfilePic")

            val postAction = PostActions(
                isLiked = index % 2 == 0,
                isDislike = index % 2 != 0,
            )

            val postEntity = PostEntity(
                id = "$index",
                post = "$index $post",
                isLiked = index % 2 == 0,
                user = user,
                postActions = postAction
            )

            allUserPost.add(postEntity)
        }
        return allUserPost
    }


    fun updateLike(postId:String, isLiked:Boolean){
        allUserPost.forEach {
            if (postId==it.id){
                it.isLiked=isLiked
            }
        }
    }


    fun allStory(): List<StoryItem> {
        val allStory = mutableListOf<StoryItem>()
        for (index in 0 until 100) {
            allStory.add(StoryItem(id = "New$index","${index}ShakivHusain", storyImage = IconsContentVibe.ProfilePic))
        }
        return allStory
    }

}