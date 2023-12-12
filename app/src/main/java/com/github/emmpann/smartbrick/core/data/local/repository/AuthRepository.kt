package com.github.emmpann.smartbrick.core.data.local.repository

import com.github.emmpann.smartbrick.core.data.local.preference.PreferencesManager
import com.github.emmpann.smartbrick.core.data.remote.response.RequestLoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.RequestRegisterResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class AuthRepository(
    private val apiService: ApiService,
) {
    fun login(email: String, password: String) = flow {
        try {
            emit(ResultApi.Success(RequestLoginResponse(true, "successed login", "toket123")))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RequestLoginResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String) = flow {
        try {
            emit(ResultApi.Success(RequestRegisterResponse(true, "Register successfully")))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RequestRegisterResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
            emit(ResultApi.Error(e.message.toString()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)
}