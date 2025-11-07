package com.aliaslzr.opchallenge.feature.tracks.data.network.model

import com.google.gson.annotations.SerializedName

data class RootTrack(
    @SerializedName("total")
    val total: Long,
    @SerializedName("items")
    val items: List<TrackItemDTO>,
)

data class TrackItemDTO(
    @SerializedName("duration_ms")
    val durationMS: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("track_number")
    val trackNumber: Long,
)