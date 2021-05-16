package com.example.androidseminar.api

import com.example.androidseminar.data.request.RequestLoginData
import com.example.androidseminar.data.request.RequestSignUpData
import com.example.androidseminar.data.response.ResponseLoginData
import com.example.androidseminar.data.response.ResponseSignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptService {
    @POST("login/signin")
    fun postLogin(
        @Body body: RequestLoginData
    ): Call<ResponseLoginData>

    @POST("login/signup")
    fun postSignUp(
        @Body body: RequestSignUpData
    ): Call<ResponseSignUpData>
}