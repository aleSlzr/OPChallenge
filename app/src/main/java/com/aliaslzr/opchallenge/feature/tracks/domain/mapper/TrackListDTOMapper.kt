package com.aliaslzr.opchallenge.feature.tracks.domain.mapper

import com.aliaslzr.opchallenge.feature.tracks.data.network.model.RootTrack
import com.aliaslzr.opchallenge.feature.tracks.data.network.model.TrackItemDTO
import com.aliaslzr.opchallenge.feature.tracks.domain.model.Track
import com.aliaslzr.opchallenge.utils.Mapper

class TrackListDTOMapper : Mapper<RootTrack, List<Track>, Unit> {
    override fun transform(
        input: RootTrack,
        additionalArgs: Unit?,
    ): List<Track> {
        val trackList: MutableList<Track> = mutableListOf()
        input.items.forEach { trackItemDTO ->
            trackList.add(TrackDTOMapper().transform(trackItemDTO))
        }
        return trackList
    }
}

class TrackDTOMapper : Mapper<TrackItemDTO, Track, Unit> {
    override fun transform(
        input: TrackItemDTO,
        additionalArgs: Unit?,
    ): Track =
        Track(
            name = input.name,
            durationMs = input.durationMS,
            trackNumber = input.trackNumber,
        )
}