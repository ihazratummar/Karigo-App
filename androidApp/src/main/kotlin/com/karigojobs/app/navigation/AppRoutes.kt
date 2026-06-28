package com.karigojobs.app.navigation

import androidx.annotation.StringRes
import com.karigojobs.app.android.ui.R
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.nav_clients
import karigojobs.shared.resources.generated.resources.nav_home
import karigojobs.shared.resources.generated.resources.nav_jobs
import karigojobs.shared.resources.generated.resources.nav_materials
import karigojobs.shared.resources.generated.resources.nav_settings
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.StringResource


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
sealed class SettingsRootRoute {
    @Serializable
    data object SettingsGraphRoute : SettingsRootRoute()

    @Serializable
    data object SettingsRoute

    @Serializable
    data object ProOverviewRoute

    @Serializable
    data object NotificationRoute

    @Serializable
    data object DataBackUpRoute

    @Serializable
    data object HelpAndSupportRoute

    @Serializable
    data object AboutRoute

    @Serializable
    data class LegalRoute(val pageTitle: String) : SettingsRootRoute()
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
    data class AddJobRoute(val jobId: String? = null, val clientId : String ? = null) : MainRoute()

    @Serializable
    data object ClientListRoute
    @Serializable
    data class ClientDetailsRoute(val clientId: String) : MainRoute()

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

    @Serializable
    data object SetupWorkerRoute
}

@Serializable
sealed class BottomNavRoute<T>(
    @Transient val titleRes: StringResource? = null,
    val unSelectedIcon: Int = R.drawable.ic_launcher_foreground,
    val fillIcon: Int = R.drawable.ic_launcher_foreground,
    val route: T
) {
    @Serializable
    data object Home : BottomNavRoute<MainRoute.HomeRoute>(
        titleRes = Res.string.nav_home,
        unSelectedIcon = R.drawable.home_line,
        fillIcon = R.drawable.home_fill,
        route = MainRoute.HomeRoute
    )

    @Serializable
    data object Jobs :
        BottomNavRoute<MainRoute.JobsRoute>(
            titleRes = Res.string.nav_jobs,
            unSelectedIcon = R.drawable.job_line,
            fillIcon = R.drawable.job_fill,
            route = MainRoute.JobsRoute
        )

    @Serializable
    data object Client :
        BottomNavRoute<MainRoute.ClientListRoute>(
            titleRes = Res.string.nav_clients,
            unSelectedIcon = R.drawable.user_line,
            fillIcon = R.drawable.user_fill,
            route = MainRoute.ClientListRoute
        )

    @Serializable
    data object Materials :
        BottomNavRoute<MainRoute.Materials>(
            titleRes = Res.string.nav_materials,
            unSelectedIcon = R.drawable.stack,
            fillIcon = R.drawable.stack_fill,
            route = MainRoute.Materials
        )

    @Serializable
    data object Settings :
        BottomNavRoute<SettingsRootRoute.SettingsRoute>(
            titleRes = Res.string.nav_settings,
            unSelectedIcon = R.drawable.settings_line,
            fillIcon = R.drawable.settings_fill,
            route = SettingsRootRoute.SettingsRoute
        )
}
