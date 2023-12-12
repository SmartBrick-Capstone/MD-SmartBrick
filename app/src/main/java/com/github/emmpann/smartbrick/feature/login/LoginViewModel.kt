package com.github.emmpann.smartbrick.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.local.repository.AuthRepository
import com.github.emmpann.smartbrick.core.data.remote.response.RequestLoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    private val _loginResponse = MutableLiveData<ResultApi<RequestLoginResponse>>()

    val loginResponse: LiveData<ResultApi<RequestLoginResponse>> = _loginResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        authRepository.login(email, password).collect() {
            _loginResponse.value = it
        }
    }


    fun setToken(token: String) = viewModelScope.launch { preferencesManager.setToken(token) }

}