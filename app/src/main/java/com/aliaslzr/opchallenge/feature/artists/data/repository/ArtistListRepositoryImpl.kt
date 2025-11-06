package com.aliaslzr.opchallenge.feature.artists.data.repository

import com.aliaslzr.opchallenge.feature.artists.data.services.ArtistListService
import com.aliaslzr.opchallenge.feature.artists.domain.mapper.ArtistListDTOMapper
import com.aliaslzr.opchallenge.feature.artists.domain.model.Artist
import com.aliaslzr.opchallenge.feature.artists.domain.repository.ArtistListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistListRepositoryImpl
    @Inject
    constructor(
        private val artistListService: ArtistListService,
    ) : ArtistListRepository {
    override fun getArtistListRepository(artistIdList: String): Flow<List<Artist>> =
        artistListService.getArtistListService(artistIdList).map {
            ArtistListDTOMapper().transform(it)
        }
}