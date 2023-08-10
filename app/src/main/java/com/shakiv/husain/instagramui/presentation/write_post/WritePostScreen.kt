package com.shakiv.husain.instagramui.presentation.write_post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.shakiv.husain.instagramui.presentation.common.composable.TopAppBar
import com.shakiv.husain.instagramui.presentation.common.composable.WritePostField
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils
import kotlinx.coroutines.launch
import com.shakiv.husain.instagramui.R.string as AppText



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePostScreen(
    writePostViewModel: WritePostViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onCameraClick : () -> Unit
) {

    val writePostState by writePostViewModel.writePostUiState.collectAsStateWithLifecycle()
    var isEnabled by remember { mutableStateOf(true) }
    val focusRequest = remember { FocusRequester() }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(2),
        onResult = writePostViewModel::onPhotoPickerSelect
    )

    val coroutineScope = rememberCoroutineScope()





    LaunchedEffect(focusRequest) {
        focusRequest.requestFocus()
    }

    Scaffold(

        modifier = Modifier
            .fillMaxSize()
            .imePadding(),

        topBar = {
            TopAppBar(
                modifier = Modifier,
                popUpScreen = {
                    popBackStack()
                },
                actions = {

                    val enabledButtonColor = MaterialTheme.colorScheme.background
                    val disableButtonColor = MaterialTheme.colorScheme.secondary

                    TextButton(
                        onClick = {
                            writePostViewModel.writePost()
                            isEnabled = !isEnabled
                            popBackStack()
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

                        Text(
                            text = stringResource(id = AppText.post_text),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                        )
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
                        pickImage.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                },
                onCameraClick = {
                    onCameraClick()
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(

            ) {


                WritePostField(
                    placeHolder = AppText.write_post_placeholder,
                    value = writePostState.post,
                    onNewValue = writePostViewModel::onPostTextChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequest)
                )


            }


        }

    }
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
            onClick = {onMediaClick() },
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
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiaryContainer)
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
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onTertiaryContainer)
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
