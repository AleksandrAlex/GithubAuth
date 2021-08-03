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
//        apiToken = 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

    }

    @GET( USER_ENDPOINT)
    @Headers("Authorization: token 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")
    suspend fun getRepo(@Path("user") userName: String): UserProfile
//    suspend fun getRepo(): UserProfile

}