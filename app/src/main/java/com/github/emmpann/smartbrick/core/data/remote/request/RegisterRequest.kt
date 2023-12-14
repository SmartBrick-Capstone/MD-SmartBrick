package com.github.emmpann.smartbrick.core.data.remote.request

data class RegisterRequest(

    val name: String,

    val email: String,

    val password: String,

    val password_confirmation: String
)
