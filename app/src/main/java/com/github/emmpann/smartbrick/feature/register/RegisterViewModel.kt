package com.github.emmpann.smartbrick.feature.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.repository.UserRepository
import com.github.emmpann.smartbrick.core.data.remote.response.RegisterResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _registerResponse = MutableLiveData<ResultApi<RegisterResponse>>()

    val registerResponse: LiveData<ResultApi<RegisterResponse>> get() = _registerResponse

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        userRepository.register(name, email, password).collect {
            _registerResponse.value = it
        }
    }
}