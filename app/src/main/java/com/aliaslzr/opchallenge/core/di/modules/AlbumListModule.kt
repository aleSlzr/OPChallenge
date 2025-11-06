package com.aliaslzr.opchallenge.core.di.modules

import com.aliaslzr.opchallenge.feature.albums.data.repository.AlbumListRepositoryImpl
import com.aliaslzr.opchallenge.feature.albums.data.services.AlbumListService
import com.aliaslzr.opchallenge.feature.albums.data.services.AlbumListServiceImpl
import com.aliaslzr.opchallenge.feature.albums.domain.repository.AlbumListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumListModule {
    @Binds
    abstract fun bindAlbumListRepository(
        albumListRepositoryImpl: AlbumListRepositoryImpl,
    ): AlbumListRepository

    @Binds
    abstract fun bindAlbumListService(
        albumListServiceImpl: AlbumListServiceImpl,
    ): AlbumListService
}