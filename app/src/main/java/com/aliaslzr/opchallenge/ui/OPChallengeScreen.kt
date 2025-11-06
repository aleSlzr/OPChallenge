package com.aliaslzr.opchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aliaslzr.opchallenge.ui.component.OPTopAppBar
import com.aliaslzr.opchallenge.ui.icon.OPIcons
import com.aliaslzr.opchallenge.ui.navigation.OPNavHost

@Composable
fun OPChallengeScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            OPTopAppBar(
                title = "OP Challenge",
                navigationIcon = OPIcons.Gear,
                navigationIconContentDescriptor = "Gear icon",
                actionIcon = OPIcons.Info,
                actionIconContentDescriptor = "Info icon",
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OPNavHost(navHostController = navController)
        }
    }
}