package com.shakiv.husain.instagramui.presentation.write_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.instagramui.data.model.PostEntity
import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.StorageService
import com.shakiv.husain.instagramui.utils.DateUtils
import com.shakiv.husain.instagramui.utils.randomId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val storageService: StorageService,
    private val accountService: AccountService
) : ViewModel() {


    private val writePostViewModelState = MutableStateFlow(
        WritePostViewModelState(isLoading = true)
    )

    val writePostUiState = writePostViewModelState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        writePostViewModelState.value
    )

    fun onPostTextChange(post: String) {
        writePostViewModelState.update {
            it.copy(post = post)
        }
    }

    fun writePost() {
        viewModelScope.launch {

            val user = UserEntity(
                userId = randomId(),
                isAnonymous = false,
                userName = " Shakiv Husain",
                userAbout = "Professional",
                userProfile = "IconsInstagram.ProfilePic"
            )

            val post = PostEntity(
                post = writePostUiState.value.post,
                date = DateUtils.getCurrentUTCTime(),
                user = user,
            )
            storageService.save(post)

        }
    }

}