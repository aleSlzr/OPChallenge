package com.aliaslzr.opchallenge.feature.albums.presentation.ui

import com.aliaslzr.opchallenge.feature.albums.presentation.model.AlbumUi

sealed class AlbumListUiState {
    data object Loading : AlbumListUiState()
    data class Error(val errorMessage: String) : AlbumListUiState()
    data class Success(val albumList: List<AlbumUi>) : AlbumListUiState()
}