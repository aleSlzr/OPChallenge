package com.aliaslzr.opchallenge.feature.albums.domain.usecase

import com.aliaslzr.opchallenge.feature.albums.domain.repository.AlbumListRepository
import javax.inject.Inject

class AlbumListUseCase
    @Inject
    constructor(
        private val albumListRepository: AlbumListRepository,
    ) {
        operator fun invoke(artistId: String, offset: Int) = albumListRepository.getAlbumListRepository(artistId, offset)
    }