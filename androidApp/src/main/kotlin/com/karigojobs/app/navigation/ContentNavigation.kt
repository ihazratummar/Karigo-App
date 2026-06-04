package com.karigojobs.app.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.karigo.app.feature.materials.list.MaterialsListScreen
import com.karigojobs.app.feature.homeScreen.HomeScreen
import com.karigojobs.app.feature.job.create.JobCreateScreen
import com.karigojobs.app.feature.job.details.JobDetailsScreen
import com.karigojobs.app.feature.job.joblist.JobListScreen
import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.job.create.AddJobViewModel
import com.karigojobs.presentation.job.details.JobDetailsViewModel
import com.karigojobs.presentation.job.jobList.JobListViewModel
import com.karigojobs.presentation.materials.list.MaterialListViewModel
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

            HomeScreen(
                onNotificationClick = {
                    navHostController.navigate(MainRoute.NotificationRoute)
                },
                onFabClick = {
                    navHostController.navigate(MainRoute.AddJobRoute)
                },
                onSeeAllJobClick = {
                    navHostController.navigate(MainRoute.JobsRoute) {
                        navHostController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                homeState = state,
                homeEffect = viewModel.effect,
                onJobClick = { jobId ->
                    navHostController.navigate(MainRoute.JobDetailsRoute(jobId = jobId))
                }
            )
        }

        composable<MainRoute.AddJobRoute> {
            val viewModel = koinViewModel<AddJobViewModel>()
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
                event = viewModel::onEven
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
                event = viewModel::onEvent
            )
        }

        composable<MainRoute.ClientRoute> {
            // TODO: Implement Client Screen
        }

        composable<MainRoute.Materials> {

            val viewModel = koinViewModel<MaterialListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            MaterialsListScreen(
                state = state,
                event = viewModel::onEvent
            )
        }

        composable<MainRoute.SettingRoute> {
            // TODO: Implement Setting Screen
        }

        composable<MainRoute.NotificationRoute> {
            // TODO: Implement Notification Screen
        }

        composable<MainRoute.EarningRoute> {
            // TODO: Implement Earning Screen
        }
    }
}