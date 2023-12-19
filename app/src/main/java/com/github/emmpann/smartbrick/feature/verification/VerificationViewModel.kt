package com.github.emmpann.smartbrick.feature.verification

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.camera.video.VideoRecordEvent.Finalize.VideoRecordError
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.response.SendOtpResponse
import com.github.emmpann.smartbrick.core.data.remote.response.VerifyOtpResponse
import com.github.emmpann.smartbrick.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    private var timer: CountDownTimer? = null
    private val _currentTime = MutableLiveData<Long>()
    val currentTimeString = _currentTime.map {
        (it / 1000).toString()
    }

    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish

    private val _verifyOtpResponse = MutableLiveData<ResultApi<VerifyOtpResponse>>()
    val verifyOtpResponse: LiveData<ResultApi<VerifyOtpResponse>> get() = _verifyOtpResponse

    private val _sendOtpResponse = MutableLiveData<ResultApi<SendOtpResponse>>()
    val sendOtpResponse: LiveData<ResultApi<SendOtpResponse>> get() = _sendOtpResponse

    private val _currentEmail = MutableLiveData<String>()
    val currentEmail: LiveData<String> = _currentEmail
    fun getEmail() {
        viewModelScope.launch {
            preferencesManager.getEmail().collect{
                _currentEmail.value = it
            }
        }
    }

    val isUserVerify: LiveData<Boolean> = preferencesManager.isUserVerified().asLiveData()



    fun setUserVerified(isVerified: Boolean) {
        viewModelScope.launch {
            preferencesManager.setUserVerified(isVerified)
        }
    }

    fun sendOtp(email: String) {
        viewModelScope.launch {
            userRepository.sendOtp(email).collect {
                _sendOtpResponse.value = it
            }
        }
    }

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            userRepository.verifyOtp(email, otp).collect {
                _verifyOtpResponse.value = it
            }
        }
    }

    fun startCountDownResendButtonTimer() {
        _eventCountDownFinish.value = false
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(p0: Long) {
                _currentTime.value = p0
                Log.d("current time2", p0.toString())

            }

            override fun onFinish() {
                _eventCountDownFinish.value = true
            }
        }
        timer?.start()
    }
}