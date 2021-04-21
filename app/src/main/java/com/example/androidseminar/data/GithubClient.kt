package com.example.androidseminar.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubClient {
    @GET("users/{user}/repos")
    fun reposForUser(@Path("user") user:String): Call<List<RepoInfo>>
}