package com.shakiv.husain.instagramui.utils.extentions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.perf.ktx.trace
import com.google.firebase.perf.metrics.Trace


@Composable
fun getContext(): Context {
    return LocalContext.current
}
