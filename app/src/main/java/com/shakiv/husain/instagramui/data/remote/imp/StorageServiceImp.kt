package com.shakiv.husain.instagramui.data.remote.imp

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.perf.ktx.trace
import com.shakiv.husain.instagramui.data.post.PostItem
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.StorageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImp @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {
    override val posts: Flow<List<PostItem>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(POST_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }


    override suspend fun getPost(postId: String): PostItem? =
        firestore.collection(POST_COLLECTION).document(postId).get().await()
            .toObject(PostItem::class.java)


    override suspend fun save(postItem: PostItem): String =
        trace(SAVE_POST_TRACE) {
            postItem.also {
                it.user.id = auth.currentUserId
            }
            firestore.collection(POST_COLLECTION).add(postItem).await().id
        }


    override suspend fun update(postItem: PostItem): Unit =
        trace(UPDATE_POST_TRACE) {

            firestore.collection(POST_COLLECTION).document(postItem.id).set(postItem).await()
        }

    override suspend fun delete(postId: String) {
        firestore.collection(POST_COLLECTION).document().delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val POST_COLLECTION = "posts"
        private const val SAVE_POST_TRACE = "savePost"
        private const val UPDATE_POST_TRACE = "updatePost"
    }
}

