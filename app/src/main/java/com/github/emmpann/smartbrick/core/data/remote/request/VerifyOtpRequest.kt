package com.github.emmpann.smartbrick.core.data.remote.request

data class VerifyOtpRequest(

	val email: String,

	val otp: String,
)
