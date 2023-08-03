package com.shakiv.husain.instagramui.data.post

import com.shakiv.husain.instagramui.data.Resource
import javax.inject.Inject


interface  PostRepository {

    suspend fun getPostFeed() : Resource<PostFeed>

}