package com.shakiv.husain.instagramui.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstant {

    const val PREFERENCES_FILE_NAME = "COM_CONTENT_VIBE_DEFAULT_PREFERENCES_FILE_KEY"



    /** Data Store Constants*/
    val KEY_USER_NAME_PREFERENCES = stringPreferencesKey("username")
    val KEY_NEED_TO_SHOW_ONE_TAB_SIGN_IN = booleanPreferencesKey("need_to_show_one_tab_signin")

}