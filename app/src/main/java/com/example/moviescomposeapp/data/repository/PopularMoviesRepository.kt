package com.example.moviescomposeapp.data.repository

import com.example.moviescomposeapp.data.remote.MovieApi
import com.example.moviescomposeapp.model.SearchResponse
import com.example.moviescomposeapp.model.UIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    val movieApi: MovieApi
) {
    suspend fun getPopularMovies(): UIState<SearchResponse> {
        try {
            val response = movieApi.getUpcoming()
            if (response.isSuccessful && response.body() != null) {
                return UIState.Success(response.body())
            } else {
                return UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            return UIState.Error(e.message.toString())
        }

    }
}