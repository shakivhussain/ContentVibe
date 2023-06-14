package com.shakiv.husain.instagramui.ui

import androidx.compose.runtime.Composable
import com.shakiv.husain.instagramui.HomeFeed
import com.shakiv.husain.instagramui.data.LocalPostProvider.allUserPost

@Composable
fun InstagramApp() {



    HomeFeed(postList = allUserPost)
}