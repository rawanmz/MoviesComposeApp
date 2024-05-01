package com.example.moviescomposeapp.presentation.navigation


sealed class Screens(val route: String) {
    data object OnBoarding : Screens("on_boarding")
    data object PopularMovie : Screens("popular_movie_screen")
}