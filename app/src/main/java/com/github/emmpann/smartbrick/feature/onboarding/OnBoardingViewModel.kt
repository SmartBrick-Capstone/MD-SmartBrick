package com.github.emmpann.smartbrick.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    fun setUserFirstTime(userFirstTime: Boolean) = viewModelScope.launch {
        preferencesManager.setUserFirstTime(userFirstTime)
    }
}