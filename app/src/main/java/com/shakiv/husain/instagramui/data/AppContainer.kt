package com.shakiv.husain.instagramui.data

import android.content.Context
import com.shakiv.husain.instagramui.data.post.FeedPostRepository
import com.shakiv.husain.instagramui.domain.service.PostRepository

interface AppContainer {
    val postRepository : PostRepository
}


class AppContainerImp(private val applicationContext: Context) : AppContainer {

    override val postRepository: PostRepository by lazy {
        FeedPostRepository()
    }
}