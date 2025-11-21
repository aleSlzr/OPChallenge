package com.aliaslzr.opchallenge.feature.albums.data.services

import com.aliaslzr.opchallenge.feature.albums.data.network.model.RootAlbum
import kotlinx.coroutines.flow.Flow

interface AlbumListService {
    fun getAlbumListService(
        artistId: String,
        offset: Int,
    ): Flow<RootAlbum>
}