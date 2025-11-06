package com.aliaslzr.opchallenge.feature.albums.data.network

import com.aliaslzr.opchallenge.feature.albums.data.network.model.RootAlbum
import com.aliaslzr.opchallenge.utils.ALBUM_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumListClient {
    @GET(ALBUM_LIST)
    suspend fun getAlbumListClient(
        @Path("id")
        id: String,
    ): Response<RootAlbum>
}