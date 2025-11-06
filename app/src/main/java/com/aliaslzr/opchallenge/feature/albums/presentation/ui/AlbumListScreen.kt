package com.aliaslzr.opchallenge.feature.albums.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    viewModel: AlbumListViewModel = hiltViewModel()
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
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxHeight()
        ) {
            items(
                items = albumListDetails,
                key = { item ->
                    item.albumId
                }
            ) { albumItem ->
                AlbumCard(albumItem)
            }
        }
    }
}

@Composable
fun AlbumCard(albumItem: AlbumUi) {
    ConstraintLayout(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        val (albumImageRef, albumDescriptionRef) = createRefs()
        AsyncImage(
            model = albumItem.albumImages.first(),
            contentDescription = albumItem.albumName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .constrainAs(albumImageRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Row(
            modifier =
                Modifier
                    .width(260.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .constrainAs(albumDescriptionRef) {
                        start.linkTo(albumImageRef.end)
                        bottom.linkTo(albumDescriptionRef.bottom)
                        end.linkTo(parent.end)
                    },
        ) {
            Column(
                modifier =
                    Modifier
                        .height(150.dp)
                        .padding(
                            start = 10.dp,
                            top = 10.dp,
                            bottom = 10.dp,
                            end = 10.dp,
                        ),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = albumItem.albumName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = albumItem.artists.first(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = albumItem.releaseDate,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}