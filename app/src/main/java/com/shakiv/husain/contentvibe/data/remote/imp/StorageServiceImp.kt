package com.shakiv.husain.contentvibe.data.remote.imp

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.perf.ktx.trace
import com.google.firebase.storage.FirebaseStorage
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.domain.model.Response
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.domain.service.StorageService
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.IMAGES
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.POST_COLLECTION
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.SAVE_POST_TRACE
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.SAVE_STORY_TRACE
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.STAGE_POST_COLLECTION
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.STORY_COLLECTION
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.UPDATE_POST_TRACE
import com.shakiv.husain.contentvibe.utils.randomId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImp @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
    private val storage: FirebaseStorage
) : StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val
            posts: Flow<List<PostEntity>>
        get() =
//            auth.currentUser.flatMapLatest { user ->
            firestore.collection(STAGE_POST_COLLECTION)
                .orderBy("date", Query.Direction.DESCENDING)
//                .limit(5)
                .dataObjects()
//            }


    override val stories: Flow<List<StoryItem>>
        get() = firestore.collection(STORY_COLLECTION).dataObjects()

    override suspend fun saveStory(storyItem: StoryItem): String =
        trace(SAVE_STORY_TRACE) {
            storyItem.also {
                it.userId = auth.currentUserId
            }
            firestore.collection(STORY_COLLECTION).add(storyItem).await().id
        }


    override suspend fun getPost(postId: String): PostEntity? =
        firestore.collection(POST_COLLECTION).document(postId).get().await()
            .toObject(PostEntity::class.java)


    override suspend fun save(postEntity: PostEntity): String =
        trace(SAVE_POST_TRACE) {
            postEntity.also {
                it.user?.userId = auth.currentUserId
            }

            firestore.collection(STAGE_POST_COLLECTION).add(postEntity).await().id
        }


    override suspend fun update(postEntity: PostEntity): Unit {
        try {
            trace(UPDATE_POST_TRACE) {
                firestore.collection(STAGE_POST_COLLECTION).document(postEntity.id).set(postEntity).await()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override suspend fun delete(postId: String) {
        firestore.collection(STAGE_POST_COLLECTION).document(postId).delete().await()
    }

    override suspend fun addImageToFirebaseStorage(uri: Uri): Response<Uri> {
        return try {
            Response.Loading
            val downloadUrl = storage.reference.child(IMAGES).child("${randomId()}.jpg")
                .putFile(uri).await()
                .storage.downloadUrl.await()
            Response.Success(downloadUrl)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    companion object {

    }
}

