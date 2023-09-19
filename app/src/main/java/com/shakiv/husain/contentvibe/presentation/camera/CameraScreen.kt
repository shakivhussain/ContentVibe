package com.shakiv.husain.contentvibe.presentation.camera

import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    onPopBack : () -> Unit
) {

    val lifeCycleOwner = LocalLifecycleOwner.current
    val state = cameraViewModel.cameraState

    val previewUseCase = remember { Preview.Builder().build() }

    LaunchedEffect(Unit) {
        val cameraProvider = cameraViewModel.getCameraProvider()
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifeCycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                previewUseCase,
                state.imageCapture
            )
        } catch (e: Exception) {
            Log.e("CameraCapture", "Failed to bind camera use cases", e)
        }
    }


    LaunchedEffect(state.imageFile) {
        if (state.imageFile != null) {
            onPopBack()
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (!state.isTakingPicture) cameraViewModel.takePicture() }
            ) {
                ImageUtils.setImage(
                    imageId = IconsContentVibe.IC_CAMERA, contentDescription = "Take picture"
                )
            }
        },

        ) {

        AndroidView(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {

                    layoutParams = ViewGroup.LayoutParams(
                        MATCH_PARENT,
                        MATCH_PARENT
                    )
                    previewUseCase.setSurfaceProvider(this.surfaceProvider)

                }
            }
        )

    }


}