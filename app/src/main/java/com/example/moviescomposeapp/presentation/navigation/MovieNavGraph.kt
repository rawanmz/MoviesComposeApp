package com.example.moviescomposeapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviescomposeapp.presentation.screens.detail.MovieDetailViewModel
import com.example.moviescomposeapp.presentation.screens.detail.MovieDetailsScreen
import com.example.moviescomposeapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.moviescomposeapp.presentation.screens.onBoarding.OnBoardingViewModel
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesScreen
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesViewModel

@Composable
fun MovieNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = onBoardingViewModel.startDestination.value
    )
    {
        composable(Screens.OnBoarding.route) {
            OnBoardingScreen(onBoardingViewModel, navController)
        }
        composable(Screens.Home.route) {
            val viewModel = hiltViewModel<PopularMoviesViewModel>()
            PopularMoviesScreen(navController, viewModel.popularMoviesState)
        }
        composable(Screens.Search.route) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)) {

            }
        }
        composable(Screens.Profile.route) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)) {

            }
        }
        composable("${Screens.MovieDetail.route}/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailsScreen(viewModel=viewModel, int = it.arguments?.getInt("id"), )

        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
        saveState = true
    }
}