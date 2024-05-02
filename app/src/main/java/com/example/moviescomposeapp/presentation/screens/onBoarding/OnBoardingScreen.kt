package com.example.moviescomposeapp.presentation.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviescomposeapp.R
import com.example.moviescomposeapp.presentation.navigation.Screens
import com.example.moviescomposeapp.presentation.navigation.popUpToTop
import com.example.moviescomposeapp.ui.theme.DarkGreenBlue
import com.example.moviescomposeapp.ui.theme.LightYellow


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(onBoardingViewModel: OnBoardingViewModel, navController: NavHostController) {
    val onBoardingCompleted by onBoardingViewModel.onBoardingCompleted.collectAsState()

    if (onBoardingCompleted) {
        navController.navigate(Screens.PopularMovie.route){
            popUpToTop(navController)
        }
    } else {
        val pagerState = rememberPagerState(
            0,
            pageCount = {
                3
            })
        HorizontalPager(state = pagerState) { page ->
            Column {
                Row(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .background(DarkGreenBlue)
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    (0..2).forEach { index ->
                        val color =
                            if (pagerState.currentPage == index) LightYellow else Color.White
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(color, CircleShape)
                                .size(10.dp)
                        )
                    }
                }
                when (page) {
                    0 -> {
                        FirstScreen()
                    }

                    1 -> {
                        SecondScreen()
                    }

                    2 -> {
                        ThirdScreen(navController) {
                            onBoardingViewModel.saveOnBoardingState(true)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun FirstScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGreenBlue),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .rotate(-90f)
                .paint(
                    // Replace with your image id
                    painterResource(id = R.drawable.bottom_background),
                    contentScale = ContentScale.FillWidth,
                    sizeToIntrinsics = false
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.movie),
                    contentDescription = "fil image",
                    modifier = Modifier.size(350.dp)
                )

                Text(
                    text = "Welcome to CineSpectra!",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Explore the latest movies, reserve the perfect seats, and experience the cinema in a whole new way.",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun SecondScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGreenBlue),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .rotate(-90f)
                .paint(
                    // Replace with your image id
                    painterResource(id = R.drawable.bottom_background),
                    contentScale = ContentScale.FillWidth,
                    sizeToIntrinsics = false
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.home_cinema),
                    contentDescription = "fil image",
                    modifier = Modifier.size(350.dp)
                )

                Text(
                    text = "Welcome to CineSpectra!",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Explore the latest movies, reserve the perfect seats, and experience the cinema in a whole new way.",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun ThirdScreen(navController: NavHostController, onFinishClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGreenBlue),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .rotate(-90f)
                .paint(
                    // Replace with your image id
                    painterResource(id = R.drawable.bottom_background),
                    contentScale = ContentScale.FillWidth,
                    sizeToIntrinsics = false
                )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.horror_movie),
                    contentDescription = "fil image",
                    modifier = Modifier.size(350.dp)
                )

                Text(
                    text = "Welcome to CineSpectra!",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Explore the latest movies, reserve the perfect seats, and experience the cinema in a whole new way.",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = true
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screens.PopularMovie.route){
                                    popUpToTop(navController)
                                }
                                onFinishClick.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(text = "Finish", modifier = Modifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}