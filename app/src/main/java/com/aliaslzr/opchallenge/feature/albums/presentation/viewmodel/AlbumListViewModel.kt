package com.aliaslzr.opchallenge.feature.albums.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliaslzr.opchallenge.feature.albums.domain.model.Album
import com.aliaslzr.opchallenge.feature.albums.domain.usecase.AlbumListUseCase
import com.aliaslzr.opchallenge.feature.albums.presentation.mapper.AlbumListUiMapper
import com.aliaslzr.opchallenge.feature.albums.presentation.model.AlbumUi
import com.aliaslzr.opchallenge.feature.albums.presentation.ui.AlbumListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class AlbumListViewModel
    @Inject
    constructor(
        private val getAlbumListUseCase: AlbumListUseCase,
        private val albumListUiMapper: AlbumListUiMapper,
    ) : ViewModel() {
        private val _artistId = MutableStateFlow("")

        val albumListUiState: StateFlow<AlbumListUiState> =
            _artistId.flatMapLatest { artistId ->
                loadAlbums(artistId)
            }.stateIn(
                scope = viewModelScope,
                initialValue = AlbumListUiState.Loading,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            )

        fun getAlbumDetails(artistId: String) {
            _artistId.update {
                artistId
            }
        }

        private fun loadAlbums(artistId: String): Flow<AlbumListUiState> = flow {
            var offset = 0
            var totalAlbums = Long.MAX_VALUE
            var loadedAlbumList = emptyList<AlbumUi>()
            val ticker = flow {
                while (true) {
                    emit(Unit)
                    delay(10_000L)
                    offset += 3
                }
            }.takeWhile {
                loadedAlbumList.size < totalAlbums
            }
            emitAll(
                ticker
                    .flatMapLatest {
                        getAlbumListUseCase(artistId, offset)
                            .map<List<Album>, AlbumListUiState> { albumDetailList ->
                                val albumList = albumListUiMapper.transform(albumDetailList)
                                totalAlbums = albumList.first().totalAlbums
                                loadedAlbumList = loadedAlbumList + albumList
                                AlbumListUiState.Success(loadedAlbumList)
                            }.onStart {
                                emit(AlbumListUiState.Loading)
                            }.catch { error ->
                                emit(AlbumListUiState.Error(error.message.toString()))
                            }
                    }
            )
        }
    }