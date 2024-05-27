package com.example.moviescomposeapp.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescomposeapp.domain.user.GetSessionIdUseCase
import com.example.moviescomposeapp.domain.user.GetUserAccountUseCase
import com.example.moviescomposeapp.domain.user.GetUserTokenUseCase
import com.example.moviescomposeapp.model.UIState
import com.example.moviescomposeapp.model.UserAccount
import com.example.moviescomposeapp.model.UserTokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase
) : ViewModel() {

    var userTokenState: MutableStateFlow<UIState<UserTokenResponse>> =
        MutableStateFlow(UIState.Loading())

    var userSessionState: MutableStateFlow<UIState<UserTokenResponse>> =
        MutableStateFlow(UIState.Loading())

    var userAccountState: MutableStateFlow<UIState<UserAccount>> =
        MutableStateFlow(UIState.Loading())
    fun getUserToken() {
        viewModelScope.launch {
            when (val response = getUserTokenUseCase.invoke()) {
                is UIState.Success -> userTokenState.value = UIState.Success(response.data)
                is UIState.Error -> userTokenState.value = UIState.Error(response.error)
                is UIState.Empty -> userTokenState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> userTokenState.value = UIState.Loading()
            }
        }
    }

    fun getSessionId(requestToken: String) {
        viewModelScope.launch {
            when (val response = getSessionIdUseCase.invoke(requestToken)) {
                is UIState.Success -> userSessionState.value = UIState.Success(response.data)
                is UIState.Error -> userSessionState.value = UIState.Error(response.error)
                is UIState.Empty -> userSessionState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> userSessionState.value = UIState.Loading()
            }
        }
    }

    fun getUserAccount(sessionId: String) {
        viewModelScope.launch {
            when (val response = getUserAccountUseCase.invoke(sessionId)) {
                is UIState.Success -> userAccountState.value = UIState.Success(response.data)
                is UIState.Error -> userAccountState.value = UIState.Error(response.error)
                is UIState.Empty -> userAccountState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> userAccountState.value = UIState.Loading()
            }
        }
    }

}

