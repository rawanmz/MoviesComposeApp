package com.example.moviescomposeapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviescomposeapp.domain.search.SearchInMoviesUseCase
import com.example.moviescomposeapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchInMoviesUseCase: SearchInMoviesUseCase
) : ViewModel() {

    var moviesState: MutableStateFlow<PagingData<Results>> =
        MutableStateFlow(PagingData.empty())


    fun searchIntMovies(query: String) {
        viewModelScope.launch {
            searchInMoviesUseCase.invoke(query).distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectIndexed { _, value ->
                    moviesState.value = value
                }
        }
    }
}