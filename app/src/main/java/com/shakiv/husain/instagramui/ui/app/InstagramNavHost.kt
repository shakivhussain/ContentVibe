package com.shakiv.husain.instagramui.ui.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shakiv.husain.instagramui.data.AppContainer
import com.shakiv.husain.instagramui.data.post.HomeViewModel
import com.shakiv.husain.instagramui.ui.components.EmptyComingSoon
import com.shakiv.husain.instagramui.ui.home.HomeFeed
import com.shakiv.husain.instagramui.ui.profile.ProfileScreen

@Composable
fun InstagramNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appContainer: AppContainer,

    ) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = HomeDestination.route
    ) {

        composable(route = HomeDestination.route) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    appContainer.postRepository
                )
            )

            HomeFeed(
                onItemClick = {
                    navController.navigateToSingleTopTo(ProfileDestination.route)
                },
                homeViewModel,
                )
        }

        composable(route = SearchDestination.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }

        composable(route = ReelsDestination.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }

        composable(route = ProfileDestination.route) {
            ProfileScreen()
        }

        composable(route = EmptyComingSoon.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }


        composable(route = AddPostDestination.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }

    }
}


fun NavHostController.navigateToSingleTopTo(route: String) =
    this.navigate(route = route) {
        popUpTo(this@navigateToSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }