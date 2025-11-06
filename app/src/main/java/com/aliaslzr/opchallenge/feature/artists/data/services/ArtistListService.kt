package com.aliaslzr.opchallenge.feature.artists.data.services

import com.aliaslzr.opchallenge.feature.artists.data.network.model.RootArtist
import kotlinx.coroutines.flow.Flow

interface ArtistListService {
    fun getArtistListService(artistIdList: String): Flow<RootArtist>
}