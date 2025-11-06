package com.aliaslzr.opchallenge.feature.albums.presentation.mapper

import com.aliaslzr.opchallenge.feature.albums.domain.model.Album
import com.aliaslzr.opchallenge.feature.albums.domain.model.AlbumArtist
import com.aliaslzr.opchallenge.feature.albums.domain.model.AlbumImage
import com.aliaslzr.opchallenge.feature.albums.presentation.model.AlbumUi
import com.aliaslzr.opchallenge.utils.Mapper

class AlbumListUiMapper : Mapper<List<Album>, List<AlbumUi>, Unit> {
    override fun transform(
        input: List<Album>,
        additionalArgs: Unit?,
    ): List<AlbumUi> {
        val albumList: MutableList<AlbumUi> = mutableListOf()
        input.forEach { album ->
            albumList.add(
                AlbumUiMapper().transform(album)
            )
        }
        return albumList
    }
}

class AlbumUiMapper : Mapper<Album, AlbumUi, Unit> {
    override fun transform(
        input: Album,
        additionalArgs: Unit?,
    ): AlbumUi =
        AlbumUi(
            totalTracks = input.totalTracks,
            albumId = input.albumId,
            albumImages = AlbumImageUiMapper().transform(input.albumImages),
            albumName = input.albumName,
            releaseDate = input.releaseDate,
            artists = AlbumArtistUiMapper().transform(input.artists),
        )
}

class AlbumImageUiMapper : Mapper<List<AlbumImage>, List<String>, Unit> {
    override fun transform(
        input: List<AlbumImage>,
        additionalArgs: Unit?,
    ): List<String> {
        val albumImages: MutableList<String> = mutableListOf()
        input.forEach { albumImage ->
            albumImages.add(albumImage.url)
        }
        return albumImages
    }
}

class AlbumArtistUiMapper : Mapper<List<AlbumArtist>, List<String>, Unit> {
    override fun transform(
        input: List<AlbumArtist>,
        additionalArgs: Unit?,
    ): List<String> {
        val albumArtists: MutableList<String> = mutableListOf()
        input.forEach { artist ->
            albumArtists.add(artist.name)
        }
        return albumArtists
    }
}