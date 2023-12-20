package com.github.emmpann.smartbrick.core.data.repository

import android.util.Log
import com.github.emmpann.smartbrick.core.data.remote.response.PredictResponse
import com.github.emmpann.smartbrick.core.data.remote.response.ResultApi
import com.github.emmpann.smartbrick.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class ImageRepository(
    private val apiService: ApiService
) {
    fun predictImage(imageFile: File) = flow {
        try {

            val requestImageFile = imageFile.asRequestBody("image/jpg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            val successResponse = apiService.predictImage(multipartBody)
            emit(ResultApi.Success(successResponse))
        } catch (e: Exception) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
            emit(ResultApi.Error(e.message.toString()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)
}