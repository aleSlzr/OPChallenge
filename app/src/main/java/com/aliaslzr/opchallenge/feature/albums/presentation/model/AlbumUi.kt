package com.aliaslzr.opchallenge.feature.albums.presentation.model

data class AlbumUi(
    val totalTracks: Long,
    val albumId: String,
    val albumImages: List<String>,
    val albumName: String,
    val releaseDate: String,
    val artists: List<String>,
)