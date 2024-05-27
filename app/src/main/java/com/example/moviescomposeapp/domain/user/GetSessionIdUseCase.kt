package com.example.moviescomposeapp.domain.user

import com.example.moviescomposeapp.data.repository.MoviesRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(private val popularMoviesRepository: MoviesRepository) {
    suspend operator fun invoke(requestToken: String) =
        popularMoviesRepository.getSessionId(requestToken)
    //to do store it in data store
}