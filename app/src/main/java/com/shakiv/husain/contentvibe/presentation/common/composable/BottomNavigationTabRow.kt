package com.shakiv.husain.contentvibe.presentation.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.shakiv.husain.contentvibe.presentation.app.BaseDestination

@Composable
fun BottomNavigationTabRow(
    contentvibeScreens: List<BaseDestination>,
    currentScreen: BaseDestination,
    onTabSelected : (BaseDestination) -> Unit
) {

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        contentvibeScreens.forEach { baseDestination: BaseDestination ->
            NavigationBarItem(
                selected = currentScreen.route == baseDestination.route,
                onClick = {
                    onTabSelected(baseDestination)
                },
                icon = {
                    Icon(painter = painterResource(id = baseDestination.icon), contentDescription = null)
                }
            )
        }
    }
}