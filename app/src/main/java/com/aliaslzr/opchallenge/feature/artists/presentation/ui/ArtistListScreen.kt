package com.aliaslzr.opchallenge.feature.artists.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.aliaslzr.opchallenge.feature.artists.presentation.viewmodel.ArtistListViewModel
import com.aliaslzr.opchallenge.ui.component.OPGridItem
import com.aliaslzr.opchallenge.ui.component.OPLoadingProgressBar
import com.aliaslzr.opchallenge.ui.navigation.OPRoutes
import com.aliaslzr.opchallenge.ui.navigation.onNavigateToScreen

@Composable
fun ArtistListScreen(
    navHostController: NavHostController,
    viewModel: ArtistListViewModel = hiltViewModel(),
) {
    val artistUiState by viewModel.artistUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.artistUiState
    }
    val lazyGridState = rememberLazyGridState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
        ) {
            Text(
                text = "Artist",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        ArtistGrid(
            lazyGridState,
            artistUiState,
            navHostController,
        )
    }
}

@Composable
fun ArtistGrid(
    lazyGridState: LazyGridState,
    artistUiState: ArtistListUiState,
    navHostController: NavHostController,
) {
    when (artistUiState) {
        ArtistListUiState.Error -> {
            Text(text = "Error")
        }
        ArtistListUiState.Loading -> {
            OPLoadingProgressBar()
        }
        is ArtistListUiState.Success -> {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
            ) {
                items(
                    items = artistUiState.artistList,
                    key = { item ->
                        item.id
                    }
                ) { artist ->
                    OPGridItem(
                        name = artist.name,
                        imageUrl = artist.images.first().url,
                        rating = artist.popularity,
                        onActionClicked = {
                            onNavigateToScreen(
                                navHostController = navHostController,
                                route = OPRoutes
                                    .Detail
                                    .artistIdRoute(artistId = artist.id)
                            )
                        }
                    )
                }
            }
        }
    }
}