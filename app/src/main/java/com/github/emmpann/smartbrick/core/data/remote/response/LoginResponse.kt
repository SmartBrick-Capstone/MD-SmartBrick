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

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String,
)