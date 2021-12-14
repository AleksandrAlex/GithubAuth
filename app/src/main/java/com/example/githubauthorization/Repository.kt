package com.example.githubauthorization

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.githubauthorization.db.EntityRepo
import com.example.githubauthorization.models.ItemHolder
import com.example.githubauthorization.models.UserProfile
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getUserProfile(userName: String): Response<UserProfile>

    fun getRepositories(search: String): LiveData<PagingData<ItemHolder>>

    suspend fun saveRepository(item: ItemHolder)

    fun getFavoriteRepositories(): Flow<List<EntityRepo>>

    suspend fun removeRepositoryFromDB(item: Long)

    fun getItemFromDatabase(itemId: Long): Flow<EntityRepo>
}