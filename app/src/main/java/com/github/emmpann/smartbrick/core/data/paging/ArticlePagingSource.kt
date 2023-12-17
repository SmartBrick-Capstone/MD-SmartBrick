package com.github.emmpann.smartbrick.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.emmpann.smartbrick.core.data.remote.response.ArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService

class ArticlePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ArticleResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        TODO("Not yet implemented")
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}