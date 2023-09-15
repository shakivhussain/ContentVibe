package com.shakiv.husain.instagramui.presentation.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.instagramui.utils.extentions.logd
import com.shakiv.husain.instagramui.utils.snackbar.SnackBarManager
import com.shakiv.husain.instagramui.utils.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class ContentVibeViewModel : ViewModel() {
    fun launchCatching(snackBar: Boolean = true, errorBlock : (String) -> Unit, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    logd( "launchCatching: ${throwable.printStackTrace()} ")
                    SnackBarManager.showMessage(throwable.toSnackBarMessage())
                    errorBlock(throwable.message.orEmpty())
                }
            },
            block = block
        )
}