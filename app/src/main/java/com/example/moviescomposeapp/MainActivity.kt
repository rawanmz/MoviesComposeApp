package com.example.moviescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviescomposeapp.presentation.navigation.BottomNavigationItem
import com.example.moviescomposeapp.presentation.navigation.MovieNavGraph
import com.example.moviescomposeapp.presentation.navigation.Screens
import com.example.moviescomposeapp.presentation.navigation.popUpToTop
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesViewModel
import com.example.moviescomposeapp.ui.theme.DarkGreenBlue
import com.example.moviescomposeapp.ui.theme.DarkYellow
import com.example.moviescomposeapp.ui.theme.MoviesComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<PopularMoviesViewModel>()
            MoviesComposeAppTheme {
//                val viewModel = hiltViewModel<PopularMoviesViewModel>()
                val navController = rememberNavController()

                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.OnBoarding.route -> false // on this screen bottom bar should be hidden
                    "${Screens.MovieDetail.route}/{id}" -> false
                    else -> true // in all other cases show bottom bar
                }
                val navigationSelectedItem = rememberSaveable {
                    mutableIntStateOf(0)
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            contentDescription = "MyScreen"
                        },
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = DarkGreenBlue
                            ) {
                                BottomNavigationBar(
                                    navigationSelectedItem,
                                    navController
                                ) {
                                    viewModel.getPopularMovies()
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    //We need to setup our NavHost in here
                    Box(modifier = Modifier.padding(paddingValues)) {
                        MovieNavGraph(navController, viewModel)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun RowScope.BottomNavigationBar(
        navigationSelectedItem: MutableIntState,
        navController: NavHostController,
        onHomeDoubleClick: () -> Unit
    ) {
        val clickCount = remember { mutableStateOf(0) }

        BottomNavigationItem().bottomNavigationItems()
            .forEachIndexed { index, navigationItem ->
                //iterating all items with their respective indexes
                NavigationBarItem(
                    selected = index == navigationSelectedItem.intValue,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = DarkGreenBlue,
                        selectedIconColor = DarkYellow,
                        selectedTextColor = DarkYellow
                    ),
                    label = {
                        Text(navigationItem.label)
                    },
                    icon = {
                        Icon(
                            navigationItem.icon,
                            contentDescription = navigationItem.label
                        )
                    },
                    onClick = {
                        // Execute the action on double-click
                        clickCount.value++
                        if (clickCount.value > 2||index!=navigationSelectedItem.intValue)
                            clickCount.value = 0
                        if (clickCount.value == 2) {
                            onHomeDoubleClick()
                        }
                        navigationSelectedItem.intValue = index
                        navController.navigate(navigationItem.route) {
                            popUpToTop(navController)
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
    }
}