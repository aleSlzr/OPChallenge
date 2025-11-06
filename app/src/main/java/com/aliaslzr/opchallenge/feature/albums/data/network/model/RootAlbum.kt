package com.aliaslzr.opchallenge.feature.albums.data.network.model

import com.google.gson.annotations.SerializedName

data class RootAlbum(
    @SerializedName("total")
    val total: Long,
    @SerializedName("items")
    val items: List<AlbumItemDTO>
)

data class AlbumItemDTO(
    @SerializedName("total_tracks")
    val totalTracks: Long,
    @SerializedName("id")
    val albumId: String,
    @SerializedName("images")
    val albumImages: List<AlbumImagesDTO>,
    @SerializedName("name")
    val albumName: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("artists")
    val artists: List<AlbumArtistDTO>,
)

data class AlbumImagesDTO(
    @SerializedName("url")
    val url: String,
)

data class AlbumArtistDTO(
    @SerializedName("name")
    val name: String,
)