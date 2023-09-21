package com.shakiv.husain.contentvibe.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce

object MultipleEventsCutterUtils {

    @OptIn(FlowPreview::class)
    @Composable
    fun <T>multipleEventsCutter(
        content: @Composable (MultipleEventsCutterManager) -> T
    ) : T {
        val debounceState = remember {
            MutableSharedFlow<() -> Unit>(
                replay = 0,
                extraBufferCapacity = 1,
                onBufferOverflow = BufferOverflow.DROP_OLDEST
            )
        }

        val result = content(
            object : MultipleEventsCutterManager {
                override fun processEvent(event: () -> Unit) {
                    debounceState.tryEmit(event)
                }
            }
        )

        LaunchedEffect(true) {
            debounceState
                .debounce(300L)
                .collect { onClick ->
                    onClick.invoke()
                }
        }

        return result
    }

}
interface MultipleEventsCutterManager {
    fun processEvent(event: () -> Unit)
}

