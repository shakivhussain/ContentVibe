package com.shakiv.husain.instagramui.presentation.write_post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shakiv.husain.instagramui.data.post.PostItem
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils


@Preview(
    device = "id:pixel_5",
    showBackground = false,
    showSystemUi = false,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun WritePostPreview() {
    WritePostScreen(){

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePostScreen(
    onItemClick: (String) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {

            var isEnabled by remember { mutableStateOf(true) }
            val focusRequest = remember { FocusRequester() }


            LaunchedEffect(focusRequest){
                focusRequest.requestFocus()
            }

            val enabledButtonColor = MaterialTheme.colorScheme.background
            val disableButtonColor = MaterialTheme.colorScheme.secondary

            TopAppBar(
                modifier = Modifier
                    .padding(top = 0.dp)
                    .padding(start = 0.dp),

                title = {

                },
                navigationIcon = {


                    IconButton(onClick = { onItemClick("") }) {
                        ImageUtils.setImage(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(30.dp),
                            imageId = IconsInstagram.IcBack,
                            colorFilter = ColorFilter.tint(
                                MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }

                },

                actions = {
                    TextButton(
                        onClick = {
                            isEnabled = !isEnabled
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
                            text = "Post",
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier

                        )
                    }

                }
            )

            var text by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                placeholder = {
                    Text(text = "Write something here...")
                },
                singleLine = false,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    placeholderColor = MaterialTheme.colorScheme.onSurface,

                    ),

                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp).focusRequester(focusRequester = focusRequest)
            )


        }
    }
}