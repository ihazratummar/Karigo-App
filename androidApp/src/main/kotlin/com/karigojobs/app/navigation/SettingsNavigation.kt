package com.karigojobs.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.karigojobs.feature.settings.SettingsNavigation
import com.karigojobs.feature.settings.SettingsScreen
import com.karigojobs.feature.settings.backup.DataBackupScreen
import com.karigojobs.presentation.backup.DataBackupViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import com.karigojobs.feature.settings.LegalScreen
import com.karigojobs.feature.settings.AboutScreen
import com.karigojobs.feature.settings.paywall.PaywallScreen
import com.karigojobs.presentation.settings.SettingsViewModel
import com.karigojobs.presentation.monetization.MonetizationViewModel
import org.koin.compose.viewmodel.koinViewModel


/**
 * @author hazratummar
 * Created on 27/06/26
 */

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigojobs.ui.common.KarigoTopAppBar

fun NavGraphBuilder.settingsNav(
    navHostController : NavHostController
) {
    navigation<SettingsRootRoute.SettingsGraphRoute>(startDestination = SettingsRootRoute.SettingsRoute) {
        composable<SettingsRootRoute.SettingsRoute> {
            val viewModel = koinViewModel<SettingsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val settingsNavigation = SettingsNavigation(
                navigateToMaterial = { navHostController.navigate(MainRoute.Materials) },
                navigateToEstimate = { navHostController.navigate(MainRoute.EstimateListRoute) },
                navigateToEarnings = { navHostController.navigate(MainRoute.EarningRoute) },
                navigateToProOverView = { navHostController.navigate(SettingsRootRoute.ProOverviewRoute) },
                navigateToNotification = { navHostController.navigate(SettingsRootRoute.NotificationRoute) },
                navigateToDataBackUp = { navHostController.navigate(SettingsRootRoute.DataBackUpRoute) },
                navigateToHelpAndSupport = { navHostController.navigate(SettingsRootRoute.HelpAndSupportRoute) },
                navigateToAbout = { navHostController.navigate(SettingsRootRoute.AboutRoute) },
                navigateToLegalPage = { pageTitle -> 
                    navHostController.navigate(SettingsRootRoute.LegalRoute(pageTitle = pageTitle)) 
                }
            )

            SettingsScreen(
                state = state,
                onCompleteBannerClick = {
                    navHostController.navigate(MainRoute.SetupWorkerRoute)
                },
                settingsNavigation = settingsNavigation,
                event = viewModel::onEvent
            )
        }

        composable<SettingsRootRoute.ProOverviewRoute> {
            val viewModel = koinViewModel<MonetizationViewModel>()
            PaywallScreen(
                viewModel = viewModel,
                onBackClick = { navHostController.popBackStack() }
            )
        }

        composable<SettingsRootRoute.NotificationRoute> {
            PlaceholderSettingsScreen(title = "Notifications", onBack = { navHostController.popBackStack() })
        }

        composable<SettingsRootRoute.DataBackUpRoute> {
            val viewModel = koinViewModel<DataBackupViewModel>()
            DataBackupScreen(
                viewModel = viewModel,
                onBack = { navHostController.popBackStack() }
            )
        }

        composable<SettingsRootRoute.HelpAndSupportRoute> {
            PlaceholderSettingsScreen(title = "Help & Support", onBack = { navHostController.popBackStack() })
        }

        composable<SettingsRootRoute.AboutRoute> {
            AboutScreen(
                onBack = { navHostController.popBackStack() }
            )
        }

        composable<SettingsRootRoute.LegalRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<SettingsRootRoute.LegalRoute>()
            LegalScreen(
                pageKey = route.pageTitle,
                onBack = { navHostController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PlaceholderSettingsScreen(
    title: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = title,
                isNavBack = true,
                onNavigationClick = onBack,
                isDivider = false
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$title Screen Placeholder")
        }
    }
}