package com.aliaslzr.opchallenge.feature.albums.domain.repository

import com.aliaslzr.opchallenge.feature.albums.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumListRepository {
    fun getAlbumListRepository(artistId: String, offset: Int): Flow<List<Album>>
}