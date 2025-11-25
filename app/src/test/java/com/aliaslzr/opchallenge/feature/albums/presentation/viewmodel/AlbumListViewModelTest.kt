package com.aliaslzr.opchallenge.feature.albums.presentation.viewmodel

import app.cash.turbine.test
import com.aliaslzr.opchallenge.MainDispatcherRule
import com.aliaslzr.opchallenge.feature.albums.domain.model.Album
import com.aliaslzr.opchallenge.feature.albums.domain.model.AlbumArtist
import com.aliaslzr.opchallenge.feature.albums.domain.model.AlbumImage
import com.aliaslzr.opchallenge.feature.albums.domain.usecase.AlbumListUseCase
import com.aliaslzr.opchallenge.feature.albums.presentation.mapper.AlbumListUiMapper
import com.aliaslzr.opchallenge.feature.albums.presentation.model.AlbumUi
import com.aliaslzr.opchallenge.feature.albums.presentation.ui.AlbumListUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class AlbumListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockedAlbumId = "3WaJSfKnzc65VDgmj2zU8B"

    private val mockAlbumListUseCase: AlbumListUseCase = mockk()
    private val mockAlbumListUiMapper: AlbumListUiMapper = mockk()

    private lateinit var viewModel: AlbumListViewModel

    @Test
    fun `when ViewModel is created, initial state should be Loading`() = runTest {
        // Given
        every {
            mockAlbumListUseCase(artistId = any(), offset = any())
        } returns flowOf(emptyList())

        // When
        viewModel = AlbumListViewModel(
            getAlbumListUseCase = mockAlbumListUseCase,
            albumListUiMapper = mockAlbumListUiMapper,
        )

        // Then
        viewModel.albumListUiState.test {
            // Loading
            assertEquals(expected = AlbumListUiState.Loading, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when album list is fetched successfully, should emit Success state`() = runTest {
        // Given
        every {
            mockAlbumListUseCase(artistId = any(), offset = any())
        } returns flowOf(mockAlbumList)

        every {
            mockAlbumListUiMapper.transform(mockAlbumList)
        } returns expectedUiAlbumList

        // When
        viewModel = AlbumListViewModel(
            getAlbumListUseCase = mockAlbumListUseCase,
            albumListUiMapper = mockAlbumListUiMapper,
        )

        // Then
        viewModel.albumListUiState.test {
            // Loading
            assertEquals(expected = AlbumListUiState.Loading, awaitItem())

            // returns first page of albumList
            val successState = awaitItem() as AlbumListUiState.Success
            assertEquals(expected = expectedUiAlbumList.size, actual = successState.albumList.size)
            assertEquals(expected = expectedUiAlbumList.first().albumName, actual = successState.albumList.first().albumName)
            assertEquals(expected = expectedUiAlbumList.first().albumId, actual = successState.albumList.first().albumId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when polling loads all albums, ticker should stop`() = runTest {
        // Given
        every {
            mockAlbumListUseCase(mockedAlbumId, 0)
        } returns flowOf(page1)
        every {
            mockAlbumListUseCase(mockedAlbumId, 3)
        } returns flowOf(page2)

        every {
            mockAlbumListUiMapper.transform(page1)
        } returns uIPage1
        every {
            mockAlbumListUiMapper.transform(page2)
        } returns uIPage2

        // When
        viewModel = AlbumListViewModel(
            getAlbumListUseCase = mockAlbumListUseCase,
            albumListUiMapper = mockAlbumListUiMapper,
        )
        viewModel.getAlbumDetails(mockedAlbumId)

        // Then
        viewModel.albumListUiState.test {
            // Loading
            assertEquals(expected = AlbumListUiState.Loading, actual = awaitItem())

            // first page called
            val firstCollection = awaitItem() as AlbumListUiState.Success
            assertEquals(expected = 1, actual = firstCollection.albumList.size)

            // advance time
            advanceTimeBy(10_000L)

            // allow coroutine work to run
            advanceUntilIdle()

            // when loads a new page the state changes to Loading until new fetch call
            assertEquals(expected = AlbumListUiState.Loading, actual = awaitItem())

            // second page called
            val secondCollection = awaitItem() as AlbumListUiState.Success
            assertEquals(expected = 2, actual = secondCollection.albumList.size)

            // all albums are loaded, so ticker should stop
            // advance time
            advanceTimeBy(50_000L)

            // allow coroutine work to run
            advanceUntilIdle()

            // there shouldn't be new emissions
            expectNoEvents()

            cancelAndIgnoreRemainingEvents()
        }
        verify(exactly = 1) { mockAlbumListUseCase(mockedAlbumId, 0) }
        verify(exactly = 1) { mockAlbumListUseCase(mockedAlbumId, 3) }
    }

    @Test
    fun `mapper invoked for each fetched page`() = runTest {
        // Given
        every {
            mockAlbumListUseCase(mockedAlbumId, 0)
        } returns flowOf(page1)
        every {
            mockAlbumListUseCase(mockedAlbumId, 3)
        } returns flowOf(page2)

        every {
            mockAlbumListUiMapper.transform(page1)
        } returns uIPage1
        every {
            mockAlbumListUiMapper.transform(page2)
        } returns uIPage2

        // When
        viewModel = AlbumListViewModel(
            getAlbumListUseCase = mockAlbumListUseCase,
            albumListUiMapper = mockAlbumListUiMapper,
        )
        viewModel.getAlbumDetails(mockedAlbumId)

        // Then
        viewModel.albumListUiState.test {
            // Loading
            assertEquals(expected = AlbumListUiState.Loading, actual = awaitItem())

            // first page called
            val firstCollection = awaitItem() as AlbumListUiState.Success
            assertEquals(expected = 1, actual = firstCollection.albumList.size)

            // advance time
            advanceTimeBy(10_000L)

            // allow coroutine work to run
            advanceUntilIdle()

            // when loads a new page the state changes to Loading until new fetch call
            assertEquals(expected = AlbumListUiState.Loading, actual = awaitItem())

            // second page called
            val secondCollection = awaitItem() as AlbumListUiState.Success
            assertEquals(expected = 2, actual = secondCollection.albumList.size)

            cancelAndIgnoreRemainingEvents()
        }

        verify { mockAlbumListUiMapper.transform(page1) }
        verify { mockAlbumListUiMapper.transform(page2) }
    }

    @Test
    fun `should call AlbumId with correct ID`() = runTest {
        // Given
        val expectedOffset = 0
        every {
            mockAlbumListUseCase(artistId = any(), offset = any())
        } returns flowOf(emptyList())

        // When
        viewModel = AlbumListViewModel(
            getAlbumListUseCase = mockAlbumListUseCase,
            albumListUiMapper = mockAlbumListUiMapper,
        )
        viewModel.getAlbumDetails(mockedAlbumId)

        viewModel.albumListUiState.test {
            awaitItem() // Loading
            awaitItem() // Success

            cancelAndIgnoreRemainingEvents()
        }

        // Then
        verify { mockAlbumListUseCase(mockedAlbumId, expectedOffset) }
    }

    private val expectedUiAlbumList =
        listOf(
            AlbumUi(
                totalAlbums = 11,
                totalTracks = 11,
                albumId = "albumId_1",
                albumImages = listOf("album/image.com"),
                albumName = "album 1",
                releaseDate = "12-12-2025",
                artists = listOf("artist name"),
            ),
            AlbumUi(
                totalAlbums = 22,
                totalTracks = 22,
                albumId = "albumId_2",
                albumImages = listOf("album/image2.com"),
                albumName = "album 2",
                releaseDate = "12-12-2025",
                artists = listOf("artist name 2"),
            ),
        )

    private val mockAlbumList =
        listOf(
            Album(
                totalAlbums = 11,
                totalTracks = 11,
                albumId = "albumId_1",
                albumImages = listOf(
                    AlbumImage(
                        url = "album/image.com",
                    ),
                ),
                albumName = "album 1",
                releaseDate = "12-12-2025",
                artists = listOf(
                    AlbumArtist(
                        name = "artist name",
                    ),
                ),
            ),
            Album(
                totalAlbums = 22,
                totalTracks = 22,
                albumId = "albumId_2",
                albumImages = listOf(
                    AlbumImage(
                        url = "album/image2.com",
                    ),
                ),
                albumName = "album 2",
                releaseDate = "12-12-2025",
                artists = listOf(
                    AlbumArtist(
                        name = "artist name 2",
                    ),
                ),
            ),
        )

    private val uIPage1 = listOf(
        AlbumUi(
            totalAlbums = 2,
            totalTracks = 11,
            albumId = "albumId_1",
            albumImages = listOf("album/image.com"),
            albumName = "album 1",
            releaseDate = "12-12-2025",
            artists = listOf("artist name"),
        ),
    )
    private val uIPage2 = listOf(
        AlbumUi(
            totalAlbums = 2,
            totalTracks = 22,
            albumId = "albumId_2",
            albumImages = listOf("album/image2.com"),
            albumName = "album 2",
            releaseDate = "12-12-2025",
            artists = listOf("artist name 2"),
        ),
    )

    private val page1 = listOf(
        Album(
            totalAlbums = 2,
            totalTracks = 11,
            albumId = "albumId_1",
            albumImages = listOf(
                AlbumImage(
                    url = "album/image.com",
                ),
            ),
            albumName = "album 1",
            releaseDate = "12-12-2025",
            artists = listOf(
                AlbumArtist(
                    name = "artist name",
                ),
            ),
        ),
    )
    private val page2 = listOf(
        Album(
            totalAlbums = 2,
            totalTracks = 22,
            albumId = "albumId_2",
            albumImages = listOf(
                AlbumImage(
                    url = "album/image2.com",
                ),
            ),
            albumName = "album 2",
            releaseDate = "12-12-2025",
            artists = listOf(
                AlbumArtist(
                    name = "artist name 2",
                ),
            ),
        ),
    )
}