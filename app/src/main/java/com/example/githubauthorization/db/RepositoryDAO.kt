package com.example.githubauthorization.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepository(item: EntityRepo)

    @Query("SELECT * FROM entityrepo")
    fun getRepositoriesFromDB(): Flow<List<EntityRepo>>

    @Delete
    suspend fun removeRepository(item: EntityRepo)
}