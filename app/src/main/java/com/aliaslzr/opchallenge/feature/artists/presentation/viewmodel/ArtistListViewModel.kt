package com.aliaslzr.opchallenge.feature.artists.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliaslzr.opchallenge.feature.artists.domain.usecase.ArtistListUseCase
import com.aliaslzr.opchallenge.feature.artists.presentation.mapper.ArtistListUiMapper
import com.aliaslzr.opchallenge.feature.artists.presentation.ui.ArtistListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ArtistListViewModel
    @Inject
    constructor(
        getArtistListUseCase: ArtistListUseCase,
    ) : ViewModel() {
        val artistUiState: StateFlow<ArtistListUIState> =
            getArtistListUseCase.invoke("4Z8W4fKeB5YxbusRsdQVPb,3WaJSfKnzc65VDgmj2zU8B")
                .map { artists ->
                    ArtistListUIState.Success(
                        artistList = ArtistListUiMapper().transform(artists),
                    )
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = ArtistListUIState.Loading,
                    started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                )
    }