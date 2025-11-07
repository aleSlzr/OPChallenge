package com.aliaslzr.opchallenge.feature.tracks.data.services

import com.aliaslzr.opchallenge.feature.tracks.data.network.TrackListClient
import com.aliaslzr.opchallenge.feature.tracks.data.network.model.RootTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrackListServiceImpl
    @Inject
    constructor(
        private val trackListClient: TrackListClient,
    ) : TrackListService {
        override fun getTrackListService(albumId: String): Flow<RootTrack> =
            flow {
                try {
                    val response = trackListClient.getTrackListClient(albumId)
                    response.body()?.let {
                        emit(it)
                    }
                } catch (e: Exception) {
                    throw e
                }
            }
    }