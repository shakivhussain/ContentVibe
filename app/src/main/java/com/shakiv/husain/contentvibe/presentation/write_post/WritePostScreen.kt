package com.shakiv.husain.contentvibe.presentation.write_post

import android.Manifest.permission.CAMERA
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.shakiv.husain.contentvibe.data.repository.PhotoSaverRepositoryImp.Companion.MAX_LOG_PHOTOS_LIMIT
import com.shakiv.husain.contentvibe.domain.model.NavigationArgsState
import com.shakiv.husain.contentvibe.domain.model.Response
import com.shakiv.husain.contentvibe.presentation.common.composable.PhotoGrid
import com.shakiv.husain.contentvibe.presentation.common.composable.ProgressBar
import com.shakiv.husain.contentvibe.presentation.common.composable.TopAppBar
import com.shakiv.husain.contentvibe.presentation.common.composable.WritePostField
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils
import com.shakiv.husain.contentvibe.utils.extentions.logd
import com.shakiv.husain.contentvibe.utils.getActivity
import kotlinx.coroutines.launch
import com.shakiv.husain.contentvibe.R.string as AppText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePostScreen(
    navigationArgsState: NavigationArgsState? = null,
    writePostViewModel: WritePostViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onCameraClick: () -> Unit
) {
    val writePostState by writePostViewModel.writePostUiState.collectAsStateWithLifecycle()


    logd("StoryItemClicked : 1")

    logd("StoryItemClicked : ${navigationArgsState?.isStoryClicked}")



    var isEnabled by remember { mutableStateOf(true) }
    val focusRequest = remember { FocusRequester() }
    val context = LocalContext.current

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent(),
        onResult = writePostViewModel::onPhotoPickerSelect
    )

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState: ScrollState = rememberScrollState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    LaunchedEffect(key1 = navigationArgsState?.isStoryClicked){
        writePostViewModel.isCurrentScreenIsStory(navigationArgsState?.isStoryClicked)
    }

    LaunchedEffect(writePostState.savedPhotos){
        if (!writePostState.savedPhotos.isNullOrEmpty()){
            writePostViewModel.uploadCameraImage()
        }
    }

    LaunchedEffect(Unit) {
        writePostViewModel.refreshSavedPhotos()
    }

    LaunchedEffect(focusRequest) {
        focusRequest.requestFocus()
    }

    LaunchedEffect(writePostState.isSaved) {

        if (!writePostState.isSaved)
            return@LaunchedEffect

        writePostViewModel.clearImages()
        popBackStack()
    }


    fun canAddPhoto(callback: () -> Unit) {
        if (writePostViewModel.canAddPhoto()) {
            callback()
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("You can't add more then $MAX_LOG_PHOTOS_LIMIT")
            }
        }
    }

    val requestCameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            writePostViewModel.onPermissionChange(permission = CAMERA, isGranted)
            canAddPhoto {
                onCameraClick()
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Camera currently disabled due to denied permission")
            }
        }
    }


    var showExplanationDialogForCameraPermission by remember { mutableStateOf(false) }

    if (showExplanationDialogForCameraPermission) {
        CameraExplanationDialog(
            onConfirm = {
                requestCameraPermission.launch(CAMERA)
                showExplanationDialogForCameraPermission = false
            },
            onDismiss = {
                showExplanationDialogForCameraPermission = false
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior,
                popUpScreen = {
                    writePostViewModel.clearImages()
                    popBackStack()
                },
                actions = {

                    val enabledContainerColor = MaterialTheme.colorScheme.primaryContainer
                    val enabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer

                    val disableContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    val disableContentColor = MaterialTheme.colorScheme.onSurfaceVariant

                    TextButton(
                        onClick = {
                            writePostViewModel.writePost()
                            isEnabled = !isEnabled
                            EmptyDestinationsNavigator.popBackStack()
                        },
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = isEnabled && !writePostState.isImageUploading && !writePostState.post.isEmpty() || !writePostState.imageUrl.isNullOrEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = enabledContainerColor,
                            contentColor = enabledContentColor,
                            disabledContainerColor = disableContainerColor,
                            disabledContentColor = disableContentColor
                        ),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(.1.dp, MaterialTheme.colorScheme.onBackground)
                    ) {

                        if (writePostState.isSaving) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = stringResource(id = AppText.post_text),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                            )
                        }
                    }

                }

            )
        },
        floatingActionButton = {
        },
        bottomBar = {
            BottomView(
                onMediaClick = {
                    canAddPhoto {
                        coroutineScope.launch {
                            writePostViewModel.loadLocalPickerPictures()
                            pickImage.launch(
                                "image/**"
                            )
                        }
                    }
                },

                onCameraClick = {
                    canAddPhoto {

                        canAddPhoto() {


                            when {
                                writePostState.hasCameraAccess -> onCameraClick()
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                    context.getActivity(), CAMERA
                                ) -> showExplanationDialogForCameraPermission = true

                                else -> {
                                    requestCameraPermission.launch(CAMERA)
                                }
                            }
                        }
                    }
                }
            )
        },
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                WritePostField(
                    placeHolder = AppText.write_post_placeholder,
                    value = writePostState.post,
                    onNewValue = writePostViewModel::onPostTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .focusRequester(focusRequest)
                )
                PhotoGrid(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    photos = writePostState.savedPhotos,
                    onRemove = { photo, index ->
                        writePostViewModel.onPhotoRemoved(photo, index)
                    })

            }


        }

    }


    AddImageToStorage(
        writePostState.addImageToStorageState,
        addImageToDatabase = { photoUrl ->
            writePostViewModel.updateImageUrl(photoUrl)
        }
    )

}


@Composable
fun AddImageToStorage(
    photoResponse: Response<Uri>,
    addImageToDatabase: (downloadUrl: String) -> Unit
) {
    when (photoResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> photoResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                addImageToDatabase(downloadUrl.toString())
            }
        }

        is Response.Failure -> print(photoResponse.e)

        else -> {
            print("Something went wrong")
        }
    }
}

@Composable
fun CameraExplanationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(id = AppText.camera_access)) },
        text = {
            Text(
                "PhotoLog would like access to the camera to be able take picture when creating a log"
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Camera, contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = "Continue")
            }
        },

        dismissButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = "Dismiss")
            }
        }
    )
}


@Composable
fun BottomView(modifier: Modifier = Modifier, onMediaClick: () -> Unit, onCameraClick: () -> Unit) {

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 8.dp),
        mainAxisSpacing = 8.dp,
        mainAxisAlignment = MainAxisAlignment.Start
    ) {


        ElevatedButton(
            onClick = { onMediaClick() },
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = .5.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageUtils.setImage(
                    imageId = IconsContentVibe.IC_MEDIA, modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
                Text(
                    text = stringResource(id = AppText.media),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }


        ElevatedButton(
            onClick = {
                onCameraClick()
            },
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = .5.dp,
                pressedElevation = 8.dp
            ),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageUtils.setImage(
                    imageId = IconsContentVibe.IC_CAMERA,
                    modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
                Text(
                    text = stringResource(id = AppText.camera),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer

                )
            }
        }

    }


}

