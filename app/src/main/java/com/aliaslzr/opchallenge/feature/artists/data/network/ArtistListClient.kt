package com.aliaslzr.opchallenge.feature.artists.data.network

import com.aliaslzr.opchallenge.feature.artists.data.network.model.RootArtist
import com.aliaslzr.opchallenge.utils.ARTIST_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistListClient {
    @GET(ARTIST_LIST)
    suspend fun getArtistListClient(
        @Query("ids") ids: String,
    ): Response<RootArtist>
}