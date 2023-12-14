package com.github.emmpann.smartbrick.core.data.local.repository

import android.util.Log
import com.github.emmpann.smartbrick.core.data.remote.request.LoginRequest
import com.github.emmpann.smartbrick.core.data.remote.request.RegisterRequest
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResult
import com.github.emmpann.smartbrick.core.data.remote.response.RegisterResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class UserRepository(
    private val apiService: ApiService,
) {
    fun login(email: String, password: String) = flow {
        try {

            val successResponse = apiService.login(LoginRequest(email, password))
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            Log.d("login error", e.toString())
            val errorBody = e.response()?.errorBody()?.string()
            Log.d("login error", errorBody.toString())
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            Log.d("login error", errorResponse.toString())
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String) = flow {
        try {
            Log.d("register", "$name, $email, $password")
            val successResponse = apiService.register(RegisterRequest(name, email, password, password))
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            Log.d("register error", e.message.toString())
            val errorBody = e.response()?.errorBody()?.string()
            Log.d("register error", errorBody.toString())
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

//    fun getUserProfile(id: String) = flow {
//        try {
//            emit(ResultApi.Success(RegisterResponse(true, "Register successfully")))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
//            emit(ResultApi.Error(errorResponse.message))
//            emit(ResultApi.Error(e.message.toString()))
//        }
//    }.onStart {
//        emit(ResultApi.Loading)
//    }.flowOn(Dispatchers.IO)


}