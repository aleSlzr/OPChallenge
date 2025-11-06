package com.aliaslzr.opchallenge.feature.artists.presentation.ui

import com.aliaslzr.opchallenge.feature.artists.presentation.model.ArtistUi

sealed class ArtistListUIState {
    data object Loading : ArtistListUIState()
    data object Error : ArtistListUIState()
    data class Success(val artistList: List<ArtistUi>) : ArtistListUIState()
}