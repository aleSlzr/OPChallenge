package com.aliaslzr.opchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aliaslzr.opchallenge.ui.component.OPTopAppBar
import com.aliaslzr.opchallenge.ui.icon.OPIcons
import com.aliaslzr.opchallenge.ui.navigation.OPNavHost
import com.aliaslzr.opchallenge.ui.navigation.OPRoutes

@Composable
fun OPChallengeScreen() {
    val navController = rememberNavController()
    val navStateStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStateStackEntry?.destination

    Scaffold(
        topBar = {
            if (currentRoute?.route != OPRoutes.Detail.route) {
                OPTopAppBar(
                    title = "OP Challenge",
                    navigationIcon = OPIcons.Gear,
                    navigationIconContentDescriptor = "Gear icon",
                    actionIcon = OPIcons.Info,
                    actionIconContentDescriptor = "Info icon",
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .then(
                    if (currentRoute?.route != OPRoutes.Detail.route) {
                        Modifier.padding(it)
                    } else {
                        Modifier.padding(0.dp)
                    },
                )
        ) {
            OPNavHost(navHostController = navController)
        }
    }
}