package com.example.moviescomposeapp.domain.popular

import com.example.moviescomposeapp.data.repository.PopularMoviesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPopularMoviesUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) {
    suspend operator fun invoke() = popularMoviesRepository.getPopularMovies()

}