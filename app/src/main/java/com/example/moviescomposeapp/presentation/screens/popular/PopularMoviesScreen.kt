package com.example.moviescomposeapp.presentation.screens.popular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviescomposeapp.Constant.MOVIE_IMAGE_BASE_URL
import com.example.moviescomposeapp.R
import com.example.moviescomposeapp.model.BackdropSize
import com.example.moviescomposeapp.model.SearchResponse
import com.example.moviescomposeapp.model.UIState
import com.example.moviescomposeapp.ui.theme.GreenBlue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PopularMoviesScreen(
    navController: NavHostController,
    popularMoviesState: MutableState<UIState<SearchResponse>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenBlue)
    ) {
        when (val result = popularMoviesState.value) {
            is UIState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GreenBlue),
                    contentAlignment = Alignment.TopCenter
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .background(GreenBlue)
                            .rotate(-90f)
                            .paint(
                                // Replace with your image id
                                painterResource(id = R.drawable.background),
                                contentScale = ContentScale.FillWidth,
                                sizeToIntrinsics = false
                            )
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(result.data?.results.orEmpty()) {
                            GlideImage(
                                model = "${MOVIE_IMAGE_BASE_URL}${BackdropSize.w300}/${it.posterPath}",
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillParentMaxWidth(),
                            )

                            Text(
                                text = it.title.orEmpty(),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }

            }

            is UIState.Empty -> {}
            is UIState.Error -> {}
            is UIState.Loading -> {}

        }
    }
}