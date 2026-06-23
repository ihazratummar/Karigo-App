package com.karigojobs.app.navigation

import com.karigojobs.app.android.ui.R
import kotlinx.serialization.Serializable


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@Serializable
sealed class RootNav {

    @Serializable
    data object ContentRoute
    @Serializable
    data object Onboarding
}

@Serializable
sealed class MainRoute {
    @Serializable
    data object HomeRoute

    @Serializable
    data object JobsRoute

    @Serializable
    data class JobDetailsRoute(val jobId: String) : MainRoute()

    @Serializable
    data class AddJobRoute(val jobId: String? = null) : MainRoute()

    @Serializable
    data object ClientRoute

    @Serializable
    data object Materials

    @Serializable
    data object EarningRoute

    @Serializable
    data object EstimateListRoute

    @Serializable
    data class AddEstimateRoute(val estimateId: String? = null) : MainRoute()

    @Serializable
    data class EstimateDetailsRoute(val estimateId: String) : MainRoute()

    @Serializable
    data object SettingRoute

    @Serializable
    data object NotificationRoute
}

@Serializable
sealed class BottomNavRoute<T>(
    val name: String,
    val unSelectedIcon: Int = R.drawable.ic_launcher_foreground,
    val fillIcon: Int = R.drawable.ic_launcher_foreground,
    val route: T
) {
    @Serializable
    data object Home : BottomNavRoute<MainRoute.HomeRoute>(
        name = "Home",
        unSelectedIcon = R.drawable.home_line,
        fillIcon = R.drawable.home_fill,
        route = MainRoute.HomeRoute
    )

    @Serializable
    data object Jobs :
        BottomNavRoute<MainRoute.JobsRoute>(
            name = "Jobs",
            unSelectedIcon = R.drawable.job_line,
            fillIcon = R.drawable.job_fill,
            route = MainRoute.JobsRoute
        )

    @Serializable
    data object Client :
        BottomNavRoute<MainRoute.ClientRoute>(
            name = "Client",
            unSelectedIcon = R.drawable.user_line,
            fillIcon = R.drawable.user_fill,
            route = MainRoute.ClientRoute
        )

    @Serializable
    data object Materials :
        BottomNavRoute<MainRoute.Materials>(
            name = "Materials",
            unSelectedIcon = R.drawable.stack,
            fillIcon = R.drawable.stack_fill,
            route = MainRoute.Materials
        )

    @Serializable
    data object Settings :
        BottomNavRoute<MainRoute.SettingRoute>(
            name = "Settings",
            unSelectedIcon = R.drawable.settings_line,
            fillIcon = R.drawable.settings_fill,
            route = MainRoute.SettingRoute
        )
}
