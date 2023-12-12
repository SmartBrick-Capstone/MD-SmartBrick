package com.github.emmpann.smartbrick.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.local.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   private val authRepository: AuthRepository,
   private val preferencesManager: PreferencesManager
) : ViewModel() {
   fun clearSession() = viewModelScope.launch { preferencesManager.clearSession() }
}