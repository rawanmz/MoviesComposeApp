package com.example.moviescomposeapp.domain.detail


import com.example.moviescomposeapp.data.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val popularMoviesRepository: MoviesRepository) {
    suspend operator fun invoke(id: Int) = popularMoviesRepository.getMovieDetails(id)

}