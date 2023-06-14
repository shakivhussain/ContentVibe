package com.shakiv.husain.instagramui

import androidx.compose.ui.graphics.vector.ImageVector
import com.shakiv.husain.instagramui.ui.IconsInstagram

interface InstagramDestination {
    val route: String
    val selectedIcon: ImageVector
    val unSelectedIcon: ImageVector
    val iconText: String
}

object InstagramRoutes {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val REELS = "Reels"
    const val PROFILE = "Profile"
}

object Home : InstagramDestination {
    override val route: String
        get() = InstagramRoutes.HOME
    override val selectedIcon: ImageVector
        get() = IconsInstagram.HOME
    override val unSelectedIcon: ImageVector
        get() = IconsInstagram.HOME
    override val iconText: String
        get() = InstagramRoutes.HOME
}


object Search : InstagramDestination {
    override val route: String
        get() = InstagramRoutes.SEARCH
    override val selectedIcon: ImageVector
        get() = IconsInstagram.SEARCH
    override val unSelectedIcon: ImageVector
        get() = IconsInstagram.SEARCH
    override val iconText: String
        get() = InstagramRoutes.SEARCH

}

object Reels : InstagramDestination {
    override val route: String
        get() = InstagramRoutes.REELS
    override val selectedIcon: ImageVector
        get() = IconsInstagram.REELS
    override val unSelectedIcon: ImageVector
        get() = IconsInstagram.REELS
    override val iconText: String
        get() = InstagramRoutes.REELS

}

object Profile : InstagramDestination {
    override val route: String
        get() = InstagramRoutes.PROFILE
    override val selectedIcon: ImageVector
        get() = IconsInstagram.PROFILE
    override val unSelectedIcon: ImageVector
        get() = IconsInstagram.PROFILE
    override val iconText: String
        get() = InstagramRoutes.PROFILE
}

val InstagramScreens: List<InstagramDestination> = listOf(Home, Search, Reels, Profile)