package com.aliaslzr.opchallenge.feature.artists.presentation.ui

import com.aliaslzr.opchallenge.feature.artists.presentation.model.ArtistUi

sealed class ArtistListUiState {
    data object Loading : ArtistListUiState()
    data object Error : ArtistListUiState()
    data class Success(val artistList: List<ArtistUi>) : ArtistListUiState()
}