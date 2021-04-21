package com.example.androidseminar.data

import com.google.gson.annotations.SerializedName

data class RepoInfo(
    @SerializedName("name") val name: String,
    val description: String,
    val language: String
)
