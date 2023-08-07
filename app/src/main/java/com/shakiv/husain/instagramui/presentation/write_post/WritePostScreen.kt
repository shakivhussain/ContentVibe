package com.shakiv.husain.instagramui.presentation.write_post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shakiv.husain.instagramui.presentation.common.composable.TopAppBar
import com.shakiv.husain.instagramui.presentation.common.composable.WritePostField
import com.shakiv.husain.instagramui.R.string as AppText


@Preview(
    device = "id:pixel_5",
    showBackground = false,
    showSystemUi = false,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun WritePostPreview() {
    WritePostScreen() {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePostScreen(
    writePostViewModel: WritePostViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {

    val writePostState by writePostViewModel.writePostUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize().imePadding(),
    ) {
        Column {

            var isEnabled by remember { mutableStateOf(true) }
            val focusRequest = remember { FocusRequester() }

            LaunchedEffect(focusRequest) {
                focusRequest.requestFocus()
            }

            val enabledButtonColor = MaterialTheme.colorScheme.background
            val disableButtonColor = MaterialTheme.colorScheme.secondary

            TopAppBar(
                modifier = Modifier,
                popUpScreen = {
                              popBackStack()
                },
                actions = {
                    TextButton(
                        onClick = {
                            writePostViewModel.writePost()
                            popBackStack()
                        },
                        modifier = Modifier,
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