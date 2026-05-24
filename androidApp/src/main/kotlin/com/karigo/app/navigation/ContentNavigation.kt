package com.karigo.app.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.karigo.app.feature.homeScreen.HomeScreen
import com.karigo.app.feature.job.create.JobCreateScreen
import com.karigo.presentation.dashboard.HomeViewModel
import com.karigo.presentation.job.create.AddJobViewModel
import org.koin.compose.viewmodel.koinViewModel


/**
 * @author hazratummar
 * Created on 21/05/26
 */
 


fun NavGraphBuilder.contentNavigation(
    navHostController: NavHostController
){

    navigation<RootNav.ContentRoute>(startDestination = MainRoute.HomeRoute){
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
                    navHostController.navigate(MainRoute.JobsRoute)
                },
                homeState = state
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

        composable<MainRoute.JobsRoute> {
            // TODO: Implement Jobs Screen
        }

        composable<MainRoute.ClientRoute> {
            // TODO: Implement Client Screen
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