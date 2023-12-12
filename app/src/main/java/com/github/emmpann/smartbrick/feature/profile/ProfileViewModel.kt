package com.github.emmpann.smartbrick.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.local.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   private val userRepository: UserRepository,
   private val preferencesManager: PreferencesManager
) : ViewModel() {

   fun currentUser() = userRepository

   fun clearSession() = viewModelScope.launch { preferencesManager.clearSession() }
}