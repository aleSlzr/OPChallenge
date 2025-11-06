package com.aliaslzr.opchallenge.feature.artists.presentation.mapper

import com.aliaslzr.opchallenge.feature.artists.domain.model.Artist
import com.aliaslzr.opchallenge.feature.artists.domain.model.ArtistImage
import com.aliaslzr.opchallenge.feature.artists.presentation.model.ArtistUi
import com.aliaslzr.opchallenge.feature.artists.presentation.model.Image
import com.aliaslzr.opchallenge.utils.Mapper

class ArtistListUiMapper : Mapper<List<Artist>, List<ArtistUi>, Unit> {
    override fun transform(
        input: List<Artist>,
        additionalArgs: Unit?
    ): List<ArtistUi> {
        val artistUiList: MutableList<ArtistUi> = mutableListOf()
        input.forEach { artist ->
            artistUiList.add(
                ArtistMapper().transform(artist)
            )
        }
        return artistUiList
    }
}

class ArtistMapper : Mapper<Artist, ArtistUi, Unit> {
    override fun transform(
        input: Artist,
        additionalArgs: Unit?,
    ): ArtistUi =
        ArtistUi(
            genres = input.genres,
            id = input.id,
            images = ArtistImageListMapper().transform(input.images),
            name = input.name,
            popularity = input.popularity,
        )
}

class ArtistImageListMapper : Mapper<List<ArtistImage>, List<Image>, Unit> {
    override fun transform(
        input: List<ArtistImage>,
        additionalArgs: Unit?,
    ): List<Image> {
        val imageList: MutableList<Image> = mutableListOf()
        input.forEach { image ->
            imageList.add(
                ImageMapper().transform(image)
            )
        }
        return imageList
    }
}

class ImageMapper : Mapper<ArtistImage, Image, Unit> {
    override fun transform(
        input: ArtistImage,
        additionalArgs: Unit?,
    ): Image =
        Image(
            url = input.url,
            height = input.height,
            width = input.width,
        )
}