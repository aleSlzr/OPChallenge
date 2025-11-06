package com.aliaslzr.opchallenge.feature.artists.domain.repository

import com.aliaslzr.opchallenge.feature.artists.domain.model.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistListRepository {
    fun getArtistListRepository(artistIdList: String): Flow<List<Artist>>
}