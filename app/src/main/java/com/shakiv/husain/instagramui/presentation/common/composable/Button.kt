package com.shakiv.husain.instagramui.presentation.common.composable

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp


@Composable
fun BasicButton(
    @StringRes text:Int,
    modifier: Modifier=Modifier,
    action:()->Unit
) {

    Button(
        onClick = action ,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
    ) {

        Text(text = stringResource(id = text), fontSize = 16.sp)

    }
}