package com.example.moviescomposeapp.data.repository

import com.example.moviescomposeapp.BuildConfig
import com.example.moviescomposeapp.Constant
import com.example.moviescomposeapp.data.remote.MovieApi
import com.example.moviescomposeapp.model.MovieDetailsResponse
import com.example.moviescomposeapp.model.SearchResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepositoryTest {
    private lateinit var server: MockWebServer//Fake server from square lib
    private lateinit var api: MovieApi

    @Before
    fun setUp() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/${Constant.MOVIE_BASE_URL}"))//Pass any base url like this
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieApi::class.java)

    }

    @After
    fun tearDown() {
        server.shutdown()

    }

    @Test
     fun getMovieDetails() = runTest {
        val dto = MovieDetailsResponse()//The object I want back as response
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(dto)//Convert the object into json string using GSON
        val res = MockResponse()//Make a fake response for our server call
        res.setBody(json)//set the body of the fake response as the json string you are expecting as a response
        server.enqueue(res)//add it in the server response queue
        val data = api.getMovieDetail(938614, BuildConfig.TMDB_API_KEY)//make the call to our fake server(as we are using fake base url)
        server.takeRequest()//let the server take the request
        assertEquals(data.body(),dto)//the data you are getting as the call response should be same
    }

    @Test
    fun `getMovieDetails, returns Error`() = runTest {
        //First step is to make the server ready with a response
        val res = MockResponse()
        res.setResponseCode(404)
        res.setBody("404")
        server.enqueue(res)


        val data = api.getMovieDetail(938614)

        //Third step is to tell our server to accept the call created
        server.takeRequest()

        assertTrue(res.status.contains(data.raw().message,true))//Our repo shows error as the response code was 400.
        assertEquals(data.isSuccessful,false)
    }
    @Test
    fun getUpComingMovies() = runTest {
        val dto = SearchResponse()//The object I want back as response
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(dto)//Convert the object into json string using GSON
        val res = MockResponse()//Make a fake response for our server call
        res.setBody(json)//set the body of the fake response as the json string you are expecting as a response
        server.enqueue(res)//add it in the server response queue
        val data = api.getUpcoming()//make the call to our fake server(as we are using fake base url)
        server.takeRequest()//let the server take the request
        assertEquals(data.body(),dto)//the data you are getting as the call response should be same
    }
}