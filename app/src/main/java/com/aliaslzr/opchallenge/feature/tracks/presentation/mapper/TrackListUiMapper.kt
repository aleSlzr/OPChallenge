package com.aliaslzr.opchallenge.feature.tracks.presentation.mapper

import com.aliaslzr.opchallenge.feature.tracks.domain.model.Track
import com.aliaslzr.opchallenge.feature.tracks.presentation.model.TrackUi
import com.aliaslzr.opchallenge.utils.Mapper
import javax.inject.Inject

class TrackListUiMapper
    @Inject
    constructor() : Mapper<List<Track>, List<TrackUi>, Unit> {
        override fun transform(
            input: List<Track>,
            additionalArgs: Unit?,
        ): List<TrackUi> {
            val trackList: MutableList<TrackUi> = mutableListOf()
            input.forEach { trackItem ->
                trackList.add(
                    TrackUi(
                        name = trackItem.name,
                        durationMs = trackItem.durationMs,
                        trackNumber = trackItem.trackNumber,
                    ),
                )
            }
            return trackList
        }
    }