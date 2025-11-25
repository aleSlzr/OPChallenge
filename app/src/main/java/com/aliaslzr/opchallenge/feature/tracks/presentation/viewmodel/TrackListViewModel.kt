package com.aliaslzr.opchallenge.feature.tracks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliaslzr.opchallenge.feature.tracks.domain.usecase.TrackListUseCase
import com.aliaslzr.opchallenge.feature.tracks.presentation.mapper.TrackListUiMapper
import com.aliaslzr.opchallenge.feature.tracks.presentation.ui.TrackUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackListViewModel
    @Inject
    constructor(
        private val getTrackListUseCase: TrackListUseCase,
        private val trackListUiMapper: TrackListUiMapper,
    ) : ViewModel() {
        private val _trackListUiState = MutableStateFlow<TrackUiState>(TrackUiState.Loading)
        val trackListUiState: StateFlow<TrackUiState> = _trackListUiState

        fun getAlbumTracks(albumId: String) {
            viewModelScope.launch {
                getTrackListUseCase(albumId)
                    .onStart {
                        _trackListUiState.update {
                            TrackUiState.Loading
                        }
                    }.map { trackList ->
                        trackListUiMapper.transform(trackList)
                    }.catch { error ->
                        _trackListUiState.update {
                            TrackUiState.Error(error.message.toString())
                        }
                    }.collect { trackListResult ->
                        _trackListUiState.update {
                            TrackUiState.Success(trackListResult)
                        }
                    }
            }
        }
    }