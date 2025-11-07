package com.aliaslzr.opchallenge.feature.tracks.data.network

import com.aliaslzr.opchallenge.feature.tracks.data.network.model.RootTrack
import com.aliaslzr.opchallenge.utils.TRACK_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackListClient {
    @GET(TRACK_LIST)
    suspend fun getTrackListClient(
        @Path("id")
        id: String,
    ): Response<RootTrack>
}