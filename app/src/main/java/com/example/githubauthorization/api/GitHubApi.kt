package com.example.githubauthorization

import com.example.githubauthorization.model.UserProfile
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GitHubApi {

    companion object{
        const val BASE_URL = "https://api.github.com/"
//        const val USER_ENDPOINT = "user"
        const val USER_ENDPOINT = "user/repos"
    }

    @GET(USER_ENDPOINT)
    suspend fun getRepo(@Header ("Auth") auth: String): UserRepo

}