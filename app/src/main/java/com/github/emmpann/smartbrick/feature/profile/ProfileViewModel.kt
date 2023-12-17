package com.github.emmpann.smartbrick.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    val name: LiveData<String> = preferencesManager.getName().asLiveData()

    val email: LiveData<String> = preferencesManager.getEmail().asLiveData()

    fun clearSession() = viewModelScope.launch { preferencesManager.clearSession() }
}