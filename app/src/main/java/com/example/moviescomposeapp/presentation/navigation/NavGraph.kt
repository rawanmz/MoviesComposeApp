package com.example.moviescomposeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviescomposeapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.moviescomposeapp.presentation.screens.onBoarding.OnBoardingViewModel
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesScreen
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = onBoardingViewModel.startDestination
    )
    {
        composable(Screens.OnBoarding.route) {
            OnBoardingScreen(onBoardingViewModel, navController)
        }
        composable(Screens.PopularMovie.route) {
            val viewModel = hiltViewModel<PopularMoviesViewModel>()
            PopularMoviesScreen(navController, viewModel.popularMoviesState)
        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}