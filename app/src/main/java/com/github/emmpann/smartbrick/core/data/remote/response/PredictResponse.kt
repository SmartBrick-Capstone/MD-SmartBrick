package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("prediction")
    val prediction: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("errors")
    val errors: Errors
)

data class Errors(

    @field:SerializedName("image")
    val image: List<String>
)