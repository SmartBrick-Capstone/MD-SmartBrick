package com.github.emmpann.smartbrick.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.repository.UserRepository
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResult
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    private val _loginResponse = MutableLiveData<ResultApi<LoginResponse>>()

    val loginResponse: LiveData<ResultApi<LoginResponse>> get() = _loginResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        userRepository.login(email, password).collect {
            _loginResponse.value = it
        }
    }

    fun setSession(user: LoginResult) = viewModelScope.launch { preferencesManager.setSession(user) }

}