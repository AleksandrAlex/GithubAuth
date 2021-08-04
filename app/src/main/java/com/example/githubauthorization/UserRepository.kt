package com.example.githubauthorization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: GitHubApi){

    suspend fun getUserProfile(userName: String) = withContext(Dispatchers.IO){
         api.getUserProfile(userName)
    }

}