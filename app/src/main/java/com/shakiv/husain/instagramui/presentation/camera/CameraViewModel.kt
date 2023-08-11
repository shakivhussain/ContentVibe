package com.shakiv.husain.instagramui.presentation.camera

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.instagramui.InstagramApplication
import com.shakiv.husain.instagramui.domain.repository.PhotoSaverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class CameraViewModel @Inject constructor(
    applicationContext: Application,
    private val photoSaver: PhotoSaverRepository,
) : AndroidViewModel(applicationContext) {

    private val context: Context
        get() = getApplication<InstagramApplication>()
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    data class CameraState(
        val isTakingPicture: Boolean = false,
        val imageCapture: ImageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build(),
        val imageFile: File? = null,
        val captureError: ImageCaptureException? = null
    )

    var cameraState by mutableStateOf(CameraState())
        private set

    suspend fun getCameraProvider(): ProcessCameraProvider {
        return suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).apply {
                addListener({ continuation.resume(get()) }, cameraExecutor)
            }
        }
    }


    fun takePicture() {
        viewModelScope.launch {
            cameraState = cameraState.copy(isTakingPicture = true)
            val savedFile = photoSaver.generatePhotoCacheFile()

            cameraState.imageCapture.takePicture(
                ImageCapture.OutputFileOptions.Builder(savedFile).build(),
                cameraExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        photoSaver.cacheCapturedPhoto(savedFile)
                        cameraState = cameraState.copy(
                            imageFile = savedFile
                        )
                        Log.e("TakePicture", "Image Captured Success")

                    }

                    override fun onError(exception: ImageCaptureException) {
                        exception.printStackTrace()
                        Log.e("TakePicture", "Image Capture Error Found")

                    }
                }
            )

        }
    }


}