package com.github.emmpann.smartbrick.feature.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.remote.response.History
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val history: LiveData<ResultApi<List<History>>> = historyRepository.getAllHistory().asLiveData()

    val isVerified: LiveData<Boolean> = preferencesManager.isUserVerified().asLiveData()

}