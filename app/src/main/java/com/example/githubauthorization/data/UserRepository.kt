package com.example.githubauthorization.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.api.ItemsRepositoryPagingSource
import com.example.githubauthorization.db.EntityRepo
import com.example.githubauthorization.db.RepoDB
import com.example.githubauthorization.models.Item
import com.example.githubauthorization.models.ItemHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: GitHubApi, private val db: RepoDB){

    private val dbDao = db.repositoryDAO

    suspend fun getUserProfile(userName: String) = withContext(Dispatchers.IO){
         api.getUserProfile(userName)
    }

     fun getRepositories(search: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100
            ),
            pagingSourceFactory = {ItemsRepositoryPagingSource(api, db, search)}
        ).liveData

    suspend fun saveRepository(item: ItemHolder){
        dbDao.insertRepository(
            EntityRepo(
            id = item.id,
            description = item.description,
            name = item.name,
            created_at = item.created_at,
            full_name = item.full_name,
            git_url = item.git_url,
//            language = item.language,
            repos_url = item.owner.repos_url,
            login = item.owner.login,
            avatar_url = item.owner.avatar_url
            )
        )
    }

    fun getFavoriteRepositories() =
        dbDao.getRepositoriesFromDB()


    suspend fun removeRepositoryFromDB(item: Int) = withContext(Dispatchers.IO){
        dbDao.removeRepository(item)
    }

    fun getItemFromDatabase(itemId: Int) =
        dbDao.getItem(itemId)

}