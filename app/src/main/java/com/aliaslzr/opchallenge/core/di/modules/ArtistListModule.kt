package com.aliaslzr.opchallenge.core.di.modules

import com.aliaslzr.opchallenge.feature.artists.data.repository.ArtistListRepositoryImpl
import com.aliaslzr.opchallenge.feature.artists.data.services.ArtistListService
import com.aliaslzr.opchallenge.feature.artists.data.services.ArtistListServiceImpl
import com.aliaslzr.opchallenge.feature.artists.domain.repository.ArtistListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistListModule {
    @Binds
    abstract fun bindArtistListRepository(
        artistListRepositoryImpl: ArtistListRepositoryImpl,
    ): ArtistListRepository

    @Binds
    abstract fun bindArtistListService(
        artistListServiceImpl: ArtistListServiceImpl,
    ): ArtistListService
}