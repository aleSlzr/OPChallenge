package com.aliaslzr.opchallenge.feature.albums.data.services

import com.aliaslzr.opchallenge.feature.albums.data.network.AlbumListClient
import com.aliaslzr.opchallenge.feature.albums.data.network.model.RootAlbum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumListServiceImpl
    @Inject
    constructor(
        private val albumListClient: AlbumListClient,
    ) : AlbumListService {
        override fun getAlbumListService(artistId: String): Flow<RootAlbum> =
            flow {
                try {
                    val response = albumListClient.getAlbumListClient(artistId)
                    response.body()?.let {
                        emit(it)
                    }
                } catch (e: Exception) {
                    throw e
                }
            }
    }