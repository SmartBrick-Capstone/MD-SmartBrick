package com.github.emmpann.smartbrick.core.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.github.emmpann.smartbrick.core.data.remote.response.Article
import com.github.emmpann.smartbrick.core.data.remote.response.ArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.DetailArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResult
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class ArticleRepository(
    private val apiService: ApiService
) {
    fun getAllArticle() = flow {
        try {
            val successResponse = apiService.getAllArticle()
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun getDetailArticle(slug: String) = flow {
        try {
            val successResponse = apiService.getDetailArticle(slug)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetailArticleResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)
}