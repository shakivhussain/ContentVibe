package com.shakiv.husain.instagramui.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shakiv.husain.instagramui.ui.app.BaseDestination

@Composable
fun BottomNavigationTabRow(
    instagramScreens: List<BaseDestination>,
    currentScreen: BaseDestination,
    onTabSelected : (BaseDestination) -> Unit
) {

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        instagramScreens.forEach { baseDestination: BaseDestination ->
            NavigationBarItem(
                selected = currentScreen.route == baseDestination.route,
                onClick = {
                    onTabSelected(baseDestination)
                },
                icon = {
                    Icon(imageVector = baseDestination.icon, contentDescription = null)
                }
            )
        }
    }
}