package com.aliaslzr.opchallenge.feature.artists.domain.mapper

import com.aliaslzr.opchallenge.feature.artists.data.network.model.ArtistDTO
import com.aliaslzr.opchallenge.feature.artists.data.network.model.ArtistImageDTO
import com.aliaslzr.opchallenge.feature.artists.domain.model.Artist
import com.aliaslzr.opchallenge.feature.artists.domain.model.ArtistImage
import com.aliaslzr.opchallenge.utils.Mapper

class ArtistListDTOMapper : Mapper<List<ArtistDTO>, List<Artist>, Unit> {
    override fun transform(
        input: List<ArtistDTO>,
        additionalArgs: Unit?
    ): List<Artist> {
        val artistList: MutableList<Artist> = mutableListOf()
        input.forEach { artistDTO ->
            artistList.add(
                ArtistDTOMapper().transform(artistDTO)
            )
        }
        return artistList
    }
}

class ArtistDTOMapper : Mapper<ArtistDTO, Artist, Unit> {
    override fun transform(
        input: ArtistDTO,
        additionalArgs: Unit?,
    ): Artist =
        Artist(
            genres = input.genres,
            id = input.id,
            images = ArtistImageListDTOMapper().transform(input.images),
            name = input.name,
            popularity = input.popularity,
        )
}

class ArtistImageListDTOMapper : Mapper<List<ArtistImageDTO>, List<ArtistImage>, Unit> {
    override fun transform(
        input: List<ArtistImageDTO>,
        additionalArgs: Unit?,
    ): List<ArtistImage> {
        val artistImageList: MutableList<ArtistImage> = mutableListOf()
        input.forEach { imageDTO ->
            artistImageList.add(
                ArtistImageDTOMapper().transform(imageDTO)
            )
        }
        return artistImageList
    }
}

class ArtistImageDTOMapper : Mapper<ArtistImageDTO, ArtistImage, Unit> {
    override fun transform(
        input: ArtistImageDTO,
        additionalArgs: Unit?,
    ): ArtistImage =
        ArtistImage(
            url = input.url,
            height = input.height,
            width = input.width,
        )
}