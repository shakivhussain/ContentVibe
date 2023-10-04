package com.shakiv.husain.contentvibe.presentation.app

import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.domain.model.NavigationArgsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class SharedViewModel : ContentVibeViewModel() {


    private val _navigationArgsState = MutableStateFlow(NavigationArgsState())


    val navigationArgsState = _navigationArgsState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _navigationArgsState.value
    )



    fun updateUserId(userId: String){
        _navigationArgsState.update {
            it.copy(
                userId = userId
            )
        }
    }

    fun setStoryState(isStoryClicked : Boolean){
        _navigationArgsState.update {
            it.copy(
                isStoryClicked = isStoryClicked
            )
        }
    }


}