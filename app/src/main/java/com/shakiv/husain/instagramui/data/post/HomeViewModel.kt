package com.shakiv.husain.instagramui.data.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel(private val postRepository: PostRepository) : ViewModel() {


    companion object {
        fun provideFactory(postRepository: PostRepository)
                : ViewModelProvider.Factory = object : ViewModelProvider.Factory {


            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(postRepository) as T
            }

        }
    }

}