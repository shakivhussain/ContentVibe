package com.shakiv.husain.instagramui

import androidx.compose.ui.graphics.vector.ImageVector
import com.shakiv.husain.instagramui.ui.IconsInstagram

interface BaseDestination {
    val route: String
    val icon: ImageVector
}

object InstagramRoutes {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val REELS = "Reels"
    const val PROFILE = "Profile"
    const val COMING_SOON = "ComingSoon"
}

object HomeDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.HOME
    override val icon: ImageVector
        get() = IconsInstagram.HOME

}


object SearchDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.SEARCH
    override val icon: ImageVector
        get() = IconsInstagram.SEARCH

}

object ReelsDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.REELS
    override val icon: ImageVector
        get() = IconsInstagram.REELS
}

object ProfileDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.PROFILE
    override val icon: ImageVector
        get() = IconsInstagram.PROFILE
}


object EmptyComingSoon : BaseDestination {
    override val route: String
        get() = InstagramRoutes.COMING_SOON
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
}

val bottomTabRowScreens: List<BaseDestination> =
    listOf(HomeDestination, SearchDestination, ReelsDestination, ProfileDestination)