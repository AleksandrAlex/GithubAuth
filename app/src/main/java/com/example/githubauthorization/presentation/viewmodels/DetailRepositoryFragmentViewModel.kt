package com.example.githubauthorization.presentation

import androidx.lifecycle.ViewModel
import com.example.githubauthorization.Repository
import com.example.githubauthorization.models.ItemHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepositoryFragmentViewModel @Inject constructor(private val repositoty: Repository): ViewModel(){




    suspend fun saveRepository(item: ItemHolder) = withContext(Dispatchers.IO){
        repositoty.saveRepository(item)
    }

    suspend fun removeRepository(item: ItemHolder) = withContext(Dispatchers.IO) {
        repositoty.removeRepositoryFromDB(
            item.item.id
        )
    }
}

