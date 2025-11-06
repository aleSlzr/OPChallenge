package com.aliaslzr.opchallenge.feature.albums.domain.model


data class Album(
    val totalTracks: Long,
    val albumId: String,
    val albumImages: List<AlbumImage>,
    val albumName: String,
    val releaseDate: String,
    val artists: List<AlbumArtist>,
)

data class AlbumImage(
    val url: String,
)

data class AlbumArtist(
    val name: String,
)