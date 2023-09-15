package com.shakiv.husain.contentvibe.data

import android.content.Context
import com.shakiv.husain.contentvibe.data.post.FeedPostRepository
import com.shakiv.husain.contentvibe.domain.repository.PostRepository

interface AppContainer {
    val postRepository : PostRepository
}


class AppContainerImp(private val applicationContext: Context) : AppContainer {

    override val postRepository: PostRepository by lazy {
        FeedPostRepository()
    }
}