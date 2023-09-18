package com.shakiv.husain.contentvibe.presentation.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shakiv.husain.contentvibe.presentation.auth.AuthViewModel
import com.shakiv.husain.contentvibe.presentation.auth.LoginScreen
import com.shakiv.husain.contentvibe.presentation.auth.SignUpScreen
import com.shakiv.husain.contentvibe.presentation.camera.CameraScreen
import com.shakiv.husain.contentvibe.presentation.common.composable.EmptyComingSoon
import com.shakiv.husain.contentvibe.presentation.home.HomeFeed
import com.shakiv.husain.contentvibe.presentation.profile.ProfileScreen
import com.shakiv.husain.contentvibe.presentation.write_post.WritePostScreen
import com.shakiv.husain.contentvibe.utils.AppRoutes.LOGIN_SCREEN
import com.shakiv.husain.contentvibe.utils.AppRoutes.SIGN_UP_SCREEN
import com.shakiv.husain.contentvibe.utils.extentions.logd

@Composable
fun ContentVibeNavHost(
    appState: ContentVibeAppState,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {


    val startDestination = if (!authViewModel.hasUser) LOGIN_SCREEN else HomeDestination.route


    NavHost(
        navController = appState.navController,
        modifier = modifier,
        startDestination = startDestination
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
            SignUpScreen(
                navigateToLoginScreen = { appState.navController.popBackStack() },
                navigateToHomeScreen = {
                    appState.navigateAndClearTopStack(it, LOGIN_SCREEN)
                }
            )
        }

        composable(LOGIN_SCREEN) {
            LoginScreen(
                navigateToHomeScreen = {
                    appState.navigateAndClearTopStack(it, LOGIN_SCREEN)
                },
                redirectToSignupScreen = {
                    appState.navigate(it)
                },
            )
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

