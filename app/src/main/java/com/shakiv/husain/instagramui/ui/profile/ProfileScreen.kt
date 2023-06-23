package com.shakiv.husain.instagramui.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.ui.components.ProfileImage
import com.shakiv.husain.instagramui.utils.ImageUtils


@Preview()
@Composable
fun PreviewProfile() {
    ProfileScreen(Modifier)
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileScreen(
        title = "Shakiv Husain",
        onNotificationClick = {},
        onMoreOptionClick = {}
    )
}


@Composable
fun ProfileScreen(
    title: String,
    onNotificationClick: () -> Unit,
    onMoreOptionClick: () -> Unit,
) {

    Surface() {

        Column(modifier = Modifier.padding()) {

            TopBar(
                modifier = Modifier,
                title = title,
                onNotificationClick = onNotificationClick,
                onMoreOptionClick = onMoreOptionClick
            )


            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProfileImage(
                    profilePath = R.drawable.profile_pic,
                    modifier = Modifier.size(70.dp)
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TitleSubtitle(title = "13.K", subtitle = "Posts")
                    TitleSubtitle(title = "13.K", subtitle = "Posts")
                    TitleSubtitle(title = "13.K", subtitle = "Posts")

                }

            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Shakiv Husain",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.placeholder_desc),
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                    Text(text = "Follow")
                }

                Button(modifier = Modifier.weight(1f),onClick = { /*TODO*/ }) {
                    Text(text = "Follow")
                }

                Button(
                    modifier = Modifier.weight(.5f),
                    onClick = { /*TODO*/ }) {
                    ImageUtils.setImage(imageId = R.drawable.ic_notifications)
                }

            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )



        }

    }


}

@Composable
fun TitleSubtitle(title: String, subtitle: String) {


    Column(
        modifier = Modifier.padding(0.dp),
    ) {
        Text(text = title)
        Text(text = subtitle)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier,
    title: String,
    onNotificationClick: () -> Unit,
    onMoreOptionClick: () -> Unit,
) {

    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {

            IconButton(onClick = { /*TODO*/ }) {
                ImageUtils.setImage(
                    imageId = R.drawable.ic_arrow_back, modifier = Modifier.padding(start = 0.dp)
                )
            }

        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.errorContainer,
            actionIconContentColor = MaterialTheme.colorScheme.errorContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = onNotificationClick) {
                ImageUtils.setImage(imageId = R.drawable.ic_notifications)
            }

            IconButton(onClick = onMoreOptionClick) {
                ImageUtils.setImage(imageId = R.drawable.ic_more)
            }
        }
    )

}


