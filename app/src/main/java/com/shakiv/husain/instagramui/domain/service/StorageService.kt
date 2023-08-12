package com.shakiv.husain.instagramui.domain.service

import android.net.Uri
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.data.model.PostEntity
import com.shakiv.husain.instagramui.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface StorageService {

    val posts: Flow<List<PostEntity>>
    val stories : Flow<List<StoryItem>>

    suspend fun saveStory(storyItem: StoryItem):String
    suspend fun getPost(postId: String): PostEntity?
    suspend fun save(postEntity: PostEntity):String
    suspend fun update(postEntity: PostEntity)
    suspend fun delete(postId: String)

    suspend fun addImageToFirebaseStorage(uri: Uri) : Response<Uri>


}

