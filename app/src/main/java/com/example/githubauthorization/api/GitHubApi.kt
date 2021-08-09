package com.example.githubauthorization

import com.example.githubauthorization.models.UserProfile
import retrofit2.Response
import retrofit2.http.*

interface GitHubApi {

    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val USER_ENDPOINT = "users/{user}"
    }

    @GET( USER_ENDPOINT)
    suspend fun getUserProfile(@Path("user") userName: String): Response<UserProfile>
}