package com.example.githubauthorization

import com.example.githubauthorization.model.UserProfile
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

interface GitHubApi {

//    apiToken = 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

    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val USER_ENDPOINT = "users/{user}"
//        const val USER_ENDPOINT = "user"

    }

    @GET( USER_ENDPOINT)
//    @Headers("Authorization: token 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")
    suspend fun getUserProfile(@Path("user") userName: String): Response<UserProfile>
//    suspend fun getRepo(): UserProfile

}