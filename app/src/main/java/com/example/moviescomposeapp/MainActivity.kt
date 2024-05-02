package com.example.moviescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.moviescomposeapp.presentation.navigation.NavGraph
import com.example.moviescomposeapp.ui.theme.MoviesComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MoviesComposeAppTheme {
               NavGraph()
            }
        }
    }
}