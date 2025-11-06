package com.aliaslzr.opchallenge.feature.artists.data.services

import com.aliaslzr.opchallenge.feature.artists.data.network.ArtistListClient
import com.aliaslzr.opchallenge.feature.artists.data.network.model.RootArtist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistListServiceImpl
    @Inject
    constructor(
        private val artistListClient: ArtistListClient,
    ) : ArtistListService {
    override fun getArtistListService(artistIdList: String): Flow<RootArtist> =
        flow {
            val response = artistListClient.getArtistListClient(artistIdList)
            response.body()?.let {
                emit(it)
            }
        }
}