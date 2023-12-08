package com.shakiv.husain.contentvibe.presentation.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.shakiv.husain.contentvibe.presentation.story.StoryViewScreen
import com.shakiv.husain.contentvibe.presentation.write_post.WritePostScreen
import com.shakiv.husain.contentvibe.utils.AppRoutes.LOGIN_SCREEN
import com.shakiv.husain.contentvibe.utils.AppRoutes.SIGN_UP_SCREEN
import com.shakiv.husain.contentvibe.utils.AppRoutes.STORY_VIEW_SCREEN
import com.shakiv.husain.contentvibe.utils.extentions.logd

@Composable
fun ContentVibeNavHost(
    appState: ContentVibeAppState,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
    hideBottomNavigation: (Boolean) -> Unit
) {

//    val startDestination = if (!authViewModel.hasUser) LOGIN_SCREEN else HomeDestination.route
    val startDestination = LOGIN_SCREEN
    val sharedViewModelState by sharedViewModel.navigationArgsState.collectAsStateWithLifecycle()


    NavHost(
        navController = appState.navController,
        modifier = modifier,
        startDestination = startDestination
    ) {


        composable(route = HomeDestination.route) { navBackStackEntry ->
            HomeFeed(
                onItemClick = {
                    hideBottomNavigation(false)
//                    sharedViewModel.updateUserId(it.usedId.orEmpty())
//                    appState.navController.navigateToSingleTopTo(ProfileDestination.route)
                },
                onStoryCreate = { storyItem ->
                    sharedViewModel.setStoryState(true, storyItem)
                    appState.navController.navigateToSingleTopTo(AddPostDestination.route)
                },

                onStoryView = { storyItem ->

                    sharedViewModel.setStoryState(storyItem = storyItem)
                    appState.navController.navigateToSingleTopTo(STORY_VIEW_SCREEN)

                },
                onProfileClick = {
                    sharedViewModel.updateUserId(it.usedId.orEmpty())
                    appState.navController.navigateToSingleTopTo(ProfileDestination.route)
                },

                )
            logd("CurrentTag HomeDestination : ${sharedViewModelState.userId}")
        }

        composable(route = SearchDestination.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }

        composable(route = ReelsDestination.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }

        composable(route = ProfileDestination.route) { navBackStackEntry ->
            ProfileScreen(
                sharedViewModelState,
                onBackPressed = {
                    sharedViewModel.updateUserId("")
                    appState.navController.popBackStack()
                },
                navigateToLoginScreen = {
                    appState.navController.navigateAndClearBackStack(LOGIN_SCREEN)
                }
            )
        }


        composable(route = EmptyComingSoon.route) {
            EmptyComingSoon(modifier = Modifier.fillMaxWidth())
        }


        composable(route = AddPostDestination.route) {
            WritePostScreen(
                sharedViewModelState,
                popBackStack = {
                    sharedViewModel.setStoryState(false, storyItem = null)
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


        composable(STORY_VIEW_SCREEN) {
            StoryViewScreen(navigationArgsState = sharedViewModelState)
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



fun NavHostController.navigateAndClearBackStack(screen : String = LOGIN_SCREEN) =
    this.navigate(screen) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }

fun NavHostController.navigateAndClean(route: String) {
    navigate(route = route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
    graph.setStartDestination(route)
}
fun NavHostController.navigateToReelsScreen(type: String) {
    this.navigateToSingleTopTo("${ReelsDestination.route}/$type")
}

