package com.example.githubauthorization

import com.example.githubauthorization.model.UserProfile
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GitHubApi {

    companion object{
        const val BASE_URL = "https://api.github.com/"
//        const val USER_ENDPOINT = "user"
        const val USER_ENDPOINT = "users/AleksandrAlex"
    }


    @GET(USER_ENDPOINT)
    suspend fun getUser(): Response<UserProfile>

}