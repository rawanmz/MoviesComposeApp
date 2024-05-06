package com.example.moviescomposeapp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescomposeapp.domain.detail.GetMovieDetailUseCase
import com.example.moviescomposeapp.model.MovieDetailsResponse
import com.example.moviescomposeapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailUseCase
) : ViewModel() {

    var movieDetailState: MutableStateFlow<UIState<MovieDetailsResponse>> =
        MutableStateFlow(UIState.Loading())
    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            when (val response = getMovieDetailsUseCase.invoke(id)) {
                is UIState.Success -> movieDetailState.value = UIState.Success(response.data)
                is UIState.Error -> movieDetailState.value = UIState.Error(response.error)
                is UIState.Empty -> movieDetailState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> movieDetailState.value = UIState.Loading()
            }
        }
    }
}

