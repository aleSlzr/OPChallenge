package com.aliaslzr.opchallenge.feature.albums.presentation.model

import com.aliaslzr.opchallenge.feature.tracks.presentation.model.TrackUi

data class AlbumUi(
    val totalTracks: Long,
    val albumId: String,
    val albumImages: List<String>,
    val albumName: String,
    val releaseDate: String,
    val artists: List<String>,
    val tracks: List<TrackUi>? = listOf()
)