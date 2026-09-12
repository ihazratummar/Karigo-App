package com.karigojobs.app.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.karigo.app.feature.materials.list.MaterialsListScreen
import com.karigo.app.feature.siteEstimate.add.AddEstimateScreen
import com.karigo.app.feature.siteEstimate.details.EstimateDetailsScreen
import com.karigo.app.feature.siteEstimate.list.EstimateListScreen
import com.karigojobs.app.feature.homeScreen.HomeScreen
import com.karigojobs.app.feature.job.create.JobCreateScreen
import com.karigojobs.app.feature.job.details.JobDetailsScreen
import com.karigojobs.app.feature.job.joblist.JobListScreen
import com.karigojobs.app.feature.onboarding.WorkerOnboarding
import com.karigojobs.feature.client.details.ClientDetailsScreen
import com.karigojobs.feature.client.list.ClientListScreen
import com.karigojobs.feature.settings.SettingsNavigation
import com.karigojobs.feature.settings.SettingsScreen
import com.karigojobs.presentation.client.details.ClientDetailsViewModel
import com.karigojobs.presentation.client.list.ClientListViewModel
import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.earnings.EarningsViewModel
import com.karigojobs.presentation.estimate.add.AddEstimateViewModel
import com.karigojobs.presentation.estimate.details.EstimateDetailsViewModel
import com.karigojobs.presentation.estimate.list.EstimateListViewModel
import com.karigojobs.presentation.job.create.AddJobViewModel
import com.karigojobs.presentation.job.details.JobDetailsViewModel
import com.karigojobs.presentation.job.jobList.JobListViewModel
import com.karigojobs.presentation.materials.list.MaterialCategoryViewModel
import com.karigojobs.presentation.materials.list.MaterialListViewModel
import com.karigojobs.presentation.onboarding.WorkerProfileViewModel
import com.karigojobs.presentation.settings.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


/**
 * @author hazratummar
 * Created on 21/05/26
 */


