package com.example.githubauthorization.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.api.ItemsRepositoryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: GitHubApi){

    suspend fun getUserProfile(userName: String) = withContext(Dispatchers.IO){
         api.getUserProfile(userName)
    }

     fun getRepositories(search: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100
            ),
            pagingSourceFactory = {ItemsRepositoryPagingSource(api, search)}
        ).liveData

}