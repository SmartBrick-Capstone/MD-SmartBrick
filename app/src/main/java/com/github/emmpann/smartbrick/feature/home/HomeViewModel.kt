package com.github.emmpann.smartbrick.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.repository.ArticleRepository
import com.github.emmpann.smartbrick.core.data.remote.response.ArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val articleRepository: ArticleRepository
) : ViewModel() {
    val currentUsername: LiveData<String> = preferencesManager.getName().asLiveData()

    val article: LiveData<ResultApi<ArticleResponse>> = articleRepository.getAllArticle().asLiveData()
}