fun NavGraphBuilder.contentNavigation(
    navHostController: NavHostController
) {

    navigation<RootNav.ContentRoute>(startDestination = MainRoute.HomeRoute) {
        composable<MainRoute.HomeRoute> {

            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val monetizationViewModel = koinViewModel<com.karigojobs.presentation.monetization.MonetizationViewModel>()
            val monetizationState by monetizationViewModel.state.collectAsStateWithLifecycle()

            HomeScreen(
                onNotificationClick = {
                    navHostController.navigate(MainRoute.NotificationRoute)
                },
                onFabClick = {
                    navHostController.navigate(MainRoute.AddJobRoute())
                },
                onSeeAllJobClick = {
                    navHostController.navigate(MainRoute.JobsRoute) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                homeState = state,
                homeEffect = viewModel.effect,
                onJobClick = { jobId ->
                    navHostController.navigate(MainRoute.JobDetailsRoute(jobId = jobId))
                },
                navigateToAddEstimate = {
                    navHostController.navigate(MainRoute.AddEstimateRoute())
                },
                onSeeAllEstimateClick = {
                    navHostController.navigate(MainRoute.EstimateListRoute)
                },
                onCompleteBannerClick = {
                    navHostController.navigate(MainRoute.SetupWorkerRoute)
                },
                proStatus = monetizationState.proStatus,
                monthlyJobLimit = monetizationState.monthlyJobLimit,
                onGoProClick = {
                    navHostController.navigate(SettingsRootRoute.ProOverviewRoute)
                }
            )
        }

        composable<MainRoute.AddJobRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.AddJobRoute>()
            val viewModel = koinViewModel<AddJobViewModel>(
                parameters = { parametersOf(route.jobId, route.clientId) }
            )
            val state by viewModel.addJobState.collectAsStateWithLifecycle()
            JobCreateScreen(
                addJobState = state,
                onIntent = viewModel::event,
                onBackClick = {
                    navHostController.popBackStack()
                },
                addJobEffect = viewModel.addJobEffect
            )
        }

        composable<MainRoute.JobDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.JobDetailsRoute>()

            val viewModel = koinViewModel<JobDetailsViewModel>(
                parameters = { parametersOf(route.jobId) }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            JobDetailsScreen(
                jobDetailsState = state,
                jobDetailsEffect = viewModel.effect,
                onBackClick = {
                    navHostController.popBackStack()
                },
                onEditClick = { jobId ->
                    navHostController.navigate(MainRoute.AddJobRoute(jobId = jobId))
                },
                onPaywallClick = {
                    navHostController.navigate(SettingsRootRoute.ProOverviewRoute)
                },
                event = viewModel::onEvent
            )

        }

        composable<MainRoute.JobsRoute> {
            val viewModel = koinViewModel<JobListViewModel>()
            val jobListState by viewModel.state.collectAsStateWithLifecycle()
            JobListScreen(
                jobListState = jobListState,
                onJobClick = { jobId ->
                    navHostController.navigate(MainRoute.JobDetailsRoute(jobId = jobId))
                },
                onAddJobClick = {
                    navHostController.navigate(MainRoute.AddJobRoute(jobId = null))
                },
                event = viewModel::onEvent
            )
        }

        composable<MainRoute.ClientListRoute> {
            val viewModel = koinViewModel<ClientListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ClientListScreen(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                onClientClick = { clientId ->
                    navHostController.navigate(MainRoute.ClientDetailsRoute(clientId = clientId))
                }
            )
        }

        composable<MainRoute.ClientDetailsRoute> { backStackEntry ->
            val clientId = backStackEntry.toRoute<MainRoute.ClientDetailsRoute>().clientId

            val viewModel = koinViewModel<ClientDetailsViewModel>(
                parameters = { parametersOf(clientId) }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            ClientDetailsScreen(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                onJobClick = {
                    navHostController.navigate(MainRoute.JobDetailsRoute(jobId = it))
                },
                onNewJobClick = { clientId ->
                    navHostController.navigate(MainRoute.AddJobRoute(clientId = clientId))
                },
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }


        composable<MainRoute.AddEstimateRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.AddEstimateRoute>()
            val viewModel = koinViewModel<AddEstimateViewModel>(
                parameters = { parametersOf(route.estimateId) }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            val estimateId = viewModel.draftEstimateId
            AddEstimateScreen(
                onBackClick = {
                    navHostController.navigate(MainRoute.EstimateDetailsRoute(estimateId = estimateId)) {
                        popUpTo(MainRoute.AddEstimateRoute::class) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect
            )
        }
        composable<MainRoute.EstimateListRoute> {
            val viewModel = koinViewModel<EstimateListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            EstimateListScreen(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                onBackClick = {
                    navHostController.popBackStack()
                },
                onAddClick = {
                    navHostController.navigate(MainRoute.AddEstimateRoute())
                },
                onEstimateClick = { estimateId ->
                    navHostController.navigate(MainRoute.EstimateDetailsRoute(estimateId = estimateId))
                }
            )

        }

        composable<MainRoute.EstimateDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MainRoute.EstimateDetailsRoute>()

            val viewModel = koinViewModel<EstimateDetailsViewModel>(
                parameters = { parametersOf(route.estimateId) }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            EstimateDetailsScreen(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                onBackClick = {
                    navHostController.popBackStack()
                },
                onEditClick = { estimateId ->
                    navHostController.navigate(MainRoute.AddEstimateRoute(estimateId = estimateId))
                },
                onPaywallClick = {
                    navHostController.navigate(SettingsRootRoute.ProOverviewRoute)
                }
            )
        }

        composable<MainRoute.Materials> {

            val viewModel = koinViewModel<MaterialListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val categoryViewModel = koinViewModel<MaterialCategoryViewModel>()
            val categoryState by categoryViewModel.state.collectAsStateWithLifecycle()

            MaterialsListScreen(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                categoryState = categoryState,
                categoryEvent = categoryViewModel::onEvent,
                categoryEffect = categoryViewModel.effect
            )
        }

        settingsNav(navHostController = navHostController)

        composable<MainRoute.SetupWorkerRoute> {
            val viewModel = koinViewModel<WorkerProfileViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            WorkerOnboarding(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<MainRoute.NotificationRoute> {
            // TODO: Implement Notification Screen
        }

        composable<MainRoute.EarningRoute> {
            val viewModel = koinViewModel<EarningsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            com.karigojobs.feature.settings.earnings.EarningsScreen(
                state = state,
                event = viewModel::onEvent,
                effect = viewModel.effect,
                onNavigateBack = { navHostController.popBackStack() },
                onNavigateToPaywall = { navHostController.navigate(SettingsRootRoute.ProOverviewRoute) }
            )
        }
    }
}