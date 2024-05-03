package com.example.moviescomposeapp.domain.popular

import androidx.paging.PagingData
import com.example.moviescomposeapp.data.repository.PopularMoviesRepository
import com.example.moviescomposeapp.model.Results
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetPopularMoviesUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) {
    suspend operator fun invoke(): Flow<PagingData<Results>> {
        return popularMoviesRepository.getPopularMovies()
    }
}