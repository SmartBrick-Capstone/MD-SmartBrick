package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,
)

data class LoginResult(
    val id: String,

    val name: String,

    val email: String,

    val emailVerifiedAt: String,

    val token: String,
)