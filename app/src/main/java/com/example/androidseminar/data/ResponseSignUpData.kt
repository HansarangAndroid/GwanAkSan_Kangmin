package com.example.androidseminar.data

data class ResponseSignUpData(
    val success: Boolean,
    val message: String,
    val data: SignUpData?
) {
    data class SignUpData(
        val nickname: String
    )
}
