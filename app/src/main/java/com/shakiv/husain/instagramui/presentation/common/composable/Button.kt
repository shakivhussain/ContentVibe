package com.shakiv.husain.instagramui.presentation.common.composable

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.utils.ImageUtils


@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit
) {

    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
    ) {

        Text(text = stringResource(id = text), fontSize = 16.sp)

    }
}

@Preview
@Composable
fun CardEditorPreview() {
    CardEditor(
        R.string.password,
        R.drawable.ic_account,
        "Shakib Mansoori",
        { /*TODO*/ },
        MaterialTheme.colorScheme.primary,
        Modifier.padding(0.dp)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "BasicButtonPreview")
@Composable
fun BasicButtonPreview() {
    BasicButton(
        title = R.string.forgot_password,
        icon = R.drawable.ic_account,
        onButtonClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        highlightColor =  MaterialTheme.colorScheme.primary
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "BasicSmallButtonPreview")
@Composable
fun BasicSmallButtonPreview() {
    BasicSmallButton(
        title = R.string.forgot_password,
        icon = R.drawable.ic_account,
        onButtonClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        highlightColor =  MaterialTheme.colorScheme.primary
    )
}

@Composable
fun RegularButton(
    @StringRes title: Int,
    @DrawableRes icon: Int? = null,
    onButtonClick: () -> Unit,
    cardColors: CardColors = CardDefaults.cardColors(),
    modifier: Modifier = Modifier,
    highlightColor: Color,
    applyTintOnIcon: Boolean= true,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp,
        pressedElevation = 16.dp
    ),
    border: BorderStroke? = null,
) {
    BasicButton(title, icon, onButtonClick, cardColors, modifier, highlightColor,applyTintOnIcon, shape, elevation, border)
}


@Composable
fun RegularSmallButton(
    @StringRes title: Int,
    @DrawableRes icon: Int? = null,
    onButtonClick: () -> Unit,
    colors: CardColors = CardDefaults.cardColors(),
    modifier: Modifier = Modifier,
    highlightColor: Color,
    applyTintOnIcon : Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp,
        pressedElevation = 16.dp
    ),
    border: BorderStroke? = null,
) {
    BasicSmallButton(title, icon, onButtonClick, colors, modifier, highlightColor,applyTintOnIcon,shape, elevation, border)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicButton(
    @StringRes title: Int,
    @DrawableRes icon: Int? = null,
    onButtonClick: () -> Unit,
    colors: CardColors = CardDefaults.cardColors(),
    modifier: Modifier = Modifier,
    highlightColor: Color,
    applyTintOnIcon : Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp,
        pressedElevation = 16.dp
    ),
    border: BorderStroke? = null,
) {

    Card(
        colors = colors,
        modifier = modifier,
        onClick = onButtonClick,
        shape = shape,
        elevation = elevation,
        border = border
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(16.dp)
        ) {

            if (icon != null) {
                ImageUtils.setImage(
                    imageId = icon,
                    colorFilter = if (applyTintOnIcon) ColorFilter.tint(color = highlightColor) else null
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = stringResource(id = title),
                modifier = Modifier,
                color = highlightColor,
                style = MaterialTheme.typography.labelLarge
            )

        }

    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicSmallButton(
    @StringRes title: Int,
    @DrawableRes icon: Int? = null,
    onButtonClick: () -> Unit,
    colors: CardColors = CardDefaults.cardColors(),
    modifier: Modifier = Modifier,
    highlightColor: Color,
    applyTintOnIcon : Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp,
        pressedElevation = 16.dp
    ),
    border: BorderStroke? = null,
) {

    Card(
        colors = colors,
        modifier = modifier,
        onClick = onButtonClick,
        shape = shape,
        elevation = elevation,
        border = border
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {

            if (icon != null) {
                ImageUtils.setImage(
                    imageId = icon,
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (applyTintOnIcon) ColorFilter.tint(color = highlightColor) else null
                )
                Spacer(modifier = Modifier.size(8.dp))
            }


            Text(
                text = stringResource(id = title),
                modifier = Modifier,
                color = highlightColor,
                style = MaterialTheme.typography.labelLarge
            )

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(title), color = highlightColor
                )

            }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Icon(
                painter = painterResource(icon), contentDescription = "Icon", tint = highlightColor
            )
        }
    }
}