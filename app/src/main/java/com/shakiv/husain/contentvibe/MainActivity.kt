package com.shakiv.husain.contentvibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeApp
import com.shakiv.husain.contentvibe.presentation.theme.ContentVibeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentVibeTheme {
                ContentVibeApp()
            }
        }
    }
}
