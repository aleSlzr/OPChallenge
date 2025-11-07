package com.aliaslzr.opchallenge.feature.tracks.domain.usecase

import com.aliaslzr.opchallenge.feature.tracks.domain.repository.TrackListRepository
import javax.inject.Inject

class TrackListUseCase
    @Inject
    constructor(
        private val trackListRepository: TrackListRepository,
    ) {
        operator fun invoke(albumId: String) = trackListRepository.getTrackListRepository(albumId)
    }