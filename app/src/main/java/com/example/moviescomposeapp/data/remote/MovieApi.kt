package com.example.moviescomposeapp.data.remote

import com.example.moviescomposeapp.BuildConfig
import com.example.moviescomposeapp.model.MovieDetailsResponse
import com.example.moviescomposeapp.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key")
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1,
    ): Response<SearchResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")
        movieId: Int,
        @Query("api_key")
        api_key: String = BuildConfig.TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("append_to_response")
        append_to_response: String?=""
    ): Response<MovieDetailsResponse>
}