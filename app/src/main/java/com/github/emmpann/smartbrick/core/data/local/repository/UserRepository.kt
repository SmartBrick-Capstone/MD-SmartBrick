package com.github.emmpann.smartbrick.core.data.local.repository

import com.github.emmpann.smartbrick.core.data.local.pref.UserPreference
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    fun login(email: String, password: String) = flow {
        try {
            emit(ResultApi.Success("gaming"))
        } catch (e: Exception) {
            emit(ResultApi.Error(e.message.toString()))
        }

        emit(ResultApi.Success(""))
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String) = flow {
        try {
            emit(ResultApi.Success("gaming"))
        } catch (e: Exception) {
            emit(ResultApi.Error(e.message.toString()))
        }

        emit(ResultApi.Success(""))
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

//    suspend fun saveSession(user: UserResponse) {
//        userPreference.saveSession(user)
//    }
//
//    fun getSession() = userPreference.getSession()
//
//    suspend fun logout() {
//        userPreference.logout()
//    }
}