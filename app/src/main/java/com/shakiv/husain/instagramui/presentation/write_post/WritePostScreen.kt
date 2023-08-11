package com.shakiv.husain.instagramui.presentation.write_post

import android.Manifest.permission.CAMERA
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.shakiv.husain.instagramui.data.repository.PhotoSaverRepositoryImp.Companion.MAX_LOG_PHOTOS_LIMIT
import com.shakiv.husain.instagramui.presentation.common.composable.TopAppBar
import com.shakiv.husain.instagramui.presentation.common.composable.WritePostField
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils
import com.shakiv.husain.instagramui.utils.getActivity
import kotlinx.coroutines.launch
import java.io.File
import com.shakiv.husain.instagramui.R.string as AppText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePostScreen(
    writePostViewModel: WritePostViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onCameraClick: () -> Unit
) {

    val writePostState by writePostViewModel.writePostUiState.collectAsStateWithLifecycle()

    var isEnabled by remember { mutableStateOf(true) }
    val focusRequest = remember { FocusRequester() }
    val context = LocalContext.current

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(MAX_LOG_PHOTOS_LIMIT),
        onResult = writePostViewModel::onPhotoPickerSelect
    )

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState: ScrollState = rememberScrollState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    LaunchedEffect(Unit) {
        writePostViewModel.refreshSavedPhotos()
    }

    LaunchedEffect(focusRequest) {
        focusRequest.requestFocus()
    }

    LaunchedEffect(writePostState.isSaved) {

        if (!writePostState.isSaved)
            return@LaunchedEffect

        popBackStack()
    }



    fun canWritePost(callback: () -> Unit) {
        if (writePostViewModel.isValid()) {
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
            canWritePost {
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

        topBar = {
            TopAppBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior,
                popUpScreen = {
                    popBackStack()
                },
                actions = {

                    val enabledButtonColor = MaterialTheme.colorScheme.background
                    val disableButtonColor = MaterialTheme.colorScheme.secondary

                    TextButton(
                        onClick = {
                            canWritePost {
                                writePostViewModel.writePost()
                                isEnabled = !isEnabled
                                EmptyDestinationsNavigator.popBackStack()
                            }

                        },
                        modifier = Modifier.padding(end = 8.dp),
                        enabled = isEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = enabledButtonColor,
                            contentColor = enabledButtonColor,
                            disabledContainerColor = disableButtonColor,
                            disabledContentColor = disableButtonColor
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
                    coroutineScope.launch {
                        writePostViewModel.loadLocalPickerPictures()
                        pickImage.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                },

                onCameraClick = {

                    Log.d(
                        "TAGWritePostScreen", "WritePostScreen: ${writePostState.hasCameraAccess} "
                    )

                    canWritePost() {


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
//                    onCameraClick()
                }
            )
        }
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
                    modifier = Modifier.padding(16.dp),
                    photos = writePostState.savedPhotos,
                    onRemove = { photo: File ->
                        writePostViewModel.onPhotoRemoved(photo)
                    })

            }


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
        modifier = Modifier
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
                    imageId = IconsInstagram.IC_MEDIA, modifier = Modifier.size(28.dp),
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
                    imageId = IconsInstagram.IC_CAMERA,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGrid(
    modifier: Modifier,
    photos: List<File>,
    onRemove: ((photo: File) -> Unit)? = null
) {

    Row(modifier) {
        repeat(MAX_LOG_PHOTOS_LIMIT) { index ->
            val file = photos.getOrNull(index)

            if (file == null) {
                Box(Modifier.weight(1f))
            } else {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .aspectRatio(1f)
                ) {
                    AsyncImage(
                        model = file,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    if (onRemove != null) {
                        FilledTonalIconButton(onClick = { onRemove(file) }) {
                            Icon(Icons.Filled.Close, null)
                        }
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
        }
    }
}