package com.shakiv.husain.contentvibe.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.shakiv.husain.contentvibe.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {


    val userPreferencesFlow : Flow<UserPreferences>
    suspend fun <T:Any>updatePreferencesData(
        key: Preferences.Key<T>,
        value : T
    )

}