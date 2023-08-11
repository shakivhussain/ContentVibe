package com.shakiv.husain.instagramui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import java.util.UUID


fun randomId() = UUID.randomUUID().toString()


fun Context.getActivity(): Activity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}