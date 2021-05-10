package com.example.androidseminar.data

data class FollowingUserInfo(
    val userName: String
) {
    val userImage = "https://github.com/$userName.png"
}