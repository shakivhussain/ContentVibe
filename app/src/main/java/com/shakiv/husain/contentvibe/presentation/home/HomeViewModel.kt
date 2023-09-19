package com.shakiv.husain.contentvibe.presentation.home

import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.data.mapper.toPost
import com.shakiv.husain.contentvibe.data.mapper.toPostEntity
import com.shakiv.husain.contentvibe.domain.model.Post
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.domain.service.StorageService
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeViewModel
import com.shakiv.husain.contentvibe.presentation.common.composable.BottomSheetItem
import com.shakiv.husain.contentvibe.utils.extentions.logd
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
    private val storageService: StorageService,
    private val accountService: AccountService
) : ContentVibeViewModel() {


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
            val isLiked = (post.isLiked ?: false)
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
                    viewModelState.update {
                        it.copy(
                            posts = posts
                                .map { postEntity ->
                                    postEntity.toPost()
                                }
                        )
                    }
                }
            } catch (e: Exception) {
                logd("Error refreshPosts: $e ")
            }
        }
    }


    fun onMoreOptionIconClick(post: Post) {
        viewModelState.update {
            it.copy(
                clickedPost = post
            )
        }
    }


    fun getBottomSheetItems(): List<BottomSheetItem> {

        val postUserId = viewModelState.value.clickedPost?.usedId ?: return emptyList()

        return if (postUserId == accountService.currentUserId) {
            listOf(
                BottomSheetItem.HIDE,
                BottomSheetItem.REPORT,
                BottomSheetItem.DELETE
            )
        } else {
            listOf(
                BottomSheetItem.HIDE,
                BottomSheetItem.REPORT,
            )
        }

    }

    fun onItemClickMoreOption(bottomSheetItem: BottomSheetItem) {
        when (bottomSheetItem) {
            BottomSheetItem.HIDE -> {

            }

            BottomSheetItem.DELETE -> {
                deletePosts()
            }

            BottomSheetItem.REPORT -> {}
        }
    }

    private fun deletePosts() {
        val postId = viewModelState.value.clickedPost?.id ?: return
        launchCatching(
            errorBlock = {
                logd(" : DeletePosts : $it ")
            }
        ) {
            storageService.delete(postId = postId)
        }
    }


}