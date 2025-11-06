package com.aliaslzr.opchallenge.feature.albums.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliaslzr.opchallenge.feature.albums.domain.usecase.AlbumListUseCase
import com.aliaslzr.opchallenge.feature.albums.presentation.mapper.AlbumListUiMapper
import com.aliaslzr.opchallenge.feature.albums.presentation.ui.AlbumListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel
    @Inject
    constructor(
        private val getAlbumListUseCase: AlbumListUseCase,
    ) : ViewModel() {
        private val _albumListUiState =
            MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
        val albumListUiState: StateFlow<AlbumListUiState> = _albumListUiState

        fun getAlbumDetails(artistId: String) {
            viewModelScope.launch {
                getAlbumListUseCase(artistId = artistId)
                    .map { albumDetailList ->
                        AlbumListUiMapper().transform(albumDetailList)
                    }.catch { error ->
                            AlbumListUiState.Error(error.message.toString())
                    }.collect { response ->
                        _albumListUiState.update {
                            AlbumListUiState.Success(response)
                        }
                    }
            }
        }
    }