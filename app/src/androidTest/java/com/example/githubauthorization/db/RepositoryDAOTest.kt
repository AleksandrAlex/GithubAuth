package com.example.githubauthorization.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RepositoryDAOTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RepoDB
    private lateinit var dao: RepositoryDAO

    @Before
    fun setup(){

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepoDB::class.java
        ).allowMainThreadQueries()
            .build()

        dao = db.repositoryDAO
    }

    @After
    fun close(){
        db.close()
    }

    @Test
    fun insertItemInDatabase() = runBlockingTest{

        val item = EntityRepo(
                id = 1, description = "desc", name = "Alex", created_at = "2021", full_name = "Alex",
                git_url = "url", repos_url = "repo", login = "Alex1", avatar_url = "ava"
        )
        dao.insertRepository(item)
        val list = dao.getRepositoriesFromDB().first()
        assertEquals(1, list.size)
    }

    @Test
    fun removeItemFromDatabase() = runBlockingTest{

        val item = EntityRepo(
                id = 1, description = "desc", name = "Alex", created_at = "2021", full_name = "Alex",
                git_url = "url", repos_url = "repo", login = "Alex1", avatar_url = "ava"
        )
        dao.insertRepository(item)
        dao.removeRepository(item.id)
        val list = dao.getRepositoriesFromDB().first()

        assertEquals(0, list.size)
    }
}