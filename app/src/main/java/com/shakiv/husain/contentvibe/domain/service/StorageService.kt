package com.shakiv.husain.contentvibe.domain.service

import android.net.Uri
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface StorageService {

    val posts: Flow<List<PostEntity>>
    val stories : Flow<List<StoryItem>>

    suspend fun saveStory(storyItem: StoryItem):String
    suspend fun getPost(postId: String): PostEntity?
    suspend fun getPostsBy(userId: String): Flow<List<PostEntity>?>
    suspend fun savePost(postEntity: PostEntity):String
    suspend fun update(postEntity: PostEntity)
    suspend fun updateStory(storyItem: StoryItem)
    suspend fun delete(postId: String)

    suspend fun addImageToFirebaseStorage(uri: Uri) : Response<Uri>


}

