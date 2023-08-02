package com.shakiv.husain.instagramui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shakiv.husain.instagramui.data.AppContainer
import com.shakiv.husain.instagramui.ui.app.AddPostDestination
import com.shakiv.husain.instagramui.ui.app.HomeDestination
import com.shakiv.husain.instagramui.ui.app.InstagramNavHost
import com.shakiv.husain.instagramui.ui.app.bottomTabRowScreens
import com.shakiv.husain.instagramui.ui.app.navigateToSingleTopTo
import com.shakiv.husain.instagramui.ui.components.BottomNavigationTabRow

@Composable
fun InstagramApp(appContainer: AppContainer) {
    InstagramAppContent(appContainer)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstagramAppContent(appContainer: AppContainer) {

    val navController: NavHostController = rememberNavController()
    val screens = bottomTabRowScreens


    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = screens.find { it.route == currentDestination?.route } ?: HomeDestination
    var bottomBarState by remember { mutableStateOf(true) }

    bottomBarState = when (currentDestination?.route) {
        AddPostDestination.route -> {
            false
        }

        else -> {
            true
        }
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarState,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigationTabRow(screens, currentScreen) { newScreen ->
                    navController.navigateToSingleTopTo(newScreen.route)
                }
            }
        }
    ) { innerPadding ->

        InstagramNavHost(
            navController,
            modifier = Modifier.padding(innerPadding),
            appContainer,

            )

    }

}




