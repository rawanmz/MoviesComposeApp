package com.example.moviescomposeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviescomposeapp.presentation.screens.detail.MovieDetailsScreen
import com.example.moviescomposeapp.presentation.screens.detail.MovieDetailViewModel
import com.example.moviescomposeapp.presentation.screens.onBoarding.OnBoardingScreen
import com.example.moviescomposeapp.presentation.screens.onBoarding.OnBoardingViewModel
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesScreen
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesViewModel
import com.example.moviescomposeapp.presentation.screens.profile.ProfileScreen
import com.example.moviescomposeapp.presentation.screens.profile.UserProfileViewModel
import com.example.moviescomposeapp.presentation.screens.search.SearchScreen
import com.example.moviescomposeapp.presentation.screens.search.SearchViewModel

@Composable
fun MovieNavGraph(
    navController: NavHostController = rememberNavController(),
    popularMoviesViewModel: PopularMoviesViewModel
) {
    val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,//onBoardingViewModel.startDestination.value
        modifier = Modifier.semantics {
            contentDescription = Screens.Home.route
        }
    )
    {
        composable(Screens.OnBoarding.route) {
            OnBoardingScreen(onBoardingViewModel, navController)
        }
        composable(Screens.Home.route) {
            PopularMoviesScreen(navController, popularMoviesViewModel.popularMoviesState)
        }

        composable(Screens.Search.route) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(viewModel.moviesState, navController) {
                viewModel.searchIntMovies(it)
            }
        }

        composable(Screens.Profile.route) {
            val viewModel = hiltViewModel<UserProfileViewModel>()
            ProfileScreen(viewModel,navController)
        }
        composable("${Screens.MovieDetail.route}/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailsScreen(viewModel = viewModel, int = it.arguments?.getInt("id"))

        }
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}