package com.aliaslzr.opchallenge.feature.tracks.data.services

import com.aliaslzr.opchallenge.feature.tracks.data.network.model.RootTrack
import kotlinx.coroutines.flow.Flow

interface TrackListService {
    fun getTrackListService(albumId: String): Flow<RootTrack>
}