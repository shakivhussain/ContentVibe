package com.shakiv.husain.instagramui.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.data.mapper.toPost
import com.shakiv.husain.instagramui.data.mapper.toPostEntity
import com.shakiv.husain.instagramui.domain.model.Post
import com.shakiv.husain.instagramui.domain.repository.PostRepository
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val firebaseFireStore: FirebaseFirestore
) : ViewModel() {


    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true
        )
    )

    val uiState: StateFlow<HomeViewModelState> = viewModelState.stateIn(
        viewModelScope, SharingStarted.Eagerly, viewModelState.value
    )

    init {
        refreshPosts()
    }


    fun onPostLiked(post: Post) {
        viewModelScope.launch {
            val postEntity = post.toPostEntity()
            val isLiked = (post.isLiked?:false)
            storageService.update(
                postEntity.copy(
                    isLiked = !isLiked,
                    likes = if (isLiked) post.likes.minus(1) else post.likes.plus(1)
                )
            )
        }
    }


    private fun refreshPosts() {
        viewModelScope.launch(Dispatchers.IO) {

            storageService.stories.collectLatest { stories: List<StoryItem> ->

                viewModelState.update {
                    it.copy(
                        stories = stories
                    )
                }
            }

        }


        viewModelScope.launch(Dispatchers.IO) {
            try {
                storageService.posts.collectLatest { posts ->


                    Log.d("TAG", "HomeFeed refreshPosts: $posts ")

                    viewModelState.update {
                        it.copy(
                            posts = posts
                                .map { postEntity ->
                                    postEntity.toPost()
                                }.sortedByDescending { post ->
                                    post.date
                                }
                        )
                    }
                }
            } catch (e: Exception) {

                Log.d("TAG", " Error refreshPosts: $e ")

            }
        }
    }


}