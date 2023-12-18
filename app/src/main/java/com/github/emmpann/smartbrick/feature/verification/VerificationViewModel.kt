package com.github.emmpann.smartbrick.feature.verification

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    preferencesManager: PreferencesManager
) : ViewModel() {
    private var timer: CountDownTimer? = null

    private val _currentTime = MutableLiveData<Long>()

    val currentTimeString = _currentTime.map {
        (it / 1000).toString()
//        Log.d("current time", (it/1000).toString())
    }

    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish

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