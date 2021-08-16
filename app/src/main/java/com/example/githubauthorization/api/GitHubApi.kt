package com.example.githubauthorization

import com.example.githubauthorization.models.ResponseRepositories
import com.example.githubauthorization.models.UserProfile
import retrofit2.Response
import retrofit2.http.*

interface GitHubApi {

    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val USER_ENDPOINT = "users/{user}"
        const val SEARCH_REPOSITORY = "search/repositories"
    }

    @GET( USER_ENDPOINT)
    suspend fun getUserProfile(@Path("user") userName: String): Response<UserProfile>

    @GET(SEARCH_REPOSITORY)
    suspend fun getListUserRepository(
        @Query("q") search: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<ResponseRepositories>
}