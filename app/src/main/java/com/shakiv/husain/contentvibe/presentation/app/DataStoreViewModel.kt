package com.shakiv.husain.contentvibe.presentation.app

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.domain.model.UserPreferences
import com.shakiv.husain.contentvibe.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ContentVibeViewModel() {

    val userPreferencesFlow: Flow<UserPreferences> = dataStoreRepository.userPreferencesFlow

    fun <T : Any> updatePreferences(
        key: Preferences.Key<T>,
        value: T
    ) {
        viewModelScope.launch {
            dataStoreRepository.updatePreferencesData(key, value)
        }
    }

}