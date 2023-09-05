package com.shakiv.husain.instagramui.presentation.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shakiv.husain.instagramui.presentation.auth.AuthViewModel
import com.shakiv.husain.instagramui.presentation.auth.LoginScreen
import com.shakiv.husain.instagramui.presentation.auth.SignUpScreen
import com.shakiv.husain.instagramui.presentation.camera.CameraScreen
import com.shakiv.husain.instagramui.presentation.common.composable.EmptyComingSoon
import com.shakiv.husain.instagramui.presentation.home.HomeFeed
import com.shakiv.husain.instagramui.presentation.profile.ProfileScreen
import com.shakiv.husain.instagramui.presentation.write_post.WritePostScreen
import com.shakiv.husain.instagramui.utils.AppRoutes.LOGIN_SCREEN
import com.shakiv.husain.instagramui.utils.AppRoutes.SIGN_UP_SCREEN

@Composable
fun InstagramNavHost(
    appState: ContentVibeAppState,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = appState.navController,
        modifier = modifier,
        startDestination = LOGIN_SCREEN
    ) {

        composable(route = HomeDestination.route) {
            HomeFeed(
                onItemClick = {
                    appState.navigate(ProfileDestination.route)
                },
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
            WritePostScreen(
                popBackStack = {
                    appState.navController.popBackStack()
                },
                onCameraClick = {
                    appState.navigate(CameraDestination.route)
                }
            )
        }

        composable(route = CameraDestination.route) {
            CameraScreen() {
                appState.navController.popBackStack()
            }
        }

        composable(SIGN_UP_SCREEN) {
            SignUpScreen()
        }

        composable(LOGIN_SCREEN) {
            LoginScreen {
                appState.navigate(it)
            }
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


fun NavHostController.navigateToReelsScreen(type: String) {
    this.navigateToSingleTopTo("${ReelsDestination.route}/$type")
}