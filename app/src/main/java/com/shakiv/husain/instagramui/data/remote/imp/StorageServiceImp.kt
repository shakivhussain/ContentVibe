package com.shakiv.husain.instagramui.data.remote.imp

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.perf.ktx.trace
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.data.model.PostEntity
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImp @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val
            posts: Flow<List<PostEntity>>
        get() =
//            auth.currentUser.flatMapLatest { user ->
                firestore.collection("stage_post")
                    .dataObjects()
//            }


    override val stories: Flow<List<StoryItem>>
        get() = firestore.collection(STORY_COLLECTION).dataObjects()

    override suspend fun saveStory(storyItem: StoryItem) : String =
        trace(SAVE_STORY_TRACE) {
            storyItem.also {
                it.userId=auth.currentUserId
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

            firestore.collection("stage_post").add(postEntity).await().id
        }


    override suspend fun update(postEntity: PostEntity): Unit =
        trace(UPDATE_POST_TRACE) {

            firestore.collection(POST_COLLECTION).document(postEntity.id).set(postEntity).await()
        }

    override suspend fun delete(postId: String) {
        firestore.collection(POST_COLLECTION).document(postId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val POST_COLLECTION = "posts"
        private const val STORY_COLLECTION = "stories"
        private const val SAVE_STORY_TRACE = "saveStory"
        private const val SAVE_POST_TRACE = "savePost"
        private const val UPDATE_POST_TRACE = "updatePost"
    }
}

