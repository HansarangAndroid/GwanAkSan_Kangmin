package com.example.androidseminar.api

import com.example.androidseminar.data.GitHubRepoInfo
import com.example.androidseminar.data.GithubUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users/{user}/repos")
    fun reposForUser(@Path("user") user: String): Call<List<GitHubRepoInfo>>

    @GET("users/kkk5474096")
    fun getUserInfo(): Call<GithubUserInfo>

    @GET("users/kkk5474096/following")
    fun getFollowingInfo(): Call<List<GithubUserInfo>>
}