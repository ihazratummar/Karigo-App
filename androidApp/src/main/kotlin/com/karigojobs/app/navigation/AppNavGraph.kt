package com.karigojobs.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
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
import com.karigojobs.app.feature.onboarding.OnboardingScreen
import com.karigojobs.presentation.onboarding.OnboardingCompleteState
import com.karigojobs.presentation.onboarding.OnboardingViewModel
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            HorizontalDivider()
            BottomBar(navHostController = navController)
        }
    ) { paddingValues ->

        val competeState by onboardingViewModel.completedState.collectAsStateWithLifecycle()

        if (competeState == OnboardingCompleteState.Loading) return@Scaffold

        val startDestination = when (competeState) {
            OnboardingCompleteState.Completed -> RootNav.ContentRoute
            else -> RootNav.Onboarding
        }

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<RootNav.Onboarding> {
                val onboardingState by onboardingViewModel.state.collectAsStateWithLifecycle()
                val event = onboardingViewModel::onIntent
                val effect = onboardingViewModel.effect

                OnboardingScreen(
                    state = onboardingState,
                    event = event,
                    effect = effect,
                    navigateToDashboard = {
                        navController.navigate(MainRoute.HomeRoute)
                    }
                )
            }

            contentNavigation(navHostController = navController)
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
            BottomNavRoute.Materials,
            BottomNavRoute.Settings
        )
    }

    val backStackState by navHostController.currentBackStackEntryAsState()
    val currentDestination = backStackState?.destination
    val isBottomBarVisible =
        bottomNavItems.any { currentDestination?.hasRoute(it.route::class) == true }

    if (isBottomBarVisible) {
        NavigationBar(containerColor = Color.Transparent ) {
            bottomNavItems.forEach { screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navHostController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navHostController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = if (isSelected) painterResource(screen.fillIcon) else painterResource(
                                screen.unSelectedIcon
                            ),
                            contentDescription = screen.name,
                            modifier = Modifier.size(dimens.Icon.sm)
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