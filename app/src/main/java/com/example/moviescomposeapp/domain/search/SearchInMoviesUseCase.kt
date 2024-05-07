package com.example.moviescomposeapp.domain.search

import com.example.moviescomposeapp.data.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class SearchInMoviesUseCase @Inject constructor(private val popularMoviesRepository: MoviesRepository) {
    operator fun invoke(query: String) = popularMoviesRepository.searchInMovies(query)

}