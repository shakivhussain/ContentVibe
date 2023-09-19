package com.shakiv.husain.contentvibe.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

object TimerUtils {

    @Composable
    fun Timer(tick: (Int) -> Unit) {

        var ticks by remember { mutableStateOf(AppUtils.AUTO_SIGN_IN_TIMER) }

        LaunchedEffect(Unit) {
            while (ticks > 0) {
                delay(1.seconds)
                tick(ticks)
                ticks--
            }
        }
    }
}