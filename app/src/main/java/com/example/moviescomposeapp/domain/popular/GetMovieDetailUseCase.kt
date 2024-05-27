package com.example.moviescomposeapp.domain.popular

import com.example.moviescomposeapp.data.repository.MoviesRepository

import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val popularMoviesRepository: MoviesRepository) {
    suspend operator fun invoke(id: Int) = popularMoviesRepository.getMovieDetails(id)

}