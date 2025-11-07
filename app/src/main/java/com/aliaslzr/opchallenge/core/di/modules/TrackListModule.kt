package com.aliaslzr.opchallenge.core.di.modules

import com.aliaslzr.opchallenge.feature.tracks.data.repository.TrackListRepositoryImpl
import com.aliaslzr.opchallenge.feature.tracks.data.services.TrackListService
import com.aliaslzr.opchallenge.feature.tracks.data.services.TrackListServiceImpl
import com.aliaslzr.opchallenge.feature.tracks.domain.repository.TrackListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TrackListModule {
    @Binds
    abstract fun bindTrackListRepository(
        trackListRepositoryImpl: TrackListRepositoryImpl,
    ): TrackListRepository

    @Binds
    abstract fun bindTrackListService(
        trackListServiceImpl: TrackListServiceImpl
    ): TrackListService
}