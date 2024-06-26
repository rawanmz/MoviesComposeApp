package com.example.moviescomposeapp.presentation.screens.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.moviescomposeapp.domain.popular.GetPopularMoviesUseCase
import com.example.moviescomposeapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    var popularMoviesState: MutableStateFlow<PagingData<Results>> =
        MutableStateFlow(PagingData.empty())

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectIndexed { _, value ->

                    popularMoviesState.value = value
                }
        }
    }
}