package com.shakiv.husain.instagramui.data.remote.imp

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.perf.ktx.trace
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.data.post.PostItem
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImp @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val
            posts: Flow<List<PostItem>>
        get() =
//            auth.currentUser.flatMapLatest { user ->
                firestore.collection(POST_COLLECTION).whereEqualTo(USER_ID_FIELD,"IoEsEzuoAZdzBeq6pQdUKzxOoeI3")
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



    override suspend fun getPost(postId: String): PostItem? =
        firestore.collection(POST_COLLECTION).document(postId).get().await()
            .toObject(PostItem::class.java)


    override suspend fun save(postItem: PostItem): String =
        trace(SAVE_POST_TRACE) {
            postItem.also {
                it.userId = auth.currentUserId
            }

            firestore.collection(POST_COLLECTION).add(postItem).await().id
        }


    override suspend fun update(postItem: PostItem): Unit =
        trace(UPDATE_POST_TRACE) {

            firestore.collection(POST_COLLECTION).document(postItem.id).set(postItem).await()
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

