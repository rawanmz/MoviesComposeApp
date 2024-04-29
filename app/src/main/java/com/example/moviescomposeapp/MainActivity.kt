package com.example.moviescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviescomposeapp.model.UIState
import com.example.moviescomposeapp.presentation.screens.popular.PopularMoviesViewModel
import com.example.moviescomposeapp.ui.theme.MoviesComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PopularMoviesViewModel>()

        enableEdgeToEdge()
        setContent {
            MoviesComposeAppTheme {
                when (val result = viewModel.popularMoviesState.value) {
                    is UIState.Success -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(result.data?.results.orEmpty()) {
                                Text(
                                    text = it.title.orEmpty(),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    is UIState.Empty -> {}
                    is UIState.Error -> {}
                    is UIState.Loading -> {}

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesComposeAppTheme {
        Greeting("Android")
    }
}