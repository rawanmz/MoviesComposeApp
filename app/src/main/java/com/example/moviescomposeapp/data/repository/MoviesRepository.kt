package com.example.moviescomposeapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviescomposeapp.data.paging.MoviePagingSource
import com.example.moviescomposeapp.data.remote.MovieApi
import com.example.moviescomposeapp.model.MovieDetailsResponse
import com.example.moviescomposeapp.model.Results
import com.example.moviescomposeapp.model.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    val movieApi: MovieApi
) {
    suspend fun getMovieDetails(movieID: Int): UIState<MovieDetailsResponse> {
        try {
            val response = movieApi.getMovieDetail(movieID)
            if (response.isSuccessful && response.body() != null) {
                return UIState.Success(response.body())
            } else {
                return UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            return UIState.Error(e.message.toString())
        }

    }

      fun getUpComingMovies(): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviePagingSource(movieApi,false)
            }
        ).flow
    }

    fun searchInMovies(query: String): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, true, query)
            }
        ).flow
    }
}