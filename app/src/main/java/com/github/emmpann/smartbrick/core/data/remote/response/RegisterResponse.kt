package com.github.emmpann.smartbrick.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("errors")
    val errors: Errors,
)

data class Errors(

    @field:SerializedName("password")
    val password: List<String>? = null,

    @field:SerializedName("password_confirmation")
    val passwordConfirmation: List<String>? = null,

    @field:SerializedName("name")
    val name: List<String>? = null,

    @field:SerializedName("email")
    val email: List<String>? = null
)
