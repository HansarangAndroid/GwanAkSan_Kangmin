package com.example.androidseminar.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users/{user}/repos")
    fun reposForUser(@Path("user") user:String): Call<List<RepoInfo>>
}