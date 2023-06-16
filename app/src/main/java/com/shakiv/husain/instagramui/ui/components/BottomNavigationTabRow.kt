package com.shakiv.husain.instagramui.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shakiv.husain.instagramui.InstagramDestination

@Composable
fun BottomNavigationTabRow(
    instagramScreens: List<InstagramDestination>,
    currentScreen: InstagramDestination,
    onTabSelected : (InstagramDestination) -> Unit
) {

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        instagramScreens.forEach { instagramDestination: InstagramDestination ->
            NavigationBarItem(
                selected = currentScreen.route == instagramDestination.route,
                onClick = {
                    onTabSelected(instagramDestination)
                },
                icon = {
                    Icon(imageVector = instagramDestination.selectedIcon, contentDescription = null)
                }
            )
        }
    }
}