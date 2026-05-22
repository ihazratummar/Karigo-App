package com.karigo.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.karigo.app.feature.homeScreen.HomeScreen
import com.karigo.app.feature.onboarding.OnboardingScreen
import com.karigo.presentation.onboarding.OnboardingViewModel
import com.karigo.ui.theme.dimens
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomBar(navHostController = navController)
        }
    ) { paddingValues ->

        val onBoardingViewModel = koinViewModel<OnboardingViewModel>()
        val isCompleted by onBoardingViewModel.isCompleted.collectAsStateWithLifecycle()

        val startDestination = if (isCompleted) RootNav.ContentRoute else RootNav.Onboarding

        NavHost(
            navController = navController,
            startDestination =startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<RootNav.Onboarding> {
                val onboardingState by onBoardingViewModel.state.collectAsStateWithLifecycle()
                val event = onBoardingViewModel::onIntent
                val effect = onBoardingViewModel.effect

                OnboardingScreen(
                    state = onboardingState,
                    event = event,
                    effect = effect,
                    navigateToDashboard = {
                        navController.navigate(MainRoute.HomeRoute)
                    }
                )
            }

            contentNavigation()
        }
    }


}

@Composable
fun BottomBar(
    navHostController: NavHostController
) {
    val bottomNavItems = remember {
        listOf(
            BottomNavRoute.Home,
            BottomNavRoute.Jobs,
            BottomNavRoute.Client,
            BottomNavRoute.Settings
        )
    }

    val backStackState by navHostController.currentBackStackEntryAsState()
    val currentDestination = backStackState?.destination
    val isBottomBarVisible =
        bottomNavItems.any { it.route::class.qualifiedName == currentDestination?.route }

    if (isBottomBarVisible) {
        NavigationBar(containerColor = Color.Transparent ) {
            bottomNavItems.forEach { screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navHostController.navigate(screen.route) {
                            popUpTo(navHostController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    },
                    icon = {
                        Icon(
                            painter = if (isSelected) painterResource(screen.fillIcon) else painterResource(
                                screen.unSelectedIcon
                            ),
                            contentDescription = screen.name,
                            modifier = Modifier.size(dimens.iconMd)
                        )
                    },
                    label = {
                        Text(text = screen.name)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.surfaceTint,
                        selectedTextColor = MaterialTheme.colorScheme.surfaceTint,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = MaterialTheme.colorScheme.outline,
                        unselectedTextColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}