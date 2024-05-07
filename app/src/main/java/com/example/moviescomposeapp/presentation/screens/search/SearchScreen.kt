package com.example.moviescomposeapp.presentation.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.moviescomposeapp.Constant
import com.example.moviescomposeapp.R
import com.example.moviescomposeapp.model.BackdropSize
import com.example.moviescomposeapp.model.Results
import com.example.moviescomposeapp.presentation.navigation.Screens
import com.example.moviescomposeapp.ui.theme.DarkGreenBlue
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SearchScreen(
    moviesState: MutableStateFlow<PagingData<Results>>,
    navController: NavHostController,
    onSearch: (String) -> Unit
) {

    var text by rememberSaveable { mutableStateOf("") }

    val moviePagingItems = moviesState.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TextField(
                modifier = Modifier.fillMaxWidth().padding( 8.dp),
                value = text,
                onValueChange = {
                    text = it
//                    onSearch.invoke(text)
                },
                leadingIcon=  {
                    Icon( Icons.Filled.Search,"Icon")

                },
                label = { Text("Search") },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Handle "Enter" key press action
                        // This code block will be executed when the user presses "Enter" on the keyboard
                        // You can perform any action here, such as submitting the text
                        onSearch.invoke(text)
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = DarkGreenBlue,
                    unfocusedContainerColor = Color.Gray
                )
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .wrapContentSize()
                    .semantics {
                        contentDescription = "LazyVerticalGridLayout"
                    },
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center
            ) {
                items(moviePagingItems.itemCount) { index ->
                    AsyncImage(
                        model = "${Constant.MOVIE_IMAGE_BASE_URL}${BackdropSize.w300}/${moviePagingItems[index]?.posterPath}",
                        contentDescription = "NavigateToDetail",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                navController.navigate(Screens.MovieDetail.route + "/${moviePagingItems[index]?.id}")
                            },
                        contentScale = ContentScale.FillWidth,
                        error = painterResource(R.drawable.no_poster),
                        placeholder = painterResource(R.drawable.no_poster)
                    )
                    Text(
                        text = moviePagingItems[index]?.voteAverage.toString(),
                        modifier = Modifier.semantics {
                            testTag = "NavigateToDetail"
                        })
                }
            }
        }
        moviePagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
//
//                    Row(
//                        Modifier.fillMaxSize(),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        CircularProgressIndicator()
//                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = moviePagingItems.loadState.refresh as LoadState.Error
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = error.error.localizedMessage.orEmpty(),
                            modifier = Modifier,
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        CircularProgressIndicator()
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = moviePagingItems.loadState.append as LoadState.Error
                    Text(
                        text = error.error.localizedMessage.orEmpty(),
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}