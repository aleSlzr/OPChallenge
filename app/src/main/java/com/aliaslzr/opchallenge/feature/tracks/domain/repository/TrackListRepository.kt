package com.aliaslzr.opchallenge.feature.tracks.domain.repository

import com.aliaslzr.opchallenge.feature.tracks.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackListRepository {
    fun getTrackListRepository(albumId: String): Flow<List<Track>>
}