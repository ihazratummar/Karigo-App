package com.karigo.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.karigo.app.feature.homeScreen.HomeScreen


/**
 * @author hazratummar
 * Created on 21/05/26
 */
 


fun NavGraphBuilder.contentNavigation(){


    navigation<RootNav.ContentRoute>(startDestination = MainRoute.HomeRoute){
        composable<MainRoute.HomeRoute> {
            HomeScreen()
        }
    }

}