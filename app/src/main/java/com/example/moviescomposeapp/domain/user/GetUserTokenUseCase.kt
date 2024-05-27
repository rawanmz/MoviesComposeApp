package com.example.moviescomposeapp.domain.user

import com.example.moviescomposeapp.data.repository.MoviesRepository
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(private val popularMoviesRepository: MoviesRepository) {
    suspend operator fun invoke() = popularMoviesRepository.getUserToken()
        //to do store it in data store
}