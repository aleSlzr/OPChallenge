package com.aliaslzr.opchallenge.feature.artists.presentation.model

data class ArtistUi(
    val genres: List<String>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Long,
)

data class Image(
    val url: String,
    val height: Long,
    val width: Long
)