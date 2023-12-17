package com.github.emmpann.smartbrick

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    val token: LiveData<String> = preferencesManager.getToken().asLiveData()

    val userFirstTime: LiveData<Boolean> = preferencesManager.getUserFirstTime().asLiveData()


}