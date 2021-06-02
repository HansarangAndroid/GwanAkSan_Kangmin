package com.example.androidseminar.data

import com.google.gson.annotations.SerializedName

data class GitHubRepoInfo(
    val name: String,
    val description: String,
    val language: String,
    val owner: GithubUserInfo
)
