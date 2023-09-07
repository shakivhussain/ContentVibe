package com.shakiv.husain.instagramui.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.shakiv.husain.instagramui.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    val userPreferencesFlow : Flow<UserPreferences>
    suspend fun <T:Any>updatePreferencesData(
        key: Preferences.Key<T>,
        value : T
    )

}