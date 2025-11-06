package com.aliaslzr.opchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliaslzr.opchallenge.feature.artists.presentation.ui.ArtistListScreen

@Composable
fun OPNavHost(
    navHostController: NavHostController,
    startDestination: String = OPRoutes.ArtistList.route,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable(OPRoutes.ArtistList.route) {
            ArtistListScreen(navHostController)
        }
        composable(OPRoutes.Detail.route) {
            // ArtistListScreen(navHostController)
        }
    }
}

fun onNavigateToScreen(
    navHostController: NavHostController,
    route: String,
) {
    navHostController.navigate(route) {
        popUpTo(navHostController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}