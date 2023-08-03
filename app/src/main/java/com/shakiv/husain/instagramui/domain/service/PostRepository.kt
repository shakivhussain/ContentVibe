package com.shakiv.husain.instagramui.domain.service

import com.shakiv.husain.instagramui.data.Resource
import com.shakiv.husain.instagramui.data.post.PostFeed


interface  PostRepository {

    suspend fun getPostFeed() : Resource<PostFeed>

}