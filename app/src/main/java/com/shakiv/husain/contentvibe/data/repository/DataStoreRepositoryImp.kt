package com.shakiv.husain.contentvibe.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.shakiv.husain.contentvibe.domain.model.UserPreferences
import com.shakiv.husain.contentvibe.domain.repository.DataStoreRepository
import com.shakiv.husain.contentvibe.utils.DataStoreConstant.KEY_EMAIL_VERIFICATION_SEND
import com.shakiv.husain.contentvibe.utils.DataStoreConstant.KEY_NEED_TO_SHOW_ONE_TAB_SIGN_IN
import com.shakiv.husain.contentvibe.utils.DataStoreConstant.KEY_USER_NAME_PREFERENCES
import com.shakiv.husain.contentvibe.utils.extentions.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImp @Inject
constructor(
    private val preferences: DataStore<Preferences>
) : DataStoreRepository {
    override val userPreferencesFlow: Flow<UserPreferences>
        get() = preferences.data
            .catch { exeption ->
                if (exeption is IOException) {
                    exeption.printStackTrace()
                    loge(exeption)
                    emit(emptyPreferences())
                } else {
                    emit(emptyPreferences())
                }
            }.map { preferences ->
                mapUserPreferences(preferences)
            }

    override suspend fun <T : Any> updatePreferencesData(key: Preferences.Key<T>, value: T) {
        preferences.edit {preferences->
            preferences[key] = value
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val userName = preferences[KEY_USER_NAME_PREFERENCES].orEmpty()
        val needToShowOneTabSignIn = preferences[KEY_NEED_TO_SHOW_ONE_TAB_SIGN_IN]?:true
        val emailVerificationSend = preferences[KEY_EMAIL_VERIFICATION_SEND]?:false
        return UserPreferences(
            "", userName,
            needToShowOneTabSignIn = needToShowOneTabSignIn,
            emailVerificationSend = emailVerificationSend
        )
    }
}