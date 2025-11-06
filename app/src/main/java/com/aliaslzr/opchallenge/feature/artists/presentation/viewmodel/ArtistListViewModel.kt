package com.aliaslzr.opchallenge.feature.artists.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliaslzr.opchallenge.feature.artists.domain.usecase.ArtistListUseCase
import com.aliaslzr.opchallenge.feature.artists.presentation.mapper.ArtistListUiMapper
import com.aliaslzr.opchallenge.feature.artists.presentation.ui.ArtistListUiState
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
        val artistUiState: StateFlow<ArtistListUiState> =
            getArtistListUseCase.invoke("3WaJSfKnzc65VDgmj2zU8B,0X380XXQSNBYuleKzav5UO,6e9wIFWhBPHLE9bXK8gtBI,3kjuyTCjPG1WMFCiyc5IuB,57ylwQTnFnIhJh4nu4rxCs,2rBcvLKWCZs9w1qIWv560v,1l9d7B8W0IHy3LqWsxP2SH,1WvvwcQx0tj6NdDhZZ2zZz,6RZUqkomCmb8zCRqc9eznB,3j0kMFxXTTYsuw1twLClw3,6VCoG3MG7ZKRxDjaYOvtrF,1HY2Jd0NmPuamShAr6KMms")
                .map { artists ->
                    ArtistListUiState.Success(
                        artistList = ArtistListUiMapper().transform(artists),
                    )
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = ArtistListUiState.Loading,
                    started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                )
    }