package com.aliaslzr.opchallenge.feature.albums.data.repository

import com.aliaslzr.opchallenge.feature.albums.data.services.AlbumListService
import com.aliaslzr.opchallenge.feature.albums.domain.mapper.AlbumListDTOMapper
import com.aliaslzr.opchallenge.feature.albums.domain.model.Album
import com.aliaslzr.opchallenge.feature.albums.domain.repository.AlbumListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumListRepositoryImpl
    @Inject
    constructor(
        private val albumListService: AlbumListService,
    ) : AlbumListRepository {
        override fun getAlbumListRepository(artistId: String, offset: Int): Flow<List<Album>> =
            albumListService
                .getAlbumListService(
                    artistId = artistId,
                    offset = offset,
                ).map {
                    AlbumListDTOMapper().transform(it)
                }
}