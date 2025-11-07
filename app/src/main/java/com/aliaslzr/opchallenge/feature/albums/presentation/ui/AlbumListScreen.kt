package com.aliaslzr.opchallenge.feature.albums.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.aliaslzr.opchallenge.feature.albums.presentation.model.AlbumUi
import com.aliaslzr.opchallenge.feature.albums.presentation.viewmodel.AlbumListViewModel
import com.aliaslzr.opchallenge.ui.component.OPLoadingProgressBar
import com.aliaslzr.opchallenge.ui.component.OPTopAppBar
import com.aliaslzr.opchallenge.ui.icon.OPIcons

@Composable
fun AlbumListScreen(
    artistId: String,
    navHostController: NavHostController,
    viewModel: AlbumListViewModel = hiltViewModel(),
) {
    val albumListUiState by viewModel.albumListUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getAlbumDetails(artistId)
    }
    when (albumListUiState) {
        is AlbumListUiState.Error -> {
            Text("Oops there is something wrong: ${(albumListUiState as AlbumListUiState.Error).errorMessage}")
        }
        AlbumListUiState.Loading -> {
            OPLoadingProgressBar()
        }
        is AlbumListUiState.Success -> {
            val albumListDetails = (albumListUiState as AlbumListUiState.Success).albumList
            AlbumListDetailsScaffold(
                navHostController,
                albumListDetails,
            )
        }
    }
}

@Composable
fun AlbumListDetailsScaffold(
    navHostController: NavHostController,
    albumListDetails: List<AlbumUi>,
) {
    Scaffold(
        topBar = {
            OPTopAppBar(
                title = albumListDetails.first().artists.first(),
                navigationIcon = OPIcons.NavigateBack,
                navigationIconContentDescriptor = "Navigate back",
                onNavigationClick = {
                    navHostController.popBackStack()
                },
                actionIcon = OPIcons.Design,
                actionIconContentDescriptor = "Some description",
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight(),
        ) {
            items(
                items = albumListDetails,
                key = { item ->
                    item.albumId
                },
            ) { albumItem ->
                AlbumCard(albumItem)
            }
        }
    }
}

@Composable
fun AlbumCard(albumItem: AlbumUi) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = albumItem.albumImages.firstOrNull(),
                contentDescription = albumItem.albumName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 120.dp, height = 180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize()
            ) {
                Text(
                    text = albumItem.albumName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = albumItem.artists.firstOrNull().orEmpty(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = albumItem.releaseDate,
                    style = MaterialTheme.typography.labelSmall
                )
                AnimatedVisibility(visible = expanded) {
                    Column(
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        albumItem.tracks?.forEach { track ->
                            Text(
                                text = "${track.trackNumber}. ${track.name}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}