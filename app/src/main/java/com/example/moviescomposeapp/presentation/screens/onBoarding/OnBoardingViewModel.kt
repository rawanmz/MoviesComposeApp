package com.example.moviescomposeapp.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescomposeapp.domain.onBoarding.GetIsSafeFromDataStoreUseCase
import com.example.moviescomposeapp.domain.onBoarding.SaveIsFirstTimeInDataStoreUseCase
import com.example.moviescomposeapp.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveIsFirstTimeInDataStoreUseCase: SaveIsFirstTimeInDataStoreUseCase,
    private val getIsSafeFromDataStoreUseCase: GetIsSafeFromDataStoreUseCase
) : ViewModel() {

     val onBoardingCompleted = MutableStateFlow(false)
    //val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted
    var startDestination: String = Screens.OnBoarding.route

    init {
        getOnBoardingState()
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
            onBoardingCompleted.value = getIsSafeFromDataStoreUseCase().stateIn(viewModelScope).value
            startDestination =
                if (onBoardingCompleted.value) Screens.PopularMovie.route else Screens.OnBoarding.route

        }
    }

    fun saveOnBoardingState(showOnBoardingPage: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveIsFirstTimeInDataStoreUseCase(showTipsPage = showOnBoardingPage)
        }
    }
}