package com.shakiv.husain.instagramui.presentation.app

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.shakiv.husain.instagramui.utils.IconsInstagram

interface BaseDestination {
    val route: String
    val icon: Int
}

object InstagramRoutes {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val REELS = "Reels"
    const val PROFILE = "Profile"
    const val COMING_SOON = "ComingSoon"
    const val ADD_POST = "AddPost"
}

object HomeDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.HOME
    override val icon: Int
        get() = IconsInstagram.HOME

}


object SearchDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.SEARCH
    override val icon: Int
        get() = IconsInstagram.SEARCH

}

object AddPostDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.ADD_POST
    override val icon: Int
        get() = IconsInstagram.ADD_POST
}


object ReelsDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.REELS
    override val icon: Int
        get() = IconsInstagram.REELS


    val userType = "user_type"
    val routeWithArgs = "$route/{$userType}"

    val arguments = listOf(
        navArgument(userType) {
            type = NavType.StringType
        }
    )

}

object ProfileDestination : BaseDestination {
    override val route: String
        get() = InstagramRoutes.PROFILE
    override val icon: Int
        get() = IconsInstagram.PROFILE
}


object EmptyComingSoon : BaseDestination {
    override val route: String
        get() = InstagramRoutes.COMING_SOON
    override val icon: Int
        get() = TODO("Not yet implemented")

}


val bottomTabRowScreens: List<BaseDestination> =
    listOf(
        HomeDestination, SearchDestination, AddPostDestination, ReelsDestination, ProfileDestination
    )