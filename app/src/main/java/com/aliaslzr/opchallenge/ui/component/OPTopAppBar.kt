package com.aliaslzr.opchallenge.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.aliaslzr.opchallenge.ui.icon.OPIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OPTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescriptor: String,
    actionIcon: ImageVector,
    actionIconContentDescriptor: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescriptor,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescriptor,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        modifier = modifier,
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Top App Bar", showSystemUi = true)
@Composable
private fun OPTopAppBarPreview() {
    OPTopAppBar(
        title = "Title example",
        navigationIcon = OPIcons.Search,
        navigationIconContentDescriptor = "Navigation Icon",
        actionIcon = OPIcons.Gear,
        actionIconContentDescriptor = "Action Icon",
    )
}