package com.aliaslzr.opchallenge.feature.tracks.presentation.ui

import com.aliaslzr.opchallenge.feature.tracks.presentation.model.TrackUi

sealed class TrackUiState {
    data object Loading : TrackUiState()
    data class Error(val errorMessage: String) : TrackUiState()
    data class Success(val trackList: List<TrackUi>) : TrackUiState()
}