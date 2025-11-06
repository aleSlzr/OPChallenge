package com.aliaslzr.opchallenge.feature.artists.domain.model

data class Artist(
    val genres: List<String>,
    val id: String,
    val images: List<ArtistImage>,
    val name: String,
    val popularity: Long,
)

data class ArtistImage(
    val url: String,
    val height: Long,
    val width: Long
)