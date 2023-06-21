package com.shakiv.husain.instagramui.data.post

import com.shakiv.husain.instagramui.data.Resource

interface PostRepository {

    suspend fun getPostFeed() : Resource<PostFeed>

}