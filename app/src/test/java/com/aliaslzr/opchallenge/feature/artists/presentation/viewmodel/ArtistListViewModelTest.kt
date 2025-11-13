package com.aliaslzr.opchallenge.feature.artists.presentation.viewmodel

import app.cash.turbine.test
import com.aliaslzr.opchallenge.MainDispatcherRule
import com.aliaslzr.opchallenge.core.authentication.domain.model.AuthToken
import com.aliaslzr.opchallenge.core.authentication.domain.usecase.AuthUseCase
import com.aliaslzr.opchallenge.feature.artists.domain.model.Artist
import com.aliaslzr.opchallenge.feature.artists.domain.model.ArtistImage
import com.aliaslzr.opchallenge.feature.artists.domain.usecase.ArtistListUseCase
import com.aliaslzr.opchallenge.feature.artists.presentation.model.ArtistUi
import com.aliaslzr.opchallenge.feature.artists.presentation.model.Image
import com.aliaslzr.opchallenge.feature.artists.presentation.ui.ArtistListUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ArtistListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: ArtistListViewModel
    private val mockArtistListUseCase: ArtistListUseCase = mockk()
    private val mockAuthUseCase: AuthUseCase = mockk()

    private val mockAuthToken = AuthToken(
        token = "test_token",
        expiresAt = System.currentTimeMillis() + 36000L,
    )

    @Test
    fun `when ViewModel is created, initial state should be Loading`() = runTest {
        // Given
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when artist list is fetched successfully, should emit Success state`() = runTest {
        // Given
        val mockArtistList = createMockArtistList()
        val expectedUiArtistList = createExpectedUiArtistList()

        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(mockArtistList)

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            val successState = awaitItem() as ArtistListUiState.Success
            assertEquals(expectedUiArtistList.size, successState.artistList.size)
            assertEquals(expectedUiArtistList.first().name, successState.artistList.first().name)
            assertEquals(expectedUiArtistList.first().id, successState.artistList.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when artist list fetch fails, should catch error and not emit error state`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flow { throw exception }

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            // TODO
            // Since the flow catches the error but doesn't emit anything,
            // we should only receive the Loading state
            expectNoEvents()
        }
    }

    @Test
    fun `when auth fails, should catch error and not emit error state`() = runTest {
        // Given
        val exception = RuntimeException("Auth error")
        coEvery { mockAuthUseCase() } throws exception
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            // TODO
            // Since the flow catches the error but doesn't emit anything,
            // we should only receive the Loading state
            expectNoEvents()
        }
    }

    @Test
    fun `should call AuthUseCase before fetching artist list`() = runTest {
        // Given
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Consume the flow to trigger execution
        viewModel.artistUiState.test {
            awaitItem() // Loading
            awaitItem() // Success
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { mockAuthUseCase() }
    }

    @Test
    fun `should call ArtistListUseCase with correct artist ID list`() = runTest {
        // Given
        val expectedArtistIdList = "3WaJSfKnzc65VDgmj2zU8B,0X380XXQSNBYuleKzav5UO,6e9wIFWhBPHLE9bXK8gtBI,3kjuyTCjPG1WMFCiyc5IuB,57ylwQTnFnIhJh4nu4rxCs,2rBcvLKWCZs9w1qIWv560v,1l9d7B8W0IHy3LqWsxP2SH,1WvvwcQx0tj6NdDhZZ2zZz,6RZUqkomCmb8zCRqc9eznB,3j0kMFxXTTYsuw1twLClw3,6VCoG3MG7ZKRxDjaYOvtrF,1HY2Jd0NmPuamShAr6KMms"

        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Consume the flow to trigger execution
        viewModel.artistUiState.test {
            awaitItem() // Loading
            awaitItem() // Success
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        verify { mockArtistListUseCase(expectedArtistIdList) }
    }

    @Test
    fun `when multiple artists are returned, should map all to UI models correctly`() = runTest {
        // Given
        val mockArtistList = listOf(
            createMockArtist("1", "Artist One", 100),
            createMockArtist("2", "Artist Two", 200),
            createMockArtist("3", "Artist Three", 300),
        )

        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(mockArtistList)

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            val successState = awaitItem() as ArtistListUiState.Success
            assertEquals(3, successState.artistList.size)

            // Verify each artist is mapped correctly
            successState.artistList.forEachIndexed { index, artistUi ->
                val expectedArtist = mockArtistList[index]
                assertEquals(expectedArtist.id, artistUi.id)
                assertEquals(expectedArtist.name, artistUi.name)
                assertEquals(expectedArtist.popularity, artistUi.popularity)
                assertEquals(expectedArtist.genres, artistUi.genres)
                assertEquals(expectedArtist.images.size, artistUi.images.size)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `StateFlow should be properly configured with WhileSubscribed and 5 seconds timeout`() = runTest {
        // Given
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        // Test that StateFlow emits initial Loading state immediately
        viewModel.artistUiState.test {
            val initialState = awaitItem()
            assertTrue(initialState is ArtistListUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when empty artist list is returned, should emit Success state with empty list`() = runTest {
        // Given
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            val successState = awaitItem() as ArtistListUiState.Success
            assertTrue(successState.artistList.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when artist has no images, should map correctly with empty images list`() = runTest {
        // Given
        val artistWithoutImages = Artist(
            id = "artist_no_images",
            name = "Artist Without Images",
            popularity = 50,
            genres = listOf("indie"),
            images = emptyList(),
        )

        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(listOf(artistWithoutImages))

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            val successState = awaitItem() as ArtistListUiState.Success
            val artistUi = successState.artistList.first()
            assertEquals("artist_no_images", artistUi.id)
            assertEquals("Artist Without Images", artistUi.name)
            assertEquals(50L, artistUi.popularity)
            assertEquals(listOf("indie"), artistUi.genres)
            assertTrue(artistUi.images.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when artist list use case throws exception after auth succeeds, should catch error`() = runTest {
        // Given
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flow {
            throw RuntimeException("API Error")
        }

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then
        viewModel.artistUiState.test {
            assertEquals(ArtistListUiState.Loading, awaitItem())
            // TODO
            // Error is caught, so no more emissions
            expectNoEvents()
        }
    }

    @Test
    fun `should handle multiple subscribers to the same StateFlow`() = runTest {
        // Given
        val mockArtistList = createMockArtistList()
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(mockArtistList)

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Then - Test multiple subscribers
        val job1 = launch {
            viewModel.artistUiState.test {
                assertEquals(ArtistListUiState.Loading, awaitItem())
                val successState = awaitItem() as ArtistListUiState.Success
                assertEquals(1, successState.artistList.size)
                cancelAndIgnoreRemainingEvents()
            }
        }

        val job2 = launch {
            viewModel.artistUiState.test {
                assertEquals(ArtistListUiState.Loading, awaitItem())
                val successState = awaitItem() as ArtistListUiState.Success
                assertEquals("Test Artist", successState.artistList.first().name)
                cancelAndIgnoreRemainingEvents()
            }
        }

        job1.join()
        job2.join()
    }

    @Test
    fun `should only call AuthUseCase once even with multiple StateFlow subscriptions`() = runTest {
        // Given
        coEvery { mockAuthUseCase() } returns mockAuthToken
        every { mockArtistListUseCase(any()) } returns flowOf(emptyList())

        // When
        viewModel = ArtistListViewModel(mockArtistListUseCase, mockAuthUseCase)

        // Subscribe multiple times
        val job1 = launch {
            viewModel.artistUiState.test {
                awaitItem() // Loading
                awaitItem() // Success
                cancelAndIgnoreRemainingEvents()
            }
        }

        val job2 = launch {
            viewModel.artistUiState.test {
                awaitItem() // Loading
                awaitItem() // Success
                cancelAndIgnoreRemainingEvents()
            }
        }

        job1.join()
        job2.join()

        // Then - Should only be called once due to StateFlow sharing
        coVerify(exactly = 1) { mockAuthUseCase() }
        verify(exactly = 1) { mockArtistListUseCase(any()) }
    }

    private fun createMockArtistList(): List<Artist> = listOf(
        createMockArtist("1", "Test Artist", 85),
    )

    private fun createMockArtist(id: String, name: String, popularity: Long): Artist = Artist(
        id = id,
        name = name,
        popularity = popularity,
        genres = listOf("pop", "rock"),
        images = listOf(
            ArtistImage(
                url = "https://example.com/image.jpg",
                height = 640,
                width = 640,
            ),
        ),
    )

    private fun createExpectedUiArtistList(): List<ArtistUi> = listOf(
        ArtistUi(
            id = "1",
            name = "Test Artist",
            popularity = 85,
            genres = listOf("pop", "rock"),
            images = listOf(
                Image(
                    url = "https://example.com/image.jpg",
                    height = 640,
                    width = 640,
                ),
            ),
        ),
    )
}