package com.shakiv.husain.contentvibe.presentation.app

import android.content.res.Resources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shakiv.husain.contentvibe.presentation.common.composable.BottomNavigationTabRow
import com.shakiv.husain.contentvibe.utils.AppRoutes.LOGIN_SCREEN
import com.shakiv.husain.contentvibe.utils.AppRoutes.SIGN_UP_SCREEN
import com.shakiv.husain.contentvibe.utils.AppRoutes.STORY_VIEW_SCREEN
import com.shakiv.husain.contentvibe.utils.snackbar.SnackBarManager
import com.shakiv.husain.contentvibe.utils.snackbar.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun ContentVibeApp() {
    ContentVibeAppContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentVibeAppContent() {

    val appState = rememberAppState()

    val screens = bottomTabRowScreens
    val currentBackStack by appState.navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = screens.find { it.route == currentDestination?.route } ?: HomeDestination
    var bottomBarState by remember { mutableStateOf(true) }
    bottomBarState = needToShowBottomNavigation(currentDestination?.route)

    Surface {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    appState.snackbarHostState
                )
            },
            modifier = Modifier,
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    BottomNavigationTabRow(screens, currentScreen) { newScreen ->
                        appState.navController.navigateToSingleTopTo(newScreen.route)
                    }
                }
            }
        ) { innerPadding ->

            ContentVibeNavHost(
                appState,
                modifier = Modifier.padding(innerPadding),
                hideBottomNavigation = {
//                    bottomBarState = it
                }
            )
        }
    }


}

fun needToShowBottomNavigation(route: String?): Boolean {

    if (route == null)
        return true

    return when (route) {
        AddPostDestination.route,
        CameraDestination.route,
        LOGIN_SCREEN,
        SIGN_UP_SCREEN,
        STORY_VIEW_SCREEN
        -> {
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackBarManager = SnackBarManager,
    resources: Resources = resource(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    ContentVibeAppState(
        snackbarHostState, navController, snackbarManager, resources, coroutineScope
    )
}

@Stable
class ContentVibeAppState(
    val snackbarHostState: SnackbarHostState,
    val navController: NavHostController,
    val snackbarManager: SnackBarManager,
    val resources: Resources,
    coroutineScope: CoroutineScope,
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackBarMessages.filterNotNull().collect { snackbarMessages ->
                val text = snackbarMessages.toMessage(resources)
                snackbarHostState.showSnackbar(text)
            }
        }
    }

    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }


    fun navigateAndClearTopStack(navigateTo : String, clear:String) {
        navController.navigate(navigateTo) {
            popUpTo(clear) {
                inclusive = true
            }
        }
    }

}


@Composable
@ReadOnlyComposable
fun resource(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}



@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}


