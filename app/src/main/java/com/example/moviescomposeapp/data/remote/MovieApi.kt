package com.example.moviescomposeapp.data.remote

import com.example.moviescomposeapp.BuildConfig
import com.example.moviescomposeapp.model.MovieDetailsResponse
import com.example.moviescomposeapp.model.SearchResponse
import com.example.moviescomposeapp.model.UserAccount
import com.example.moviescomposeapp.model.UserTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
        apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("append_to_response")
        append_to_response: String?=""
    ): Response<MovieDetailsResponse>

    @GET("3/search/movie")
    suspend fun search(
        @Query("api_key")
        apiKey: String =  BuildConfig.TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("query")
        query: String,
        @Query("page")
        page: Int ,
        @Query("include_adult")
        includeAdult: Boolean = false
    ): Response<SearchResponse>

    @GET("3/authentication/token/new")
    suspend fun getUserToken(
        @Query("api_key")
        apiKey: String =  BuildConfig.TMDB_API_KEY
    ): Response<UserTokenResponse>

    //if success redirect to web page
    //url https://www.themoviedb.org/authenticate/{requestToken}
    // after verifying should be faster than 60 second
    @POST("3/authentication/session/new")
    suspend fun getSessionId(
        @Query("api_key")
        apiKey: String =  BuildConfig.TMDB_API_KEY,
        @Query("request_token")
        requestToken:String
    ): Response<UserTokenResponse>//return session_id

 //   https://api.themoviedb.org/3/authentication/session/new?api_key=7bb7631fd14ebe601a5cfb40ddb1cf28&request_token=a8462e498f46dfbdf097cabc694a4c2701024804

    @GET("3/account")
    suspend fun getUserAccount(
        @Query("api_key")
        apiKey: String =  BuildConfig.TMDB_API_KEY,
        @Query("session_id")
        sessionId:String
    ): Response<UserAccount>

 //   https://api.themoviedb.org/3/account?api_key=7bb7631fd14ebe601a5cfb40ddb1cf28&session_id=7ad9bcc374d7eb70fe4b03a1fb703423db97492d



}