package com.aliaslzr.opchallenge.feature.albums.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliaslzr.opchallenge.feature.albums.domain.usecase.AlbumListUseCase
import com.aliaslzr.opchallenge.feature.albums.presentation.mapper.AlbumListUiMapper
import com.aliaslzr.opchallenge.feature.albums.presentation.ui.AlbumListUiState
import com.aliaslzr.opchallenge.feature.tracks.domain.usecase.TrackListUseCase
import com.aliaslzr.opchallenge.feature.tracks.presentation.mapper.TrackListUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel
    @Inject
    constructor(
        private val getAlbumListUseCase: AlbumListUseCase,
        private val getTrackListUseCase: TrackListUseCase,
    ) : ViewModel() {
        private val _albumListUiState =
            MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
        val albumListUiState: StateFlow<AlbumListUiState> = _albumListUiState

        fun getAlbumDetails(artistId: String) {
            viewModelScope.launch {
                getAlbumListUseCase(artistId)
                    .map { albumDetailList ->
                        val albumMapped = AlbumListUiMapper().transform(albumDetailList)
                        coroutineScope {
                            albumMapped.map { albumItem ->
                                async {
                                    val trackList = getTrackListUseCase(albumItem.albumId).firstOrNull() ?: emptyList()
                                    val trackListMapped = TrackListUiMapper().transform(trackList)
                                    albumItem.copy(tracks = trackListMapped)
                                }
                            }.awaitAll()
                        }
                    }.onStart {
                        _albumListUiState.update {
                            AlbumListUiState.Loading
                        }
                    }.catch { error ->
                        _albumListUiState.update {
                            AlbumListUiState.Error(error.message.toString())
                        }
                    }.collect { albumWithTracks ->
                        _albumListUiState.update {
                            AlbumListUiState.Success(albumWithTracks)
                        }
                    }
            }
        }
    }