package com.shakiv.husain.contentvibe.presentation.app

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.shakiv.husain.contentvibe.utils.IconsContentVibe

interface BaseDestination {
    val route: String
    val icon: Int
}

object ContentVibeRoutes {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val REELS = "Reels"
    const val PROFILE = "Profile"
    const val COMING_SOON = "ComingSoon"
    const val ADD_POST = "AddPost"
    const val CAMERA_SCREEN="Camera"
}

object HomeDestination : BaseDestination {
    override val route: String
        get() = ContentVibeRoutes.HOME
    override val icon: Int
        get() = IconsContentVibe.HOME

}


object SearchDestination : BaseDestination {
    override val route: String
        get() = ContentVibeRoutes.SEARCH
    override val icon: Int
        get() = IconsContentVibe.SEARCH

}

object AddPostDestination : BaseDestination {
    override val route: String
        get() = ContentVibeRoutes.ADD_POST
    override val icon: Int
        get() = IconsContentVibe.ADD_POST
}


object ReelsDestination : BaseDestination {
    override val route: String
        get() = ContentVibeRoutes.REELS
    override val icon: Int
        get() = IconsContentVibe.REELS


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
        get() = ContentVibeRoutes.PROFILE
    override val icon: Int
        get() = IconsContentVibe.PROFILE
}


object EmptyComingSoon : BaseDestination {
    override val route: String
        get() = ContentVibeRoutes.COMING_SOON
    override val icon: Int
        get() = TODO("Not yet implemented")

}


object CameraDestination:BaseDestination{
    override val route: String
        get() = ContentVibeRoutes.CAMERA_SCREEN
    override val icon: Int
        get() = TODO("Not yet implemented")

}

val bottomTabRowScreens: List<BaseDestination> =
    listOf(
        HomeDestination, SearchDestination, AddPostDestination, ReelsDestination, ProfileDestination
    )