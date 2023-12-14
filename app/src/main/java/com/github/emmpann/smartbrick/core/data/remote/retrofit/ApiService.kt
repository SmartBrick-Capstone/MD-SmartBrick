package com.github.emmpann.smartbrick.core.data.remote.retrofit

import com.github.emmpann.smartbrick.core.data.remote.request.LoginRequest
import com.github.emmpann.smartbrick.core.data.remote.request.RegisterRequest
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}