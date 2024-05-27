package com.example.moviescomposeapp.domain.popular

import com.example.moviescomposeapp.data.repository.MoviesRepository
import com.example.moviescomposeapp.model.MovieDetailsResponse
import com.example.moviescomposeapp.model.UIState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailUseCaseTest : BaseTestCase() {

    @MockK(relaxed = true)
    lateinit var moviesRepository: MoviesRepository

    @MockK(relaxed = true)
    lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getMovieDetailUseCase = GetMovieDetailUseCase(moviesRepository)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    val dummy = UIState.Success(MovieDetailsResponse())

    @Test
    fun invoke() {
        runTest {
            val expected = dummy
            coEvery {
                moviesRepository.getMovieDetails(11)
            } returns expected
            val result = getMovieDetailUseCase(11)
            Assert.assertEquals(expected, result)
        }
    }
}