package com.aliaslzr.opchallenge.feature.albums.domain.mapper

import com.aliaslzr.opchallenge.feature.albums.data.network.model.AlbumArtistDTO
import com.aliaslzr.opchallenge.feature.albums.data.network.model.AlbumImagesDTO
import com.aliaslzr.opchallenge.feature.albums.data.network.model.AlbumItemDTO
import com.aliaslzr.opchallenge.feature.albums.data.network.model.RootAlbum
import com.aliaslzr.opchallenge.feature.albums.domain.model.Album
import com.aliaslzr.opchallenge.feature.albums.domain.model.AlbumArtist
import com.aliaslzr.opchallenge.feature.albums.domain.model.AlbumImage
import com.aliaslzr.opchallenge.utils.Mapper

class AlbumListDTOMapper : Mapper<RootAlbum, List<Album>, Unit> {
    override fun transform(
        input: RootAlbum,
        additionalArgs: Unit?
    ): List<Album> {
        val albumList: MutableList<Album> = mutableListOf()
        input.items.forEach { albumDTO ->
            albumList.add(
                AlbumDTOMapper().transform(albumDTO)
            )
        }
        return albumList
    }
}

class AlbumDTOMapper : Mapper<AlbumItemDTO, Album, Unit> {
    override fun transform(
        input: AlbumItemDTO,
        additionalArgs: Unit?,
    ): Album =
        Album(
            totalTracks = input.totalTracks,
            albumId = input.albumId,
            albumImages = AlbumImageListDTOMapper().transform(input.albumImages),
            albumName = input.albumName,
            releaseDate = input.releaseDate,
            artists = AlbumArtistListDTOMapper().transform(input.artists),
        )
}

class AlbumImageListDTOMapper : Mapper<List<AlbumImagesDTO>, List<AlbumImage>, Unit> {
    override fun transform(
        input: List<AlbumImagesDTO>,
        additionalArgs: Unit?,
    ): List<AlbumImage> {
        val albumImages: MutableList<AlbumImage> = mutableListOf()
        input.forEach { albumImageDTO ->
            albumImages.add(
                AlbumImage(
                    url = albumImageDTO.url
                )
            )
        }
        return albumImages
    }
}

class AlbumArtistListDTOMapper : Mapper<List<AlbumArtistDTO>, List<AlbumArtist>, Unit> {
    override fun transform(
        input: List<AlbumArtistDTO>,
        additionalArgs: Unit?,
    ): List<AlbumArtist> {
        val albumArtist: MutableList<AlbumArtist> = mutableListOf()
        input.forEach { albumArtistDTO ->
            albumArtist.add(
                AlbumArtist(
                    name = albumArtistDTO.name
                )
            )
        }
        return albumArtist
    }
}