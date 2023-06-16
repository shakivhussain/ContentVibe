package com.shakiv.husain.instagramui.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shakiv.husain.instagramui.ui.app.HomeDestination
import com.shakiv.husain.instagramui.ui.app.InstagramNavHost
import com.shakiv.husain.instagramui.ui.app.bottomTabRowScreens
import com.shakiv.husain.instagramui.ui.app.navigateToSingleTopTo
import com.shakiv.husain.instagramui.ui.components.BottomNavigationTabRow

@Composable
fun InstagramApp() {
    InstagramAppContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstagramAppContent() {

    val navController: NavHostController = rememberNavController()
    val screens = bottomTabRowScreens


    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = screens.find { it.route == currentDestination?.route } ?: HomeDestination

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavigationTabRow(screens, currentScreen) {newScreen->
                navController.navigateToSingleTopTo(newScreen.route)
            }
        }
    ) { innerPadding ->

        InstagramNavHost(
            navController,
            modifier = Modifier.padding(innerPadding)
        )

    }

}




