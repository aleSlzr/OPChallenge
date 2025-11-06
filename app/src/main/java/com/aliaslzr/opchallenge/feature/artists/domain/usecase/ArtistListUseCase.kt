package com.aliaslzr.opchallenge.feature.artists.domain.usecase

import com.aliaslzr.opchallenge.feature.artists.domain.repository.ArtistListRepository
import javax.inject.Inject

class ArtistListUseCase
    @Inject
    constructor(
      private val artistListRepository: ArtistListRepository,
    ) {
        operator fun invoke(artistIdList: String) = artistListRepository.getArtistListRepository(artistIdList)
    }