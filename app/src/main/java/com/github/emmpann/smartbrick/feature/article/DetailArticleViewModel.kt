package com.github.emmpann.smartbrick.feature.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.github.emmpann.smartbrick.core.data.local.repository.ArticleRepository
import com.github.emmpann.smartbrick.core.data.remote.response.DetailArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articleSlug = MutableLiveData<String>()

    val article: LiveData<ResultApi<DetailArticleResponse>> = _articleSlug.switchMap {
        articleRepository.getDetailArticle(it).asLiveData()
    }

    fun setArticleSlug(slug: String) {
        _articleSlug.value = slug
    }
}