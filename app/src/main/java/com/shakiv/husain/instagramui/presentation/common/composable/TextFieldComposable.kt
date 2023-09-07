package com.shakiv.husain.instagramui.presentation.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.sp
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils
import kotlinx.coroutines.job
import com.shakiv.husain.instagramui.R.drawable as AppIcon
import com.shakiv.husain.instagramui.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePostField(
    @StringRes placeHolder: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = { newText ->
            onNewValue(newText)
        },
        placeholder = {
            Text(text = stringResource(id = placeHolder))
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
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicField(
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(id = text)) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val focusRequest = FocusRequester()

    OutlinedTextField(
        singleLine = true,
        value = value,
        label = {
            Text(
                text = stringResource(id = AppText.email)
            )
        },
        onValueChange = onNewValue,
        modifier = modifier.focusRequester(focusRequest),
        placeholder = { Text(stringResource(AppText.enter_email)) },
        leadingIcon = {
            ImageUtils.setImage(
                IconsInstagram.IC_EMAIL, contentDescription = "Email",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    )

    LaunchedEffect(key1 = Unit) {
        coroutineContext.job.invokeOnCompletion {
            focusRequest.requestFocus()
        }
    }
}


@Composable
fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PasswordField(
        value = value, onNewValue = onNewValue, placeholder = AppText.enter_password, modifier = modifier
    )
}


@Composable
fun ConfirmPassword(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(
        value = value, onNewValue = onNewValue, placeholder = AppText.confirm_password,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int
) {

    var isVisible by remember { mutableStateOf(false) }

    val icon = if (isVisible) painterResource(id = AppIcon.ic_visibility_on) else painterResource(
        AppIcon.ic_visibility_off
    )
    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onNewValue,
        placeholder = { Text(stringResource(placeholder)) },
        leadingIcon = {
            ImageUtils.setImage(
                IconsInstagram.IC_LOCK, contentDescription = "Lock",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        },
        label = {
            Text(
                text = stringResource(id = AppText.password)
            )
        },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )

}
