package com.shakiv.husain.instagramui.data

import android.app.Application
import android.content.Context
import com.shakiv.husain.instagramui.data.post.FeedPostRepository
import com.shakiv.husain.instagramui.data.post.PostRepository

interface AppContainer {
    val postRepository : PostRepository
}


class AppContainerImp(private val applicationContext: Context) : AppContainer {

    override val postRepository: PostRepository by lazy {
        FeedPostRepository()
    }


}