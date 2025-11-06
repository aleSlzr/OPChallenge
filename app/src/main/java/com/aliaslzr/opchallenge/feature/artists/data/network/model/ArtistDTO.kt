package com.aliaslzr.opchallenge.feature.artists.data.network.model

import com.google.gson.annotations.SerializedName

data class RootArtist(
    @SerializedName("artists")
    val artists : List<ArtistDTO>
)

data class ArtistDTO(
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ArtistImageDTO>,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Long,
)

data class ArtistImageDTO(
    @SerializedName("url")
    val url: String,
    @SerializedName("height")
    val height: Long,
    @SerializedName("width")
    val width: Long
)