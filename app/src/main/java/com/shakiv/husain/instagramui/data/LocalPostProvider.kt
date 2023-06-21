package com.shakiv.husain.instagramui.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.shakiv.husain.instagramui.data.post.PostActions
import com.shakiv.husain.instagramui.data.post.PostFeed
import com.shakiv.husain.instagramui.data.post.PostItem
import com.shakiv.husain.instagramui.data.post.User
import com.shakiv.husain.instagramui.utils.IconsInstagram

object LocalPostProvider {

    private val allUserPost = mutableStateListOf<PostItem>()

    private const val post =
        "The world has come closer by social media platforms, So close that you have to think multiple times before posting anything because what you share online directly impacts your relationships with your colleagues, neighbours and friends"



    fun getFeed(): PostFeed {
        allUserPost()

        return PostFeed(
            postItemList = allUserPost,
            storyList = allStory()
        )

    }

    fun allUserPost(): List<PostItem> {
        for (index in 0 until 500) {
            val user = User("$index Shakiv Husain", "Professional", profile = IconsInstagram.ProfilePic)

            val postAction = PostActions(
                isLiked = index % 2 == 0,
                isDislike = index % 2 != 0,
            )

            val postItem = PostItem(
                id = "$index",
                post = "$index $post",
                isLiked = index % 2 == 0,
                user = user,
                postActions = postAction
            )

            allUserPost.add(postItem)
        }
        return allUserPost
    }


    fun updateLike(postId:String, isLiked:Boolean){
        Log.d("TAGupdateLike", " 1 updateLike: $postId ")

        allUserPost.forEach {
            if (postId==it.id){

                Log.d("TAGupdateLike", " 2 updateLike: $postId == ${it.id} IsLiked ${it.isLiked}")

                it.isLiked=isLiked
            }
        }
    }


    fun allStory(): List<StoryItem> {
        val allStory = mutableListOf<StoryItem>()
        for (index in 0 until 100) {
            allStory.add(StoryItem("${index}ShakivHusain", storyImage = IconsInstagram.ProfilePic))
        }
        return allStory
    }

}