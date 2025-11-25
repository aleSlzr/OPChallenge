package com.aliaslzr.opchallenge.feature.tracks.presentation.viewmodel

import app.cash.turbine.test
import com.aliaslzr.opchallenge.MainDispatcherRule
import com.aliaslzr.opchallenge.feature.tracks.domain.model.Track
import com.aliaslzr.opchallenge.feature.tracks.domain.usecase.TrackListUseCase
import com.aliaslzr.opchallenge.feature.tracks.presentation.mapper.TrackListUiMapper
import com.aliaslzr.opchallenge.feature.tracks.presentation.model.TrackUi
import com.aliaslzr.opchallenge.feature.tracks.presentation.ui.TrackUiState
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class TrackListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockTrackListUseCase: TrackListUseCase = mockk()
    private val mockTrackListUiMapper: TrackListUiMapper = mockk()

    private lateinit var viewModel: TrackListViewModel
    private val albumId = "albumId"

    @Test
    fun `when ViewModel is created, initial state should be Loading`() = runTest {
        // Given
        every {
            mockTrackListUseCase(any())
        } returns flowOf(emptyList())

        // When
        viewModel = TrackListViewModel(
            getTrackListUseCase = mockTrackListUseCase,
            trackListUiMapper = mockTrackListUiMapper,
        )

        // Then
        viewModel.trackListUiState.test {
            // Loading
            assertEquals(expected = TrackUiState.Loading, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when track list is loaded, should emit Success state`() = runTest {
        // Given
        every {
            mockTrackListUseCase(any())
        } returns flowOf(trackListDomain)
        every {
            mockTrackListUiMapper.transform(trackListDomain)
        } returns trackListUi

        // When
        viewModel = TrackListViewModel(
            getTrackListUseCase = mockTrackListUseCase,
            trackListUiMapper = mockTrackListUiMapper,
        )
        viewModel.getAlbumTracks(albumId)

        // Then
        viewModel.trackListUiState.test {
            // Loading
            assertEquals(TrackUiState.Loading, awaitItem())

            // returns track list
            val success = awaitItem() as TrackUiState.Success
            assertEquals(expected = trackListUi.size, actual = success.trackList.size)
            assertEquals(expected = trackListUi.first().name, actual = success.trackList.first().name)
            assertEquals(expected = trackListUi.first().trackNumber, actual = success.trackList.first().trackNumber)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { mockTrackListUseCase(albumId) }
    }

    @Test
    fun `should emit Loading state, then useCase throws an exception and Error should load`() = runTest {
        val errorMessage = "Error Message"
        // Given
        every {
            mockTrackListUseCase(any())
        } returns flow { throw RuntimeException(errorMessage) }

        // When
        viewModel = TrackListViewModel(
            getTrackListUseCase = mockTrackListUseCase,
            trackListUiMapper = mockTrackListUiMapper,
        )
        viewModel.getAlbumTracks(albumId)

        // Then
        viewModel.trackListUiState.test {
            // Loading
            assertEquals(TrackUiState.Loading, awaitItem())

            // returns Error
            val error = awaitItem() as TrackUiState.Error
            assertEquals(errorMessage, error.errorMessage)
        }
    }

    private val trackListUi =
        listOf(
            TrackUi(
                name = "album 1",
                durationMs = 3000,
                trackNumber = 1,
            ),
            TrackUi(
                name = "album 2",
                durationMs = 3000,
                trackNumber = 2,
            ),
            TrackUi(
                name = "album 3",
                durationMs = 3000,
                trackNumber = 3,
            ),
        )

    private val trackListDomain =
        listOf(
            Track(
                name = "track 1",
                durationMs = 3000,
                trackNumber = 1,
            ),
            Track(
                name = "track 2",
                durationMs = 3000,
                trackNumber = 2,
            ),
            Track(
                name = "track 3",
                durationMs = 3000,
                trackNumber = 3 ,
            ),
        )
}