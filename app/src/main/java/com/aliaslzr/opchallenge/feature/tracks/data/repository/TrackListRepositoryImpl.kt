package com.aliaslzr.opchallenge.feature.tracks.data.repository

import com.aliaslzr.opchallenge.feature.tracks.data.services.TrackListService
import com.aliaslzr.opchallenge.feature.tracks.domain.mapper.TrackListDTOMapper
import com.aliaslzr.opchallenge.feature.tracks.domain.model.Track
import com.aliaslzr.opchallenge.feature.tracks.domain.repository.TrackListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrackListRepositoryImpl
    @Inject
    constructor(
        private val trackListService: TrackListService,
    ) : TrackListRepository {
        override fun getTrackListRepository(albumId: String): Flow<List<Track>> =
            trackListService.getTrackListService(albumId).map {
                TrackListDTOMapper().transform(it)
            }
    }