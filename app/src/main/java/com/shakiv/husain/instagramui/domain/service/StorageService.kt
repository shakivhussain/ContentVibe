package com.shakiv.husain.instagramui.domain.service

import com.shakiv.husain.instagramui.data.post.PostItem
import kotlinx.coroutines.flow.Flow

interface StorageService {

    val posts: Flow<List<PostItem>>
    suspend fun getPost(postId: String): PostItem?
    suspend fun save(postItem: PostItem):String
    suspend fun update(postItem: PostItem)
    suspend fun delete(postId: String)

}