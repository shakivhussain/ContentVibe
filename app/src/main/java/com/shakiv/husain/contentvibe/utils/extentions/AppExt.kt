package com.shakiv.husain.contentvibe.utils.extentions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun getContext(): Context {
    return LocalContext.current
}
