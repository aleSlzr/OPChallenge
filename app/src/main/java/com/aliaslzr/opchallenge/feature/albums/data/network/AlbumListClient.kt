package com.aliaslzr.opchallenge.feature.albums.data.network

import com.aliaslzr.opchallenge.feature.albums.data.network.model.RootAlbum
import com.aliaslzr.opchallenge.utils.ALBUM_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumListClient {
    @GET(ALBUM_LIST)
    suspend fun getAlbumListClient(
        @Path("id")
        id: String,
        @Query("include_groups")
        includeGroups: String = "album",
        @Query("limit")
        limit: Int = 3,
        @Query("offset")
        offset: Int,
    ): Response<RootAlbum>
}