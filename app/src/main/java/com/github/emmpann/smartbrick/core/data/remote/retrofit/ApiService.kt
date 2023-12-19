package com.github.emmpann.smartbrick.core.data.remote.retrofit

import com.github.emmpann.smartbrick.core.data.remote.request.LoginRequest
import com.github.emmpann.smartbrick.core.data.remote.request.RegisterRequest
import com.github.emmpann.smartbrick.core.data.remote.request.SendOtpRequest
import com.github.emmpann.smartbrick.core.data.remote.request.VerifyOtpRequest
import com.github.emmpann.smartbrick.core.data.remote.response.ArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.DetailArticleResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ImageResponse
import com.github.emmpann.smartbrick.core.data.remote.response.LoginResponse
import com.github.emmpann.smartbrick.core.data.remote.response.RegisterResponse
import com.github.emmpann.smartbrick.core.data.remote.response.SendOtpResponse
import com.github.emmpann.smartbrick.core.data.remote.response.VerifyOtpResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("send-otp")
    suspend fun sendOtp(@Query("email") email: String): SendOtpResponse

    @POST("verify-email")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): VerifyOtpResponse

    @GET("articles")
    suspend fun getAllArticle(): ArticleResponse

    @GET("articles/{slug}/show")
    suspend fun getDetailArticle(
        @Path("slug") slug: String,
    ): DetailArticleResponse

    @POST("image")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): ImageResponse
}