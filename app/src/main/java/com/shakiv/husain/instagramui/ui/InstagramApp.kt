package com.shakiv.husain.instagramui.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shakiv.husain.instagramui.Home
import com.shakiv.husain.instagramui.HomeFeed
import com.shakiv.husain.instagramui.InstagramDestination
import com.shakiv.husain.instagramui.InstagramRoutes
import com.shakiv.husain.instagramui.InstagramScreens
import com.shakiv.husain.instagramui.Profile
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.Reels
import com.shakiv.husain.instagramui.Search
import com.shakiv.husain.instagramui.data.LocalPostProvider.allUserPost

@Composable
fun InstagramApp() {
    InstagramAppContent()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstagramAppContent() {


    val navController: NavHostController = rememberNavController()
    val screens = InstagramScreens
    val selectedDestination = remember { mutableStateOf(InstagramRoutes.HOME) }
    val postLazyListState = rememberLazyListState()
    val storyLazyListState = rememberLazyListState()


    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavigation(screens, selectedDestination)
        }
    ) { innerPadding ->

//
//        InstagramNavHost(
//            navController,
//            modifier = Modifier.padding(innerPadding)
//        )

        Crossfade(targetState = selectedDestination.value) { currentDestination ->

            when (currentDestination) {
                Home.route -> {
                    HomeFeed(
                        postList = allUserPost,
                        modifier = Modifier.padding(innerPadding),
                        navController,
                        postLazyListState,
                        storyLazyListState
                    )
                }

                Search.route -> {

                }

                Reels.route -> {

                    EmptyComingSoon()
                }

                Profile.route -> {

                    HomeFeed(
                        postList = allUserPost,
                        modifier = Modifier.padding(innerPadding),
                        navController,
                        postLazyListState,
                        storyLazyListState
                    )
                }
            }

        }


    }

}

//@Composable
//fun InstagramNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(
//        navController = navController, modifier = modifier, startDestination = InstagramRoutes.HOME
//    ) {
//        composable(route = Home.route) {
//
//            HomeFeed(
//                postList = allUserPost,
//                navController = navController,
//                postLazyListState = postLazyListState,
//                storyLazyListState = storyLazyListState,
//            )
//        }
//
//
//        composable(route = Search.route) {
//
//            HomeFeed(
//                postList = allUserPost,
//                navController = navController,
//                postLazyListState = postLazyListState,
//                storyLazyListState = storyLazyListState,
//            )
//        }
//
//        composable(route = Reels.route) {
//
//            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
//
//        }
//
//        composable(route = Profile.route) {
//
//
//        }
//
//    }
//}

@Composable
fun BottomNavigation(
    instagramScreens: List<InstagramDestination>,
    selectedDestination: MutableState<String>
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        instagramScreens.forEach { instagramDestination: InstagramDestination ->
            NavigationBarItem(
                selected = instagramDestination.route == selectedDestination.value,
                onClick = { selectedDestination.value = instagramDestination.route },
                icon = {
                    Icon(imageVector = instagramDestination.selectedIcon, contentDescription = null)
                }
            )
        }
    }
}


@Composable
fun EmptyComingSoon(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.empty_screen_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = R.string.empty_screen_title),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
    }
}